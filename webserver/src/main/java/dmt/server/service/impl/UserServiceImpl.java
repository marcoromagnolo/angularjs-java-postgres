package dmt.server.service.impl;

import dmt.server.data.entity.UserAccountEntity;
import dmt.server.data.entity.UserEntity;
import dmt.server.data.exception.DataException;
import dmt.server.data.repository.AccountRepo;
import dmt.server.data.repository.UserRepo;
import dmt.server.service.UserService;
import dmt.server.service.dto.UserDTO;
import dmt.server.service.exception.AuthorizationException;
import dmt.server.service.exception.DbException;
import dmt.server.service.exception.GenericException;
import dmt.server.service.exception.InternalException;
import dmt.server.enums.ErrorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Marco Romagnolo
 */
@Service("userService")
public class UserServiceImpl extends AbstractService implements UserService {

    private static final Logger logger = Logger.getLogger(UserServiceImpl.class.getName());

    @Autowired
    private UserRepo<UserEntity> userRepo;

    @Autowired
    private AccountRepo<UserAccountEntity> accountRepo;

    @Override
    public List<UserDTO> getUsers(String token) throws AuthorizationException, InternalException {
        try {
            txOpen();
            getSession(token);
            List<UserEntity> users = userRepo.findAll();
            List<UserDTO> list = new ArrayList<>();
            for (UserEntity user : users) {
                list.add(new UserDTO(user));
            }
            return list;
        } catch (DataException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new DbException();
        } finally {
            txClose();
        }
    }

    @Override
    public UserDTO getUser(String userId, String token) throws GenericException, AuthorizationException, InternalException {
        try {
            txOpen();
            getSession(token);
            UserEntity user = userRepo.find(Long.valueOf(userId));
            return new UserDTO(user);
        } catch (NumberFormatException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new GenericException(ErrorType.ERROR_USERID_NUMBER_FORMAT);
        } catch (DataException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new DbException();
        } finally {
            txClose();
        }
    }

    @Override
    public void saveUser(String userId, String token, String firstName, String lastName, String email, Date birthday, String phone) throws GenericException, AuthorizationException, InternalException {
        try {
            txOpen();
            getSession(token);
            UserAccountEntity user = accountRepo.find(Long.valueOf(userId));
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            user.setBirthday(birthday);
            user.setPhone(phone);
            accountRepo.save(user);
        } catch (NumberFormatException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new GenericException(ErrorType.ERROR_USERID_NUMBER_FORMAT);
        } catch (DataException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new DbException();
        } finally {
            txClose();
        }
    }

    @Override
    public void changePassword(String userId, String token, String password) throws GenericException, AuthorizationException, InternalException {
        try {
            txOpen();
            getSession(token);
            UserAccountEntity user = accountRepo.find(Long.valueOf(userId));
            user.setPassword(password);
            accountRepo.save(user);
        } catch (NumberFormatException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new GenericException(ErrorType.ERROR_USERID_NUMBER_FORMAT);
        } catch (DataException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new DbException();
        } finally {
            txClose();
        }
    }

}
