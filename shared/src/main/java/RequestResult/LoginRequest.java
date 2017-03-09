package RequestResult;

/**
 * Created by emmag on 2/10/2017.
 * Class containing the login request
 */
public class LoginRequest {
    private String userName;
    private String password;

    public LoginRequest(String username, String password) {
        userName = username;
        this.password = password;
    }

    public LoginRequest() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
