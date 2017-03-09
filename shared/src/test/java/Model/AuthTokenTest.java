package Model;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by emmag on 2/24/2017.
 */
public class AuthTokenTest {
    private AuthToken legal;
    private AuthToken empty;
    private AuthToken badDate;
    private Date date;

    @Before
    public void setUp(){
        Calendar cal = Calendar.getInstance();
        date = cal.getTime();
        legal = new AuthToken("hi390sj", "emma", date);
        empty = new AuthToken(null, null, null);
    }

    @Test
    public void getAuth_code() throws Exception {
        assertEquals("hi390sj", legal.getAuth_code());
        assertEquals(null, empty.getAuth_code());
        assertNotEquals("code", legal.getAuth_code());
        assertNotEquals("code", empty.getAuth_code());
    }

    @Test
    public void getUser() throws Exception {
        assertEquals("emma", legal.getUser());
        assertEquals(null, empty.getUser());
        assertNotEquals("name", empty.getUser());
    }

    @Test
    public void getTime_stamp() throws Exception {
        assertEquals(date, legal.getTime_stamp());
        assertEquals(null, empty.getTime_stamp());
        assertNotEquals(date, empty.getTime_stamp());
    }

    @Test
    public void failTest() throws Exception {
        assertEquals(null, "hi");
    }

}