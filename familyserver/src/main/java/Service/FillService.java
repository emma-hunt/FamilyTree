package Service;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.UUID;

import DataAccess.DatabaseDao;
import Model.Event;
import Model.Person;
import Model.User;
import RequestResult.FillRequest;
import RequestResult.FillResult;
import Resource.Location;
import Resource.LocationData;
import Resource.NameData;

/**
 * Created by emmag on 2/14/2017.
 * Class to service the fill request
 */

public class FillService {
    /**
     * fills the database with generated data for the user for the specified number of generations
     * @param r fill request
     * @return fill result
     */
    private LocationData locations;
    private NameData femaleNames;
    private NameData maleNames;
    private NameData lastNames;

    public FillResult fill(FillRequest r){
        FillResult result = null;
        boolean success = true;
        DatabaseDao db = new DatabaseDao();
        String username = r.getUsername();
        int generations = r.getGenerations();
        User user = db.userAccessor.read(username);
        if(user != null) {
            success = success && db.eventAccessor.deleteAllRelated(username);
            success = success && db.personAccessor.deleteAllRelated(username);
            success = success && generate(db, user, generations+1);
            if(success) {
                int peopleCreated = (int) (Math.pow(2, generations+1)) - 1;
                int eventsCreated = (peopleCreated * 4) - 1;
                result = new FillResult("Successfully added " + peopleCreated + " persons and " + eventsCreated + " events to the database.");
            }
        }
        else {
            success = false;
        }
        if(result == null) {
            result = new FillResult("Invalid fill. Please try again.");
        }
        db.closeConnection(success);
        return result;
    }

    public boolean generate(DatabaseDao db, User user, int generations) {
        boolean success = true;
        success = success && loadData();
        if(success) {
            ArrayList<Person> people = new ArrayList<>();
            ArrayList<Event> events = new ArrayList<>();
            int baseYear = 2017 - (int) (Math.random() * 5) - 24;
            Person userPerson = generatePerson(people, events, user.getUserName(), user.getGender(), baseYear, generations);
            userPerson.setFirstName(user.getFirstName());
            userPerson.setLastName(user.getLastName());
            userPerson.setPersonID(user.getPersonID());

            for(Person p : people) {
                success = success && db.personAccessor.createPerson(p);
            }
            for(Event e : events) {
                success = success && db.eventAccessor.insertEvent(e);
            }
        }
        return success;
    }

    private Person generatePerson(ArrayList<Person> people, ArrayList<Event> events, String descendant, char gender, int birth, int gensLeft) {
        if(gensLeft == 0) {
            return null;
        }
        String fn;
        String ln;
        if(gender == 'f'){
            fn = femaleNames.generateName();
        }
        else {
            fn = maleNames.generateName();
        }
        ln = lastNames.generateName();

        Person father = generatePerson(people, events, descendant, 'm', birth - (int)(Math.random() * 6 + 24), gensLeft - 1);
        Person mother = generatePerson(people, events, descendant, 'f', birth - (int)(Math.random() * 6 + 24), gensLeft - 1);
        Person myself;
        if(mother != null && father != null) {
            father.setSpouse(mother.getPersonID());
            mother.setSpouse(father.getPersonID());
            generateMarriage(events, descendant, mother.getPersonID(), father.getPersonID(), birth);
            myself = new Person(createID(), descendant, fn, ln, gender, father.getPersonID(), mother.getPersonID());
        }
        else {
            myself = new Person(createID(), descendant, fn, ln, gender, null, null);
        }
        people.add(myself);
        generateEvents(events, myself.getPersonID(), descendant, birth);
        return myself;
    }

    private void generateEvents(ArrayList<Event> events, String personID, String descendant, int baseYear){
        //Event[] events = new Event[];
        Location loc = locations.getLocation();
        Event birth = new Event(createID(), descendant, personID, loc.getDecLat(), loc.getDecLon(),
                loc.getCountry(), loc.getCity(), "Birth", baseYear);
        loc = locations.getLocation();
        Event baptism = new Event(createID(), descendant, personID, loc.getDecLat(), loc.getDecLon(),
                loc.getCountry(), loc.getCity(), "Baptism", baseYear + 8);
        loc = locations.getLocation();
        int deathYear = baseYear + (int)(Math.random() * 20 + 65);
        Event death = new Event(createID(), descendant, personID, loc.getDecLat(), loc.getDecLon(),
                loc.getCountry(), loc.getCity(), "Death", deathYear);
        events.add(birth);
        events.add(baptism);
        events.add(death);
    }

    private void generateMarriage(ArrayList<Event> events, String descendant, String mother, String father, int baseYear) {
        int year = baseYear + (int) (Math.random() * 5) + 1;
        Location loc = locations.getLocation();
        events.add(new Event(createID(), descendant, mother, loc.getDecLat(), loc.getDecLon(), loc.getCountry(), loc.getCity(), "Marriage", year));
        events.add(new Event(createID(), descendant, father, loc.getDecLat(), loc.getDecLon(), loc.getCountry(), loc.getCity(), "Marriage", year));
    }

    private String createID() {
        UUID uuid = UUID.randomUUID();
        String id = uuid.toString();
        return id;
    }

    private boolean loadData() {
        Reader reader = null;
        Gson gson = new Gson();
        try {
            reader = new FileReader("./ResourceFiles/locations.json");
            locations = gson.fromJson(reader, LocationData.class);
            reader = new FileReader("./ResourceFiles/fnames.json");
            femaleNames = gson.fromJson(reader, NameData.class);
            reader = new FileReader("./ResourceFiles/mnames.json");
            maleNames = gson.fromJson(reader, NameData.class);
            reader = new FileReader("./ResourceFiles/snames.json");
            lastNames = gson.fromJson(reader, NameData.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
