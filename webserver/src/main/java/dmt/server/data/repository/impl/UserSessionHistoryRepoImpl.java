package dmt.server.data.repository.impl;

import dmt.server.data.entity.UserSessionHistoryEntity;
import dmt.server.data.repository.UserSessionHistoryRepo;
import org.springframework.stereotype.Repository;

@Repository("userSessionHistoryRepo")
public class UserSessionHistoryRepoImpl extends AbstractRepo<UserSessionHistoryEntity> implements UserSessionHistoryRepo<UserSessionHistoryEntity> {

}
