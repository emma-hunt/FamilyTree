package ServerProxy;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import EncoderDecoder.LoadData;
import Model.Event;
import Model.Person;
import Model.User;
import RequestResult.ClearResult;
import RequestResult.EventFamilyResult;
import RequestResult.EventRequest;
import RequestResult.EventSingleResult;
import RequestResult.FillRequest;
import RequestResult.FillResult;
import RequestResult.LoadRequest;
import RequestResult.LoadResult;
import RequestResult.LoginRequest;
import RequestResult.LoginResult;
import RequestResult.PersonFamilyResult;
import RequestResult.PersonRequest;
import RequestResult.PersonSingleResult;
import RequestResult.RegisterRequest;
import RequestResult.RegisterResult;
import RequestResult.Result;

import static org.junit.Assert.*;

/**
 * Created by grace97 on 3/9/17.
 */
public class ServerProxyTest {

    ServerProxy proxy;

    @Before
    public void setUp() throws Exception {
        proxy = new ServerProxy();
    }

    @After
    public void tearDown() throws Exception {
        Result result = proxy.clear();
        assertEquals(ClearResult.class, result.getClass());
        ClearResult cleared = (ClearResult) result;
        assertEquals("Clear succeeded.", cleared.getMessage());
    }

    @Test
    public void setServerHost() throws Exception {
        proxy.setServerPort("9090");
        Assert.assertEquals("9090", proxy.getServerPort());
        proxy.setServerPort("8080");
        Assert.assertEquals("8080", proxy.getServerPort());
    }

    @Test
    public void setServerPort() throws Exception {
        proxy.setServerHost("08.390.089");
        Assert.assertEquals("08.390.089", proxy.getServerHost());
        proxy.setServerHost("localhost");
        Assert.assertEquals("localhost", proxy.getServerHost());

    }

    @Test
    public void register() throws Exception {
        RegisterRequest baseReq = new RegisterRequest("username", "password", "email", "first", "last", "f");
        RegisterRequest failedReq = new RegisterRequest(null, "pw", "em", "fn", "ln", "f");
        RegisterRequest badGender = new RegisterRequest("user", "word", "gmail", "jane", "las", "j");
        RegisterResult baseRes = (RegisterResult) proxy.register(baseReq);
        assertEquals(null, baseRes.getMessage());
        assertEquals("username", baseRes.getUserName());
        RegisterResult repeatedRes = (RegisterResult) proxy.register(baseReq);
        assertEquals(null, repeatedRes.getUserName());
        assertEquals(null, repeatedRes.getAuthToken());
        assertNotEquals(null, repeatedRes.getMessage());
        RegisterResult failRes = (RegisterResult) proxy.register(failedReq);
        assertNotEquals(null, failRes.getMessage());
        RegisterResult badGenRes = (RegisterResult) proxy.register(badGender);
        assertNotEquals(null , badGenRes.getMessage());
    }

    @Test
    public void login() throws Exception {
        RegisterRequest baseReq = new RegisterRequest("username", "password", "email", "first", "last", "f");
        RegisterResult baseRes = (RegisterResult) proxy.register(baseReq);
        assertEquals(null, baseRes.getMessage());
        LoginRequest validReq = new LoginRequest("username", "password");
        LoginResult validRes = (LoginResult) proxy.login(validReq);
        assertEquals("username", validRes.getUserName());
        assertEquals(null, validRes.getMessage());
        LoginRequest invalidReq = new LoginRequest("hacker", "evil");
        LoginResult invalidRes = (LoginResult) proxy.login(invalidReq);
        assertEquals(null, invalidRes.getUserName());
        assertNotEquals(null, invalidRes.getMessage());
    }

    @Test
    public void clear() throws Exception {
        Result r = proxy.clear();
        assertEquals(ClearResult.class, r.getClass());
    }

