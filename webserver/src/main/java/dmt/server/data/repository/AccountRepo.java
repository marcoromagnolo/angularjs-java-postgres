package dmt.server.data.repository;

import dmt.server.data.entity.UserAccountEntity;
import dmt.server.data.exception.DataException;

/**
 * @author Marco Romagnolo
 */
public interface AccountRepo<T> extends Repo<T> {

    UserAccountEntity findByUserId(Long userId) throws DataException;

    UserAccountEntity findByUsername(String username) throws DataException;

    UserAccountEntity findByEmail(String email) throws DataException;

}
