package RequestResult;

/**
 * Created by emmag on 2/10/2017.
 * Class containing a register result
 */
public class RegisterResult implements Result{
    private String authToken;
    private String userName;
    private String personID;
    private String message;

    public RegisterResult(String auth, String user, String id) {
        authToken = auth;
        userName = user;
        personID = id;
    }

    public RegisterResult(String message) {
        this.message = message;
    }

    public RegisterResult() {

    }

    public String getAuthToken() {
        return authToken;
    }

    public String getUserName() {
        return userName;
    }

    public String getPersonID() {
        return personID;
    }

    public String getMessage() {
        return message;
    }
}