    @Test
    public void fill() throws Exception {
        RegisterRequest baseReq = new RegisterRequest("username", "password", "email", "first", "last", "f");
        RegisterResult baseRes = (RegisterResult) proxy.register(baseReq);
        assertEquals(null, baseRes.getMessage());

        FillRequest validReq = new FillRequest("username", 2);
        FillResult validRes = (FillResult) proxy.fill(validReq);
        assertEquals("Successfully added 7 persons and 27 events to the database.", validRes.getMessage());
        FillRequest userOnlyReq = new FillRequest("username");
        FillResult userOnlyRes = (FillResult) proxy.fill(userOnlyReq);
        assertEquals("Successfully added 31 persons and 123 events to the database.", userOnlyRes.getMessage());
        FillRequest illegalReq = new FillRequest("joe");
        FillResult illegalRes = (FillResult) proxy.fill(illegalReq);
        assertEquals("Invalid fill. Please try again.", illegalRes.getMessage());
    }

    @Test
    public void load() throws Exception {
        User user = new User("username", "password", "email", "emma", "hunt", 'f',"id");
        Person mother = new Person("momma", "username", "lori", "hunt", 'f', null, null, "daddyO");
        Person father = new Person("daddyO", "username", "galen", "hunt", 'm', null, null, "momma");
        Person myself = new Person("id", "username", "emma", "hunt", 'f', "momma", "daddyO", null);
        Event birth = new Event("birth", "username", "id", 14.90, 25.38, "USA", "Rochester", "Birth", 1997);
        Event baptism = new Event("baptism", "username", "id", 38.89, 17.22, "USA", "Seattle", "Baptism", 2003);
        User[]users = new User[] {user};
        Person[] persons = new Person[] {mother, father, myself};
        Event[] events = new Event[] {birth, baptism};
        LoadData data = new LoadData(users, persons, events);

        LoadRequest loadRes = new LoadRequest(data);

        LoadResult result = (LoadResult) proxy.load(loadRes);
        assertNotEquals(null, result.getMessage());
        assertEquals("Successfully added 1 users, 3 persons, and 2 events to the database.", result.getMessage());

    }

    @Test
    public void person() throws Exception {
        RegisterRequest baseReq = new RegisterRequest("username", "password", "email", "first", "last", "f");
        RegisterResult baseRes = (RegisterResult) proxy.register(baseReq);
        assertEquals(null, baseRes.getMessage());
        assertNotEquals(null, baseRes.getAuthToken());
        String auth = baseRes.getAuthToken();
        assertNotEquals(null, baseRes.getPersonID());
        String id = baseRes.getPersonID();

        PersonRequest legalFam = new PersonRequest(auth, null);
        PersonFamilyResult famRes = (PersonFamilyResult) proxy.person(legalFam);
        assertEquals(null, famRes.getMessage());
        assertNotEquals(null, famRes.getData());

        PersonRequest legalSin = new PersonRequest(auth, id);
        PersonSingleResult sinRes = (PersonSingleResult) proxy.person(legalSin);
        assertEquals(null, sinRes.getMessage());
        assertNotEquals(null, sinRes.getData());

        PersonRequest ilPerson = new PersonRequest(auth, "dawg");
        PersonSingleResult ilPerRes = (PersonSingleResult) proxy.person(ilPerson);
        assertEquals(null, ilPerRes.getData());
        assertEquals("Invalid person request. Please try again.", ilPerRes.getMessage());

        PersonRequest illegalSin = new PersonRequest("code", id);
        PersonSingleResult illegalSinRes = (PersonSingleResult) proxy.person(illegalSin);
        assertEquals(null, illegalSinRes.getData());
        assertEquals("Invalid authentication. Please login again.", illegalSinRes.getMessage());

        PersonRequest illegalFam = new PersonRequest("hello", null);
        PersonFamilyResult ilFamRes = (PersonFamilyResult) proxy.person(illegalFam);
        assertEquals(null, ilFamRes.getData());
        assertEquals("Invalid authentication. Please login again.", ilFamRes.getMessage());

    }

    @Test
    public void event() throws Exception {
        RegisterRequest baseReq = new RegisterRequest("username", "password", "email", "first", "last", "f");
        RegisterResult baseRes = (RegisterResult) proxy.register(baseReq);
        assertEquals(null, baseRes.getMessage());
        assertNotEquals(null, baseRes.getAuthToken());
        String auth = baseRes.getAuthToken();

        EventRequest legalFam = new EventRequest(auth, null);
        EventFamilyResult famRes = (EventFamilyResult) proxy.event(legalFam);
        assertEquals(null, famRes.getMessage());
        assertNotEquals(null, famRes.getData());
        ServerProxy proxy;

    }

}