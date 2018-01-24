package dmt.server.test.data;

import dmt.server.data.entity.UserAccountRecoveryEntity;
import dmt.server.data.exception.DataException;
import dmt.server.data.repository.AccountRecoveryRepo;
import dmt.server.data.repository.impl.AccountRecoveryRepoImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/appconfig.xml"})
public class AccountRecoveryRepoTest {

    private EntityFactory factory = new EntityFactory();

	@Autowired
	private AccountRecoveryRepo<UserAccountRecoveryEntity> accountRecoveryRepo;

    /**
     * Launch all test in order
     */
    @Test
    public void launchAll() throws DataException {
        accountRecoveryRepo.clearTable();
        insert();
        find();
        update();
        delete();
    }

    /**
     * Test Insert
     */
    public void insert() {
        try {
            System.out.println("Testing " + AccountRecoveryRepoImpl.class + ".insert()");
            UserAccountRecoveryEntity entity = factory.getUserAccountRecoveryEntity();
            accountRecoveryRepo.insert(entity);
            System.out.println("Inserted: " + entity);
        } catch (DataException e) {
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Find test
     */
    public void find() {
        try {
            System.out.println("Testing " + AccountRecoveryRepoImpl.class + ".find()");
            UserAccountRecoveryEntity entity = accountRecoveryRepo.find(EntityFactory.ID);
            System.out.println("Found: " + entity);
            Assert.assertNotNull(entity);
        } catch (DataException e) {
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Update test
     */
    public void update() {
        try {
            System.out.println("Testing " + AccountRecoveryRepoImpl.class + ".update()");
            UserAccountRecoveryEntity entity = factory.getUserAccountRecoveryEntity();
            accountRecoveryRepo.update(entity);
            System.out.println("Updated: " + entity);
        } catch (DataException e) {
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Delete test
     */
    public void delete() {
        try {
            System.out.println("Testing " + AccountRecoveryRepoImpl.class + ".delete()");
            UserAccountRecoveryEntity entity = factory.getUserAccountRecoveryEntity();
            accountRecoveryRepo.delete(entity);
            System.out.println("Deleted: " + entity);
        } catch (DataException e) {
            Assert.fail(e.getMessage());
        }
    }
}
