package dmt.server.test.data;

import dmt.server.data.entity.UserSettingEntity;
import dmt.server.data.exception.DataException;
import dmt.server.data.repository.UserSettingsRepo;
import dmt.server.data.repository.impl.UserSettingsRepoImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/appconfig.xml"})
public class UserSettingsRepoTest {

    private EntityFactory factory = new EntityFactory();
    
	@Autowired
	private UserSettingsRepo<UserSettingEntity> userSettingRepo;

    /**
     * Launch all test in order
     */
    @Test
    public void launchAll() throws DataException {
        userSettingRepo.clearTable();
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
            System.out.println("Testing " + UserSettingsRepoImpl.class + ".insert()");
            UserSettingEntity entity = factory.getUserSettingEntity();
            userSettingRepo.insert(entity);
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
            System.out.println("Testing " + UserSettingsRepoImpl.class + ".find()");
            UserSettingEntity entity = userSettingRepo.find(EntityFactory.ID);
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
            System.out.println("Testing " + UserSettingsRepoImpl.class + ".update()");
            UserSettingEntity entity = factory.getUserSettingEntity();
            userSettingRepo.update(entity);
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
            System.out.println("Testing " + UserSettingsRepoImpl.class + ".delete()");
            UserSettingEntity entity = factory.getUserSettingEntity();
            userSettingRepo.delete(entity);
            System.out.println("Deleted: " + entity);
        } catch (DataException e) {
            Assert.fail(e.getMessage());
        }
    }
}
