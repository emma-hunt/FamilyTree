package ServerProxy;

import RequestResult.ClearResult;
import RequestResult.EventFamilyRequest;
import RequestResult.EventFamilyResult;
import RequestResult.EventSingleRequest;
import RequestResult.EventSingleResult;
import RequestResult.FillRequest;
import RequestResult.FillResult;
import RequestResult.LoadRequest;
import RequestResult.LoadResult;
import RequestResult.LoginRequest;
import RequestResult.LoginResult;
import RequestResult.PersonFamilyRequest;
import RequestResult.PersonFamilyResult;
import RequestResult.PersonSingleRequest;
import RequestResult.PersonSingleResult;
import RequestResult.RegisterRequest;
import RequestResult.RegisterResult;

/**
 * Created by emmag on 2/10/2017.
 */

public class ServerProxy {
    /**
     *  Creates a new user account, generates 4 generations of ancestor data for the new
     *  user, logs the user in, and returns an auth token.
     * @param r register request
     * @return register result
     */
    public RegisterResult register(RegisterRequest r){
        return null;
    }

    /**
     * logs in user and returns an auth token
     * @param r login request
     * @return  login result
     */
    public LoginResult login(LoginRequest r){
        return null;
    }

    /**
     * clears all data from the database
     * @return clear result
     */
    public ClearResult clear(){
        return null;
    }

    /**
     * fills the database with generated data for the user for the specified number of generations
     * @param r fill request
     * @return fill result
     */
    public FillResult fill(FillRequest r){
        return null;
    }

    /**
     * clears the database, then loads the user, person, and event data
     * @param r load request
     * @return  load result
     */
    public LoadResult load(LoadRequest r){
        return null;
    }

    /**
     * returns single person with specified id
     * @param r person single request
     * @return  person single result
     */
    public PersonSingleResult personSingle(PersonSingleRequest r){
        return null;
    }

    /**
     * returns all the family members of the current user
     * @param r person family request
     * @return  person family result
     */
    public PersonFamilyResult personFamily(PersonFamilyRequest r) {
        return null;
    }

    /**
     * returns a single event with specified id
     * @param r event single request
     * @return event single result
     */
    public EventSingleResult eventSingle(EventSingleRequest r) {
        return null;
    }

    /**
     * returns all the events related to the current user's family
     * @param r event family request
     * @return  event family result
     */
    public EventFamilyResult eventFamily(EventFamilyRequest r) {
        return null;
    }

}
