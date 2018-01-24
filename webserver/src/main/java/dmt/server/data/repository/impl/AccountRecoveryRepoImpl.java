package dmt.server.data.repository.impl;

import dmt.server.data.entity.UserAccountRecoveryEntity;
import dmt.server.data.repository.AccountRecoveryRepo;
import org.springframework.stereotype.Repository;

/**
 * @author Marco Romagnolo
 */
@Repository("recoveryRepo")
public class AccountRecoveryRepoImpl extends AbstractRepo<UserAccountRecoveryEntity> implements AccountRecoveryRepo<UserAccountRecoveryEntity> {

}
