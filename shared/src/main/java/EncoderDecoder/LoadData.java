package EncoderDecoder;

import Model.Event;
import Model.Person;
import Model.User;

/**
 * Created by emmag on 3/7/2017.
 */

public class LoadData {
    User[] users;
    Person[] persons;
    Event[]  events;

    public LoadData(User[] users, Person[] persons, Event[] events) {
        this.users = users;
        this.persons = persons;
        this.events = events;
    }

    public User[] getUsers() {
        return users;
    }

    public void setUsers(User[] users) {
        this.users = users;
    }

    public Person[] getPersons() {
        return persons;
    }

    public void setPersons(Person[] persons) {
        this.persons = persons;
    }

    public Event[] getEvents() {
        return events;
    }

    public void setEvents(Event[] events) {
        this.events = events;
    }
}
