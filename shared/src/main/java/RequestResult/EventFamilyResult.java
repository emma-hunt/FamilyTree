package RequestResult;

import java.util.ArrayList;

import Model.Event;

/**
 * Created by emmag on 2/10/2017.
 * Class containing the Family Event result
 */
public class EventFamilyResult implements Result{
    ArrayList<Event> data;
    String message;

    public EventFamilyResult(ArrayList<Event> data) {
        this.data = data;
    }

    public EventFamilyResult(String message) {
        this.message = message;
    }

    public EventFamilyResult() {

    }

    public ArrayList<Event> getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }
}
