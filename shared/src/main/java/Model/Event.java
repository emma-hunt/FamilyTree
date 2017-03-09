package Model;

/**
 * Created by emmag on 2/10/2017.
 * Event Class, corresponding to the Event table in the database
 *
 * @author emmag
 * @version 1.0 Feb. 10 2017
 */

public class Event {
    /** String containing the unique event id */
    private String eventID;
    /** String containing the username of the descendant to which the event relates */
    private String descendant; //references user object
    /** String containing the personID id of the personID to whom this event is about */
    private String personID;
    /** double containing the latitude of the event */
    private double latitude;
    /** double containing the longitude of the event */
    private double longitude;
    /** string containing the country in which the event took place */
    private String country;
    /** string containing the city in which the event took place */
    private String city;
    /** integer containing the year in which the event took place */
    private int year;
    /** string containing the event type, must be Birth, Baptism, Christening, Marriage, Death */
    private String eventType;

    /**
     * constructor for the event object
     * @param eventID      event's unique id
     * @param descendant    id of user to which the event belongs
     * @param personID        id of personID to which the event belongs
     * @param latitude      latitude of event location
     * @param longitude     longitude of event location
     * @param country       country where event occurs
     * @param city          city where event occurs
     * @param year          year in which the event occurs
     * @param eventType    type of event, Birth, Baptism, Christening, Marriage, Death
     */
    public Event(String eventID, String descendant, String personID, double latitude, double longitude, String country, String city, String eventType, int year) {
        this.eventID = eventID;
        this.descendant = descendant;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.year = year;
        this.eventType = eventType;
    }

    /**
     * gets event id
     * @return eventID
     */
    public String getEventID() {
        return eventID;
    }

    /**
     * gets descendant's username
     * @return  descendant
     */
    public String getDescendant() {
        return descendant;
    }

    /**
     * gets personID's id whom the event is about
     * @return personID
     */
    public String getPersonID() {
        return personID;
    }

    /**
     * gets the latitude of the event
     * @return latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * gets the longitude of the event
     * @return longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * gets the country in which the event occurred
     * @return country
     */
    public String getCountry() {
        return country;
    }

    /**
     * gets the city in which the event occurred
     * @return city
     */
    public String getCity() {
        return city;
    }

    /**
     * gets the year in which the event occurred
     * @return year
     */
    public int getYear() {
        return year;
    }

    /**
     * gets the event type, either Birth, Baptism, Christening, Marriage, or Death
     * @return event
     */
    public String getEventType() {
        return eventType;
    }

    @Override
    public boolean equals(Object o) {
        //return super.equals(o);
        if(this == o) {
            return true;
        }
        if(this == null && o != null) {
            return false;
        }
        if(this != null && o == null) {
            return false;
        }
        if(this.getClass() != o.getClass()){
            return false;
        }
        Event that = (Event) o;
        if(!this.getEventID().equals(that.getEventID())){
            return false;
        }
        if(!this.getDescendant().equals(that.getDescendant())){
            return false;
        }
        if(!this.getPersonID().equals(that.getPersonID())){
            return false;
        }
        if(this.getLatitude() != that.getLatitude()) {
            return false;
        }
        if(that.getLongitude() != that.getLongitude()){
            return false;
        }
        if(!this.getCity().equals(that.getCity())) {
            return false;
        }
        if(!this.getCountry().equals(that.getCountry())){
            return false;
        }
        if (!this.getEventType().equals(that.getEventType())) {
            return false;
        }
        if(this.getYear() != that.getYear()){
            return false;
        }
        return true;
    }
}
