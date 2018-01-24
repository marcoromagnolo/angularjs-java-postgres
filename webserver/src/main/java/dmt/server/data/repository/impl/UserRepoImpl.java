package dmt.server.data.repository.impl;

import dmt.server.data.entity.UserEntity;
import dmt.server.data.repository.UserRepo;
import org.springframework.stereotype.Repository;

/**
 * @author Marco Romagnolo
 */
@Repository("userRepo")
public class UserRepoImpl extends AbstractRepo<UserEntity> implements UserRepo<UserEntity> {

}
