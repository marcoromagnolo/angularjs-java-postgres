package dmt.server.data.repository.impl;

import dmt.server.data.entity.UserAccountEntity;
import dmt.server.data.exception.DataException;
import dmt.server.data.helper.Condition;
import dmt.server.data.helper.Conditions;
import dmt.server.data.repository.AccountRepo;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

/**
 * @author Marco Romagnolo
 */
@Repository("accountRepo")
public class AccountRepoImpl extends AbstractRepo<UserAccountEntity> implements AccountRepo<UserAccountEntity> {

    private static final Logger LOGGER = Logger.getLogger(AccountRepoImpl.class.getName());

    @Override
    public UserAccountEntity findByUserId(Long userId) throws DataException {
        try {
            Conditions conditions = new Conditions(new Condition(UserAccountEntity.class, "userId", userId));
            ResultSet rs = select(conditions);
            return toEntity(rs);
        } catch (SQLException e) {
            throw new DataException(e);
        }
    }

    @Override
    public UserAccountEntity findByUsername(String username) throws DataException {
        try {
            ;
            Conditions conditions = new Conditions(new Condition(UserAccountEntity.class, "username", username));
            ResultSet rs = select(conditions);
            return toEntity(rs);
        } catch (SQLException e) {
            throw new DataException(e);
        }
    }

    @Override
    public UserAccountEntity findByEmail(String email) throws DataException {
        try {
            Conditions conditions = new Conditions(new Condition(UserAccountEntity.class, "email", email));
            ResultSet rs = select(conditions);
            return toEntity(rs);
        } catch (SQLException e) {
            throw new DataException(e);
        }
    }

}
