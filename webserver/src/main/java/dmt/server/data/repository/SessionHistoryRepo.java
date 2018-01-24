package dmt.server.data.repository;

import dmt.server.data.exception.DataException;

/**
 * @author Marco Romagnolo
 */
public interface SessionHistoryRepo<T> extends Repo<T> {

    T findByToken(String token) throws DataException;

}
