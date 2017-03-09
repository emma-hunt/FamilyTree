package Service;

import DataAccess.DatabaseDao;
import Model.Event;
import Model.Person;
import Model.User;
import RequestResult.LoadRequest;
import RequestResult.LoadResult;

/**
 * Created by emmag on 2/14/2017.
 * Class to service the load request
 */

public class LoadService {
    /**
     * clears the database, then loads the user, person, and event data
     * @param r load request
     * @return  load result
     */
    public LoadResult load(LoadRequest r){
        LoadResult result = null;
        DatabaseDao db = new DatabaseDao();
        boolean success = true;
        success = success && db.clearDatabase();
        User[] users = r.getData().getUsers();
        int numUsers = 0;
        Person[] persons = r.getData().getPersons();
        int numPersons = 0;
        Event[] events = r.getData().getEvents();
        int numEvents = 0;
        if(users != null) {
            for (User u : users) {
                success = success && db.userAccessor.createUser(u);
                numUsers++;
            }
        }
        if(persons != null) {
            for (Person p : persons) {
                success = success && db.personAccessor.createPerson(p);
                numPersons++;
            }
        }
        if(events != null) {
            for (Event e : events) {
                success = success && db.eventAccessor.insertEvent(e);
                numEvents++;
            }
        }
        if(success) {
            String message = "Successfully added " + numUsers + " users, " + numPersons + " persons, and " + numEvents + " events to the database.";
            result = new LoadResult(message);
        }
        else {
            result = new LoadResult("Invalid load. Please try again");
        }
        db.closeConnection(success);
        return result;
    }
}
