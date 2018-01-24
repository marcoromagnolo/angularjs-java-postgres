package dmt.server.data.repository.impl;

import dmt.server.data.entity.SessionEntity;
import dmt.server.data.entity.UserEntity;
import dmt.server.data.exception.DataException;
import dmt.server.data.helper.Condition;
import dmt.server.data.helper.Conditions;
import dmt.server.data.repository.SessionRepo;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * @author Marco Romagnolo
 */
@Repository("sessionRepo")
public class SessionRepoImpl extends AbstractRepo<SessionEntity> implements SessionRepo<SessionEntity> {

    @Override
    public List<SessionEntity> findByUser(UserEntity user) throws DataException {
        try {
            String query = "select from " + SessionEntity.class.getSimpleName() + " where user=" + user;
            Statement stmt = getConnection().createStatement();
            stmt.executeQuery(query);
            return toEntities(stmt.getResultSet());
        } catch (SQLException e) {
            throw new DataException(e);
        }
    }

    @Override
    public void deleteByToken(String token) throws DataException {
        try {
            Conditions conditions = new Conditions(new Condition(SessionEntity.class, "token", token));
            delete(conditions);

        } catch (Exception e) {
            throw new DataException(e);
        }
    }
}
