package dmt.server.data.repository.impl;

import dmt.server.data.entity.UserEntity;
import dmt.server.data.entity.UserSettingEntity;
import dmt.server.data.exception.DataException;
import dmt.server.data.repository.UserSettingsRepo;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Repository("userSettingRepo")
public class UserSettingsRepoImpl extends AbstractRepo<UserSettingEntity> implements UserSettingsRepo<UserSettingEntity> {

	@Override
    public List<UserSettingEntity> findByUser(UserEntity user) throws DataException
    {
        try{
        	String query = "SELECT from "+UserSettingEntity.class.getSimpleName()+" WHERE user="+user;
        	Statement stmt = getConnection().createStatement();
        	stmt.executeQuery(query);
        	return toEntities(stmt.getResultSet());
        }catch(SQLException e){
        	throw new DataException(e);
        }
        
    }
}


