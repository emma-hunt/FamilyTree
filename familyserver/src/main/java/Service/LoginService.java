package Service;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import DataAccess.DatabaseDao;
import Model.AuthToken;
import Model.User;
import RequestResult.LoginRequest;
import RequestResult.LoginResult;

/**
 * Created by emmag on 2/14/2017.
 * Class to service a login request
 */

public class LoginService {
    /**
     * logs in user and returns an auth token
     * @param r login request
     * @return  login result
     */
    public LoginResult login(LoginRequest r){
        //System.out.println("now in login service");
        LoginResult result = null;
        DatabaseDao db = new DatabaseDao();
        String username = r.getUserName();
        String password = r.getPassword();
        User u = db.userAccessor.read(username);
        boolean success = false;
        if(u != null && u.getPassword().equals(password)){
            AuthToken token = createAuthToken(username);
            success = db.authTokenAccessor.insertAuthToken(token);
            result = new LoginResult(token.getAuth_code(), username, u.getPersonID());
        }
        else {
            result = new LoginResult("Invalid username or password. Please Try Again.");
        }
        db.closeConnection(success);
        return result;
    }

   /* public LoginResult activeLogin(User u, DatabaseDao db){
        LoginResult result = null;
        //DatabaseDao db = new DatabaseDao();
        //String username = r.getUserName();
        //String password = r.getPassword();
        //User u = db.userAccessor.read(username);
        boolean success = false;
       // if(u != null){
            AuthToken token = createAuthToken(u.getUserName());
            success = db.authTokenAccessor.insertAuthToken(token);
           // result = new LoginResult(token.getAuth_code(), username, u.getPersonID());
        //}
        //db.closeConnection(success);
        return result;
    }*/


    public AuthToken createAuthToken(String username) {
        UUID uuid = UUID.randomUUID();
        String authCode = uuid.toString();
        Calendar cal = Calendar.getInstance();
        Date timestamp = cal.getTime();
        AuthToken token = new AuthToken(authCode, username, timestamp);
        return token;
    }

}
