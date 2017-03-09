package RequestResult;

/**
 * Created by emmag on 2/10/2017.
 * class containing the login result
 */
public class LoginResult implements Result{
    private String authToken;
    private String userName;
    private String personID;
    private String message;

    public LoginResult(String authToken, String userName, String personID) {
        this.authToken = authToken;
        this.userName = userName;
        this.personID = personID;
    }

    public LoginResult(String message) {
        this.message = message;
    }

    public LoginResult() {

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
