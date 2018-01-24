package dmt.server.test.data;

import dmt.server.data.entity.SessionHistoryEntity;
import dmt.server.data.exception.DataException;
import dmt.server.data.repository.SessionHistoryRepo;
import dmt.server.data.repository.impl.SessionHistoryRepoImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.logging.Logger;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/appconfig.xml"})
public class SessionHistoryRepoTest {

	private static final Logger logger = Logger.getLogger(SessionHistoryRepoTest.class.getName());
    private EntityFactory factory = new EntityFactory();
	
	@Autowired
	private SessionHistoryRepo<SessionHistoryEntity> sessionHistoryRepo;

    /**
     * Launch all test in order
     */
    @Test
    public void launchAll() throws DataException {
        sessionHistoryRepo.clearTable();
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
            System.out.println("Testing " + SessionHistoryRepoImpl.class + ".insert()");
            SessionHistoryEntity entity = factory.getSessionHistoryEntity();
            sessionHistoryRepo.insert(entity);
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
            System.out.println("Testing " + SessionHistoryRepoImpl.class + ".find()");
            SessionHistoryEntity entity = sessionHistoryRepo.find(EntityFactory.ID);
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
            System.out.println("Testing " + SessionHistoryRepoImpl.class + ".update()");
            SessionHistoryEntity entity = factory.getSessionHistoryEntity();
            sessionHistoryRepo.update(entity);
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
            System.out.println("Testing " + SessionHistoryRepoImpl.class + ".delete()");
            SessionHistoryEntity entity = factory.getSessionHistoryEntity();
            sessionHistoryRepo.delete(entity);
            System.out.println("Deleted: " + entity);
        } catch (DataException e) {
            Assert.fail(e.getMessage());
        }
    }
	
}