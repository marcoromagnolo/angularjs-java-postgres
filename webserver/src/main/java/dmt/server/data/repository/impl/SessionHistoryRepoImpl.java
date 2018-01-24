package dmt.server.data.repository.impl;

import dmt.server.data.entity.SessionHistoryEntity;
import dmt.server.data.exception.DataException;
import dmt.server.data.exception.TooManyResultsException;
import dmt.server.data.repository.SessionHistoryRepo;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Marco Romagnolo
 */
@Repository("sessionHistoryRepo")
public class SessionHistoryRepoImpl  extends AbstractRepo<SessionHistoryEntity> implements SessionHistoryRepo<SessionHistoryEntity> {

    @Override
    public SessionHistoryEntity findByToken(String token) throws DataException {
        try {
            String query = "select from " + SessionHistoryEntity.class.getSimpleName() + " where token=" + token;
            Statement stmt = getConnection().createStatement();
            stmt.executeQuery(query);
            if (stmt.getFetchSize()>1) {
                throw new TooManyResultsException();
            }
            return toEntity(stmt.getResultSet());
        } catch (SQLException e) {
            throw new DataException(e);
        }
    }
}
