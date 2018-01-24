package dmt.server.data.repository;

import dmt.server.data.entity.UserEntity;
import dmt.server.data.exception.DataException;

import java.util.List;

public interface UserSettingsRepo<T> extends Repo<T> {
	
    List<T> findByUser(UserEntity user) throws DataException;

}
