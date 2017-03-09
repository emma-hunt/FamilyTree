package RequestResult;

/**
 * Created by emmag on 2/10/2017.
 * Class containing a single person request
 */
public class PersonRequest {
    String auth;
    String personID;

    public PersonRequest(String auth, String personID) {
        this.auth = auth;
        this.personID = personID;
    }

    public String getPersonID() {
        return personID;
    }

    public String getAuth() {
        return auth;
    }
}
