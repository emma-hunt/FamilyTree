package DataAccess;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;

import Model.AuthToken;

import static org.junit.Assert.*;

/**
 * Created by emmag on 2/27/2017.
 */
public class AuthTokenDaoTest {
    DatabaseDao db;
    AuthTokenDao authTokenDao;
    AuthToken basic;
    AuthToken nullKey;
    AuthToken badTime;
    AuthToken outOfTime;
    AuthToken repeatedCode;
    AuthToken repeatedUser;

    @Before
    public void setUp() throws Exception {
        DatabaseDao.initialize();
        db = new DatabaseDao();
        authTokenDao = db.authTokenAccessor;
        Calendar cal = Calendar.getInstance();
        basic = new AuthToken("code", "username", cal.getTime());
        nullKey = new AuthToken(null, "un", cal.getTime());
        badTime = new AuthToken("blaze", "420", null);
        repeatedCode = new AuthToken("code", "someoneelse", cal.getTime());
        repeatedUser = new AuthToken("hello", "username", cal.getTime());
        cal.add(Calendar.HOUR, -2);
        outOfTime = new AuthToken("weston", "buddyboy", cal.getTime());
    }

    @After
    public void tearDown() {
        db.closeConnection(false);
    }


    @Test
    public void insertAuthToken() throws Exception {
        assertTrue(authTokenDao.insertAuthToken(basic));
        assertTrue(authTokenDao.insertAuthToken(repeatedUser));
        assertTrue(authTokenDao.insertAuthToken(outOfTime));
        assertFalse(authTokenDao.insertAuthToken(nullKey));
        //assertFalse(authTokenDao.insertAuthToken(badTime));
        assertFalse(authTokenDao.insertAuthToken(repeatedCode));
    }

    @Test
    public void checkAuthToken() throws Exception {
        insertAuthToken();
        assertEquals(basic, authTokenDao.checkAuthToken("code"));
        assertEquals(null, authTokenDao.checkAuthToken("blaze"));
        assertEquals(outOfTime, authTokenDao.checkAuthToken("weston"));
        assertEquals(repeatedUser, authTokenDao.checkAuthToken("hello"));

    }

    @Test
    public void update() throws Exception {
        insertAuthToken();
        assertTrue(authTokenDao.update(repeatedCode));
        assertEquals(repeatedCode, authTokenDao.checkAuthToken("code"));
    }

    @Test
    public void clearInactiveAuthTokens() throws Exception {
        insertAuthToken();
        assertTrue(authTokenDao.clearInactiveAuthTokens());
        assertEquals(null, authTokenDao.checkAuthToken("weston"));
    }

    /*@Test
    public void read() throws Exception {
        insertAuthToken();
        assertEquals(basic, authTokenDao.read("code"));
        assertEquals(null, authTokenDao.read("blaze"));
        assertEquals(repeatedUser, authTokenDao.read("hello"));
        assertEquals(outOfTime, authTokenDao.read("weston"));
    }*/

    @Test
    public void delete() throws Exception {
        insertAuthToken();
        assertTrue(authTokenDao.delete("code"));
        assertEquals(null, authTokenDao.checkAuthToken("code"));
    }

    @Test
    public void clearAuthTokens() throws Exception {
        assertTrue(authTokenDao.clearAuthTokens());
    }

}