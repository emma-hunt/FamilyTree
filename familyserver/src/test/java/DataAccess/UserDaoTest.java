package DataAccess;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Model.User;

import static org.junit.Assert.*;

/**
 * Created by emmag on 2/24/2017.
 */
public class UserDaoTest {
    DatabaseDao db;
    UserDao userDao;
    User genericUser;
    User legalUser;
    User badGender;
    User nullKey;
    User nullField;
    User repeatedUsername;

    @Before
    public void setUp() throws Exception {
        DatabaseDao.initialize();
        db = new DatabaseDao();
        userDao = db.userAccessor;
        genericUser = new User("username", "password", "email@gmail.com", "First", "Last", 'm', "personid");
        legalUser = new User("emmahunt", "secret", "emma.g@gmail.com", "emma", "hunt", 'f', "hi27f");
        badGender = new User("name", "word", "@email", "fn", "ln", 't', "id");
        nullKey = new User(null, "pw", "em", "fn", "ln", 'f', "id");
        nullField = new User("unique", "pw", "em", null, "ln", 'f', "id");
        repeatedUsername = new User("username", "pw", "@", "fn", "ln", 'm', "id");
        //createUser();
    }

    @Test
    public void createUser() throws Exception {
        assertTrue(userDao.createUser(genericUser));
        assertTrue(userDao.createUser(legalUser));
        assertFalse(userDao.createUser(badGender));
        assertFalse(userDao.createUser(nullKey));
        assertFalse(userDao.createUser(nullField));
        assertFalse(userDao.createUser(repeatedUsername));
    }

    @Test
    public void read() throws Exception {
        createUser();
       // assertEquals(genericUser, userDao.read("username"));
        assertTrue(genericUser.equals(userDao.read("username")));
        assertEquals(legalUser, userDao.read("emmahunt"));
        assertEquals(null, userDao.read("name"));
        assertNotEquals(badGender, userDao.read("name"));
        assertEquals(null, userDao.read("pw"));
        assertNotEquals(nullKey, userDao.read("pw"));
        assertEquals(null, userDao.read("unique"));
        assertNotEquals(repeatedUsername, userDao.read("username"));
    }

    @Test
    public void update() throws Exception {
        createUser();
        assertTrue(userDao.update(repeatedUsername));
        //assertFalse(userDao.update(badGender));
    }

    @Test
    public void delete() throws Exception {
        assertTrue(userDao.delete("username"));
        //assertFalse(userDao.delete("username"));
        //assertFalse(userDao.delete("unique"));
    }

    @Test
    public void clearUsers() throws Exception {
        assertTrue(userDao.clearUsers());
    }

    @After
    public void cleanUp() {
        db.clearDatabase();
        db.closeConnection(false);
    }

}