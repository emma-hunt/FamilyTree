package RequestResult;

import Model.Event;

/**
 * Created by emmag on 2/10/2017.
 * Class containing the single event result
 */
public class EventSingleResult implements Result{
    Event data;
    String message;

    public EventSingleResult(Event data) {
        this.data = data;
    }

    public EventSingleResult(String message) {
        this.message = message;
    }

    public EventSingleResult() {

    }

    public Event getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }
}
