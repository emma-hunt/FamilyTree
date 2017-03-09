package RequestResult;

import Model.Person;

/**
 * Created by emmag on 3/8/2017.
 */

public class PersonSingleResult implements Result {
    Person data;
    String message;

    public PersonSingleResult(Person data) {
        this.data = data;
    }

    public PersonSingleResult(String message) {
        this.message = message;
    }

    public PersonSingleResult() {

    }

    public Person getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }
}
