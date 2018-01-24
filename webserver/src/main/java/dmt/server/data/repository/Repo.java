package dmt.server.data.repository;

import dmt.server.data.exception.DataException;
import dmt.server.data.helper.Conditions;

import java.util.List;

/**
 * @author Marco Romagnolo
 */
public interface Repo<T> {

    void close();

    T insert(T entity) throws DataException;

    int update(T entity) throws DataException;

    int update(T entity, Conditions conditions) throws DataException;

    T save(T object) throws DataException;

    void delete(T object) throws DataException;

    void delete(Conditions conditions) throws DataException;

    List<T> findAll() throws DataException;

    T find(Long id) throws DataException;

    void clearTable() throws DataException;
}
