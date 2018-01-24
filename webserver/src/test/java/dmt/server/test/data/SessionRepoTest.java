package dmt.server.test.data;

import dmt.server.data.entity.SessionEntity;
import dmt.server.data.exception.DataException;
import dmt.server.data.repository.SessionRepo;
import dmt.server.data.repository.impl.SessionRepoImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/appconfig.xml"})
public class SessionRepoTest {

    private EntityFactory factory = new EntityFactory();
	
	@Autowired
	private SessionRepo<SessionEntity> sessionRepo;

    /**
     * Launch all test in order
     */
    @Test
    public void launchAll() throws DataException {
        sessionRepo.clearTable();
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
            System.out.println("Testing " + SessionRepoImpl.class + ".insert()");
            SessionEntity entity = factory.getSessionEntity();
            sessionRepo.insert(entity);
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
            System.out.println("Testing " + SessionRepoImpl.class + ".find()");
            SessionEntity entity = sessionRepo.find(EntityFactory.ID);
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
            System.out.println("Testing " + SessionRepoImpl.class + ".update()");
            SessionEntity entity = factory.getSessionEntity();
            sessionRepo.update(entity);
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
            System.out.println("Testing " + SessionRepoImpl.class + ".delete()");
            SessionEntity entity = factory.getSessionEntity();
            sessionRepo.delete(entity);
            System.out.println("Deleted: " + entity);
        } catch (DataException e) {
            Assert.fail(e.getMessage());
        }
    }
	
}