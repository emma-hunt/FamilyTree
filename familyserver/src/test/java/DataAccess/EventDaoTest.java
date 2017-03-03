package DataAccess;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import Model.Event;
import Model.Person;

import static org.junit.Assert.*;

/**
 * Created by emmag on 2/27/2017.
 */
public class EventDaoTest {
    DatabaseDao db;
    EventDao eventDao;
    Event basic;
    Event sample;
    Event valid;
    Event nullID;
    Event nullField;
    Event badType;
    Event repeatedID;

    @Before
    public void setUp() throws Exception {
        DatabaseDao.initialize();
        db = new DatabaseDao();
        eventDao = db.eventAccessor;
        basic = new Event("eventid", "username", "person", 13.48, 18.9880, "USA", "DC", "Marriage", 1997);
        sample = new Event("ev97", "emmagh", "emma", 15.26, 17.00, "USA", "Rochester", "Birth", 1997);
        valid = new Event("ev19", "emmagh", "emma", 17.00, 3.98, "USA", "Provo", "Marriage", 2019);
        nullID = new Event(null, "un", "p", 9.1, 9.2, "cont", "city", "Christening", 1880);
        nullField = new Event("jiwt", null, "g", 97, 2.38, "england", "london", "Baptism", 7);
        badType = new Event("event", "name", "soul", 8, 9, "c", "c", "Decapitation", 1389);
        repeatedID = new Event("ev19", "emmagh", "emma", 87.97, 190.89, "Germany", "Berlin", "Death", 2019);

    }

    @After
    public void tearDown() throws Exception {
        db.closeConnection(false);
    }

    @Test
    public void insertEvent() throws Exception {
        assertTrue(eventDao.insertEvent(basic));
        assertTrue(eventDao.insertEvent(sample));
        assertTrue(eventDao.insertEvent(valid));
        assertFalse(eventDao.insertEvent(nullID));
        assertFalse(eventDao.insertEvent(nullField));
        assertFalse(eventDao.insertEvent(badType));
        assertFalse(eventDao.insertEvent(repeatedID));
    }

    @Test
    public void read() throws Exception {
        insertEvent();
        assertEquals(basic, eventDao.read("eventid"));
        assertEquals(sample, eventDao.read("ev97"));
        assertEquals(valid, eventDao.read("ev19"));
        assertEquals(null, eventDao.read("event"));
        assertNotEquals(repeatedID, eventDao.read("ev19"));

    }

    @Test
    public void update() throws Exception {
        insertEvent();
        assertTrue(eventDao.update(repeatedID));
        assertEquals(repeatedID, eventDao.read("ev19"));
    }

    @Test
    public void delete() throws Exception {
        insertEvent();
        assertTrue(eventDao.delete("eventid"));
        assertTrue(eventDao.delete("ev19"));
        assertEquals(null, eventDao.read("ev19"));
    }

    @Test
    public void deleteAllRelated() throws Exception {
        insertEvent();
        assertTrue(eventDao.deleteAllRelated("emmagh"));
        assertEquals(null, eventDao.read("ev19"));
        assertEquals(null, eventDao.read("ev97"));
    }

    @Test
    public void getAllRelatedEvents() throws Exception {
        insertEvent();
        ArrayList<Event> events = new ArrayList<>();
        events.add(sample);
        events.add(valid);
        assertEquals(events, eventDao.getAllRelatedEvents("emmagh"));
    }

    @Test
    public void clearEvents() throws Exception {
        assertTrue(eventDao.clearEvents());
    }

}