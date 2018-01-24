package dmt.server.test.data;

import dmt.server.data.entity.*;
import dmt.server.data.exception.DataException;
import dmt.server.data.repository.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/appconfig.xml"})
public class EntityJoinTest {

    private EntityFactory factory = new EntityFactory();

    @Autowired
    private AccountRepo<UserAccountEntity> accountRepo;

    @Autowired
    private UserRepo<UserEntity> userRepo;

    @Autowired
    private UserSettingsRepo<UserSettingEntity> userSettingRepo;

    @Autowired
    private AccountRecoveryRepo<UserAccountRecoveryEntity> accountRecoveryRepo;

    @Autowired
    private SessionHistoryRepo<SessionHistoryEntity> sessionHistoryRepo;

    @Autowired
    private UserSessionHistoryRepo<UserSessionHistoryEntity> userSessionHistoryRepo;

    /**
     * Launch all test in order
     */
    @Test
    public void launchAll() throws DataException {
        accountRepo.clearTable();
        userRepo.clearTable();
        userSettingRepo.clearTable();
        accountRecoveryRepo.clearTable();
        sessionHistoryRepo.clearTable();
        insert();
        find();
        findAll();
    }

    /**
     * Test Insert
     */
    public void insert() {
        try {
            System.out.println("Inserting all data for Joins");

            UserAccountEntity accountEntity = factory.getUserAccountEntity();
            accountRepo.insert(accountEntity);
            System.out.println("Inserted: " + accountEntity);

            UserEntity userEntity = factory.getUserEntity();
            userRepo.insert(userEntity);
            System.out.println("Inserted: " + userEntity);

            UserAccountRecoveryEntity recoveryEntity = factory.getUserAccountRecoveryEntity();
            accountRecoveryRepo.insert(recoveryEntity);
            System.out.println("Inserted: " + recoveryEntity);

            UserSettingEntity settingEntity = factory.getUserSettingEntity();
            userSettingRepo.insert(settingEntity);
            System.out.println("Inserted: " + settingEntity);

            SessionHistoryEntity sessionHistoryEntity = factory.getSessionHistoryEntity();
            sessionHistoryRepo.insert(sessionHistoryEntity);
            sessionHistoryEntity.setId(EntityFactory.ID + 1);
            sessionHistoryRepo.insert(sessionHistoryEntity);
            sessionHistoryEntity.setId(EntityFactory.ID + 2);
            sessionHistoryRepo.insert(sessionHistoryEntity);
            System.out.println("Inserted: " + settingEntity);
        } catch (DataException e) {
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Find test
     */
    public void find() {
        try {
            System.out.println("Find data with Joins");

            UserAccountEntity entity = accountRepo.find(EntityFactory.ID);
            System.out.println("Found: " + entity);
            Assert.assertNotNull(entity);

            UserSessionHistoryEntity userSessionHistoryEntity = userSessionHistoryRepo.find(EntityFactory.ID);
            System.out.println("Found: " + userSessionHistoryEntity);
            Assert.assertNotNull(userSessionHistoryEntity);

        } catch (DataException e) {
            Assert.fail(e.getMessage());
        }
    }

    /**
     * FindAll test
     */
    private void findAll() {
        try {
            List<SessionHistoryEntity> sessionHistoryList = sessionHistoryRepo.findAll();
            System.out.println("Found: " + sessionHistoryList);
            Assert.assertNotNull(sessionHistoryList);
        } catch (DataException e) {
            Assert.fail(e.getMessage());
        }
    }
}
