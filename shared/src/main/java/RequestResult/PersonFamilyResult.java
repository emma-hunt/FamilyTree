package RequestResult;

import java.util.ArrayList;

import Model.Person;

/**
 * Created by emmag on 3/6/2017.
 */

public class PersonFamilyResult implements Result{
    ArrayList<Person> data;
    String message;

    public PersonFamilyResult(ArrayList<Person> data) {
        this.data = data;
    }

    public PersonFamilyResult(String message) {
        this.message = message;
    }

    public PersonFamilyResult() {

    }

    public ArrayList<Person> getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }
}
