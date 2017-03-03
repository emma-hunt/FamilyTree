package DataAccess;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import javax.xml.crypto.Data;

import Model.Person;

import static org.junit.Assert.*;

/**
 * Created by emmag on 2/27/2017.
 */
public class PersonDaoTest {
    DatabaseDao db;
    PersonDao personDao;
    Person basic;
    Person sample;
    Person nullRelatives;
    Person nullID;
    Person nullUser;
    Person badGender;
    Person repeatedID;


    @Before
    public void setUp() throws Exception {
        DatabaseDao.initialize();
        db = new DatabaseDao();
        personDao = db.personAccessor;
        basic = new Person("personid", "username", "first", "last", 'm', "father", "mother", "spouse");
        sample = new Person("egh19", "emmagh", "emma", "hunt", 'f', "galen", "lori", "tanner");
        nullRelatives = new Person("lwh14", "emmagh", "weston", "hunt", 'm', null, null, null);
        nullID = new Person(null, "name", "f", "l", 'f', null, null, null);
        nullUser = new Person("id", null, "fn", "ln", 'm', null, null, null);
        badGender = new Person("per", "usn", "fn", "ln", 'g', null, null, null);
        repeatedID = new Person("personid", "realuser", "john", "doe", 'm', "jackdoe", "janedoe", null);

    }

    @After
    public void tearDown() throws Exception {
        db.closeConnection(false);
    }

    @Test
    public void createPerson() throws Exception {
        assertTrue(personDao.createPerson(basic));
        assertTrue(personDao.createPerson(sample));
        assertTrue(personDao.createPerson(nullRelatives));
        assertFalse(personDao.createPerson(nullUser));
        assertFalse(personDao.createPerson(nullID));
        assertFalse(personDao.createPerson(badGender));
        assertFalse(personDao.createPerson(repeatedID));
    }

    @Test
    public void read() throws Exception {
        createPerson();
        assertEquals(basic, personDao.read("personid"));
        assertEquals(sample, personDao.read("egh19"));
        assertEquals(nullRelatives, personDao.read("lwh14"));
        assertEquals(null, personDao.read("id"));
        assertEquals(null, personDao.read("per"));
        assertNotEquals(repeatedID, personDao.read("personid"));
    }

    @Test
    public void update() throws Exception {
        createPerson();
        assertTrue(personDao.update(repeatedID));

    }

    @Test
    public void delete() throws Exception {
        createPerson();
        assertTrue(personDao.delete("personid"));
        assertTrue(personDao.delete("egh19"));
        assertTrue(personDao.delete("lwh14"));
        assertEquals(null, personDao.read("personid"));
    }

    @Test
    public void deleteAllRelated() throws Exception {
        createPerson();
        assertTrue(personDao.deleteAllRelated("emmagh"));
        assertEquals(null, personDao.read("egh19"));
        assertEquals(null, personDao.read("lwh14"));
    }

    @Test
    public void getAllRelatedPeople() throws Exception {
        createPerson();
        ArrayList<Person> peeps = new ArrayList<>();
        peeps.add(sample);
        peeps.add(nullRelatives);
        //peeps = personDao.getAllRelatedPeople("emmagh");
        //assertEquals(sample, peeps.get(0));
        //assertEquals(nullRelatives, peeps.get(1));
        assertEquals(peeps, personDao.getAllRelatedPeople("emmagh"));

    }

    @Test
    public void clearPeople() throws Exception {
        assertTrue(personDao.clearPeople());

    }

}