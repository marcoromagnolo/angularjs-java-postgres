package dmt.server.test.data;

import dmt.server.data.entity.UserAccountEntity;
import dmt.server.data.exception.DataException;
import dmt.server.data.repository.AccountRepo;
import dmt.server.data.repository.impl.AccountRepoImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/appconfig.xml"})
public class AccountRepoTest {

    private EntityFactory factory = new EntityFactory();

    @Autowired
    private AccountRepo<UserAccountEntity> accountRepo;

    /**
     * Launch all test in order
     */
    @Test
    public void launchAll() throws DataException {
        accountRepo.clearTable();
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
            System.out.println("Testing " + AccountRepoImpl.class + ".insert()");
            UserAccountEntity entity = factory.getUserAccountEntity();
            accountRepo.insert(entity);
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
            // By ID
            System.out.println("Testing " + AccountRepoImpl.class + ".find()");
            UserAccountEntity entity = accountRepo.find(EntityFactory.ID);
            System.out.println("Found: " + entity);
            Assert.assertNotNull(entity);

            // By Email
            System.out.println("Testing " + AccountRepoImpl.class + ".find()");
            entity = accountRepo.findByEmail("unitTest@test.it");
            System.out.println("Found: " + entity);
            Assert.assertNotNull(entity);

            // By Username
            System.out.println("Testing " + AccountRepoImpl.class + ".find()");
            entity = accountRepo.findByUsername("test_username");
            System.out.println("Found: " + entity);
            Assert.assertNotNull(entity);

            // By UserId
            System.out.println("Testing " + AccountRepoImpl.class + ".find()");
            entity = accountRepo.findByUserId(EntityFactory.ID);
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
            System.out.println("Testing " + AccountRepoImpl.class + ".update()");
            UserAccountEntity entity = factory.getUserAccountEntity();
            accountRepo.update(entity);
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
            System.out.println("Testing " + AccountRepoImpl.class + ".delete()");
            UserAccountEntity entity = factory.getUserAccountEntity();
            accountRepo.delete(entity);
            System.out.println("Deleted: " + entity);
        } catch (DataException e) {
            Assert.fail(e.getMessage());
        }
    }

}
