package Service;

import java.util.UUID;

import DataAccess.DatabaseDao;
import Model.AuthToken;
import Model.User;
import RequestResult.RegisterRequest;
import RequestResult.RegisterResult;

/**
 * Created by emmag on 2/14/2017.
 * Class to service the register request
 */

public class RegisterService {
    /**
     *  Creates a new user account, generates 4 generations of ancestor data for the new
     *  user, logs the user in, and returns an auth token.
     * @param r register request
     * @return register result
     */
    public RegisterResult register(RegisterRequest r){
        RegisterResult result = null;
        boolean success = false;
        DatabaseDao db = new DatabaseDao();
        //check to see if user exists
        if(db.userAccessor.read(r.getUsername()) == null) {
            success = true;
            //create new user
            User user = new User(r.getUsername(), r.getPassword(), r.getEmail(), r.getFirstname(), r.getLastname(), r.getGender().charAt(0), createID());
            success = success && db.userAccessor.createUser(user);
            //generate fake data
            FillService fill = new FillService();
            success = success && fill.generate(db, user, 4);
            //login new user
            LoginService login = new LoginService();
            AuthToken auth = login.createAuthToken(user.getUserName());
            success = success && db.authTokenAccessor.insertAuthToken(auth);
            if(success){
                result = new RegisterResult(auth.getAuth_code(), user.getUserName(), user.getPersonID());
            }
            else {
                result = new RegisterResult("Invalid register information. Please Try Again.");
            }
        }
        else {
            success = false;
            result = new RegisterResult("Invalid register information. Please Try Again.");
        }
        db.closeConnection(success);
        //db.closeConnection(true);
        return result;
    }


    private String createID() {
        UUID uuid = UUID.randomUUID();
        String id = uuid.toString();
        return id;
    }
}
