package Service;

import java.util.ArrayList;

import DataAccess.DatabaseDao;
import Model.AuthToken;
import Model.Person;
import Model.User;
import RequestResult.PersonFamilyResult;
import RequestResult.PersonRequest;
import RequestResult.PersonSingleResult;

/**
 * Created by emmag on 2/14/2017.
 * Class to service a request for a family of persons
 */

public class PersonService {
    /**
     * returns all the family members of the current user
     * @param r person family request
     * @return  person family result
     */
    public PersonFamilyResult family(PersonRequest r) {
        PersonFamilyResult result = null;
        boolean success = false;
        DatabaseDao db = new DatabaseDao();
        String auth = r.getAuth();
        AuthToken token = db.authTokenAccessor.checkAuthToken(auth);
        if(token != null) {
            User user = db.userAccessor.read(token.getUser());
            if(user != null) {
                ArrayList<Person> people = db.personAccessor.getAllRelatedPeople(user.getUserName());
                if(people != null) {
                    result = new PersonFamilyResult(people);
                    success = true;
                }
            }
        }
        else {
            result = new PersonFamilyResult("Invalid authentication. Please login again.");
        }
        if(result == null) {
            result = new PersonFamilyResult("Invalid person request. Please try again.");
        }
        db.closeConnection(success);
        return result;
    }

    /**
     * returns the single person being searched for
     * @param r request
     * @return single person result
     */
    public PersonSingleResult single(PersonRequest r){
        PersonSingleResult result = null;
        boolean success = false;
        DatabaseDao db = new DatabaseDao();
        String auth = r.getAuth();
        AuthToken token = db.authTokenAccessor.checkAuthToken(auth);
        if(token != null) {
            Person person = db.personAccessor.read(r.getPersonID());
            if(person != null) {
                if(person.getDescendant().equals(token.getUser())) {
                    result = new PersonSingleResult(person);
                    success = true;
                }
            }
        }
        else {
            result = new PersonSingleResult("Invalid authentication. Please login again.");
        }
        if(result == null) {
            result = new PersonSingleResult("Invalid person request. Please try again.");
        }
        db.closeConnection(success);
        return result;
    }


}
