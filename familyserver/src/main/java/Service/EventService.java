package Service;

import java.util.ArrayList;

import DataAccess.DatabaseDao;
import Model.AuthToken;
import Model.Event;
import Model.User;
import RequestResult.EventRequest;
import RequestResult.EventFamilyResult;
import RequestResult.EventSingleResult;

/**
 * Created by emmag on 2/14/2017.
 * class to service the events for a family call
 */

public class EventService {
    /**
     * returns all the events related to the current user's family
     * @param r event family request
     * @return  event family result
     */
    public EventFamilyResult family(EventRequest r) {
        EventFamilyResult result = null;
        boolean success = false;
        DatabaseDao db = new DatabaseDao();
        String auth = r.getAuth();
        AuthToken token = db.authTokenAccessor.checkAuthToken(auth);
        if(token != null) {
            User user = db.userAccessor.read(token.getUser());
            if(user != null) {
                ArrayList<Event> events = db.eventAccessor.getAllRelatedEvents(user.getUserName());
                if(events != null) {
                    result = new EventFamilyResult(events);
                    success = true;
                }
            }
        }
        else {
            result = new EventFamilyResult("Invalid authentication. Please login again.");
        }
        if(result == null) {
            result = new EventFamilyResult("Invalid event request. Please try again.");
        }
        db.closeConnection(success);
        return result;
    }

    /**
     * returns the single event being searched for
     * @param r request
     * @return single event result
     */
    public EventSingleResult single(EventRequest r){
        EventSingleResult result = null;
        boolean success = false;
        DatabaseDao db = new DatabaseDao();
        String auth = r.getAuth();
        AuthToken token = db.authTokenAccessor.checkAuthToken(auth);
        if(token != null) {
            Event event = db.eventAccessor.read(r.getEventID());
            if(event != null) {
                if(event.getDescendant().equals(token.getUser())) {
                    result = new EventSingleResult(event);
                    success = true;
                }
            }
        }
        else {
            result = new EventSingleResult("Invalid authentication. Please login again.");
        }
        if(result == null) {
            result = new EventSingleResult("Invalid event request. Please try again.");
        }
        db.closeConnection(success);
        return result;
    }

}
