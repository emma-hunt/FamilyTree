package Service;

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
        //todo remember to create a person for each user in the same transaction
        return null;
    }

}
