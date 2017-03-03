package DataAccess;

import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;

import static org.junit.Assert.*;

/**
 * Created by emmag on 2/24/2017.
 */
public class DatabaseDaoTest {
    DatabaseDao db;

    @Before
    public void setUp() throws Exception {
        boolean success = DatabaseDao.initialize();
        db = new DatabaseDao();
        assertTrue(success);
    }

    @Test
    public void getConnection() throws Exception {
        Connection conn = db.getConnection();
        assertNotEquals(null, conn);
    }

    /*@Test
    public void initialize() throws Exception {

    }*/

    @Test
    public void closeConnection() throws Exception {
        db.closeConnection(true);
        assertEquals(null, db.getConnection());

    }

    @Test
    public void clearDatabase() throws Exception {
        boolean success = db.clearDatabase();
        assertTrue(success);
    }

}