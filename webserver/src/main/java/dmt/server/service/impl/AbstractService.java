package dmt.server.service.impl;

import dmt.server.component.Database;
import dmt.server.data.entity.SessionEntity;
import dmt.server.data.entity.SessionHistoryEntity;
import dmt.server.data.entity.UserEntity;
import dmt.server.data.exception.DataException;
import dmt.server.data.repository.SessionHistoryRepo;
import dmt.server.data.repository.SessionRepo;
import dmt.server.enums.ErrorType;
import dmt.server.service.dto.SessionDTO;
import dmt.server.service.exception.AuthorizationException;
import dmt.server.service.exception.DbException;
import dmt.server.service.exception.GenericException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 */
public abstract class AbstractService {

    private static final Logger logger = Logger.getLogger(AbstractService.class.getName());

    private static Map<String, SessionDTO> sessions;

    @Autowired
    private Database db;

    @Autowired
    private SessionRepo<SessionEntity> sessionRepo;

    @Autowired
    private SessionHistoryRepo<SessionHistoryEntity> sessionHistoryRepo;

    @Value("${service.auth.multisession}")
    private boolean multiSession;

    @Value("${auth.session.maxInactiveInterval}")
    private int maxInactiveInterval;

    @PostConstruct
    private void init() throws GenericException, DbException {
        if (sessions==null) {
            sessions = new HashMap<>();
            try {
                List<SessionEntity> persistentSessions = sessionRepo.findAll();
                if (persistentSessions!=null) {
                    for (SessionEntity session : persistentSessions) {
                        sessions.put(session.getToken(), convert(session));
                    }
                }
            } catch (DataException e) {
                logger.log(Level.SEVERE, e.getMessage(), e);
                throw new DbException();
            }
        }
    }

    protected void txOpen() throws DbException {
        try {
            db.getConnection();
        } catch (DataException e) {
            throw new DbException();
        }
    }

    protected void txClose() throws DbException {
        try {
            db.disconnect();
        } catch (DataException e) {
            throw new DbException();
        }
    }

    protected SessionDTO getSession(String token) throws AuthorizationException {
        if (token==null || !sessions.containsKey(token)) {
            throw new AuthorizationException(ErrorType.ERROR_INVALID_SESSION);
        }
        SessionDTO session = sessions.get(token);
        long now = new Date().getTime();
        long timeLimit = maxInactiveInterval + session.getCreateDate().getTime();
        if (session.isExpiry() && maxInactiveInterval>=0 && now>timeLimit) {
            throw new AuthorizationException(ErrorType.ERROR_SESSION_EXPIRED);
        }
        return session;
    }

    protected SessionDTO createSession(UserEntity user, boolean expiry, String address, String headers) throws GenericException, DataException {
        if (!multiSession) {
            if (sessionRepo.findByUser(user).isEmpty()) {
                throw new GenericException(ErrorType.ERROR_ALREADY_OPEN_SESSION);
            }
        }
        String token = generateToken();
        Date now = new Date();
        SessionEntity session = new SessionEntity();
        session.setToken(token);
        session.setCreateDate(now);
        session.setUserId(user.getId());
        session.setExpiry(expiry);
        SessionEntity sessionWrap = sessionRepo.save(session);
        historyLogin(user, token, expiry, now, address, headers);
        SessionDTO sessionDTO = convert(sessionWrap);
        sessions.put(session.getToken(), sessionDTO);
        return sessionDTO;
    }

    protected void destroySession(UserEntity user, String token, String remoteAddress, String headers) throws AuthorizationException, DataException, GenericException {
        SessionDTO session = getSession(token);
        if (session.getUserId().equals(user.getId().toString())) {
            historyLogout(user, token, new Date(), session.getCreateDate(), remoteAddress, headers);
            sessions.remove(token);
            sessionRepo.deleteByToken(token);
        } else {
            throw new GenericException(ErrorType.ERROR_USER_INVALID_SESSION);
        }
    }

    private String generateToken() {
        String uniqueKey;
        do {
            uniqueKey = UUID.randomUUID().toString();
        } while (sessions.get(uniqueKey)!=null);
        return uniqueKey;
    }

    private void historyLogin(UserEntity user, String token, boolean expiry, Date date, String address, String headers) throws DataException {
        SessionHistoryEntity sessionHistory = new SessionHistoryEntity();
        sessionHistory.setUserId(user.getId());
        sessionHistory.setToken(token);
        sessionHistory.setExpiry(expiry);
        sessionHistory.setLoginDate(date);
        sessionHistory.setHeaders(headers);
        sessionHistory.setAddress(address);
        sessionHistoryRepo.save(sessionHistory);
    }

    private void historyLogout(UserEntity user, String token, Date date, Date loginDate, String address, String headers) throws DataException {
        SessionHistoryEntity sessionHistory = new SessionHistoryEntity();
        sessionHistory.setUserId(user.getId());
        sessionHistory.setToken(token);
        sessionHistory.setExpiry(false);
        sessionHistory.setLogoutDate(date);
        sessionHistory.setLoginDate(loginDate);
        sessionHistory.setHeaders(headers);
        sessionHistory.setAddress(address);
        sessionHistoryRepo.save(sessionHistory);
    }

    private SessionDTO convert(SessionEntity session) {
        return new SessionDTO(session.getUserId().toString(), session.getToken(), session.isExpiry(), session.getCreateDate());
    }

}
