package dmt.server.test.data;

import dmt.server.data.entity.UserEntity;
import dmt.server.data.exception.DataException;
import dmt.server.data.repository.UserRepo;
import dmt.server.data.repository.impl.UserRepoImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/appconfig.xml"})
public class UserRepoTest {

    private EntityFactory factory = new EntityFactory();

    @Autowired
    private UserRepo<UserEntity> userRepo;

    /**
     * Launch all test in order
     */
    @Test
    public void launchAll() throws DataException {
        userRepo.clearTable();
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
            System.out.println("Testing " + UserRepoImpl.class + ".insert()");
            UserEntity entity = factory.getUserEntity();
            userRepo.insert(entity);
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
            System.out.println("Testing " + UserRepoImpl.class + ".find()");
            UserEntity entity = userRepo.find(EntityFactory.ID);
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
            System.out.println("Testing " + UserRepoImpl.class + ".update()");
            UserEntity entity = factory.getUserEntity();
            userRepo.update(entity);
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
            System.out.println("Testing " + UserRepoImpl.class + ".delete()");
            UserEntity entity = factory.getUserEntity();
            userRepo.delete(entity);
            System.out.println("Deleted: " + entity);
        } catch (DataException e) {
            Assert.fail(e.getMessage());
        }
    }

}
