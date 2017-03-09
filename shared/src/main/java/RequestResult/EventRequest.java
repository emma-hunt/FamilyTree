package RequestResult;

/**
 * Created by emmag on 2/10/2017.
 * Class containing the family events request
 */
public class EventRequest {
    String auth;
    String eventID;

    public EventRequest(String auth, String eventID) {
        this.auth = auth;
        this.eventID = eventID;
    }

    public String getAuth() {
        return auth;
    }

    public String getEventID() {
        return eventID;
    }
}
