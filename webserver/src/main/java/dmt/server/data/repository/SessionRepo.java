package dmt.server.data.repository;

import dmt.server.data.entity.UserEntity;
import dmt.server.data.exception.DataException;

import java.util.List;

/**
 * @author Marco Romagnolo
 */
public interface SessionRepo<T> extends Repo<T> {

    List<T> findByUser(UserEntity user) throws DataException;

    void deleteByToken(String token) throws DataException;
}
