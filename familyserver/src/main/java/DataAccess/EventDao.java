package DataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Model.Event;

/**
 * Created by emmag on 2/10/2017.
 * Class to access the Event table of the database
 */

public class EventDao {
    Connection connection;

    public EventDao(Connection conn) {
        this.connection = conn;
    }
    /**
     * creates an Event in the database
     * @param ev Event object to add to database
     * @return boolean success in adding event
     */
    public boolean insertEvent(Event ev){
        try {
            Statement stat = connection.createStatement();
            PreparedStatement prep = connection.prepareStatement("insert into event values (?, ?, ?, ?, ?, ?, ?, ?, ?);");
            prep.setString(1, ev.getEventID());
            prep.setString(2, ev.getDescendant());
            prep.setString(3, ev.getPersonID());
            prep.setString(4, String.valueOf(ev.getLatitude()));
            prep.setString(5, String.valueOf(ev.getLongitude()));
            prep.setString(6, ev.getCountry());
            prep.setString(7, ev.getCity());
            prep.setString(8, ev.getEventType());
            prep.setString(9, String.valueOf(ev.getYear()));
            prep.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * reads an event from the database for the given id and stores info in an Event object
     * @param event_id  id of event to be read from database
     * @return event object containing the read in information
     */
    public Event read(String event_id) {
        Event ev = null;
        PreparedStatement prep = null;
        ResultSet rs = null;
        try {
            prep = connection.prepareStatement("select * from event where event_id = ?");
            prep.setString(1, event_id);
            //prep.addBatch();
            //connection.setAutoCommit(true);
            rs = prep.executeQuery();
            //connection.setAutoCommit(false);
            while (rs.next()) {
                String event = rs.getString(1);
                String descendant = rs.getString(2);
                String person = rs.getString(3);
                Double latitude = rs.getDouble(4);
                Double longitude = rs.getDouble(5);
                String country = rs.getString(6);
                String city = rs.getString(7);
                String event_type = rs.getString(8);
                int year = rs.getInt(9);
                ev = new Event(event_id, descendant, person, latitude, longitude, country, city, event_type, year);
            }
        }
        catch (SQLException e) {
            // ERROR
            e.printStackTrace();
        }
        finally {
            try {
                if (rs != null) rs.close();
                if (prep != null) prep.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return ev;
    }

    /**
     * updates and event in the database corresponding to the given event object
     * @param ev event object containing the event id to be updated and information to update
     * @return  boolean success of updating object
     */
    public boolean update(Event ev) {
        try {
            Statement stat = connection.createStatement();
            PreparedStatement prep = connection.prepareStatement("update event set descendant = ?, person = ?, latitude = ?, longitude = ?, country = ?, city = ?, event_type = ?, year = ? where event_id = ?");
            prep.setString(1, ev.getDescendant());
            prep.setString(2, ev.getPersonID());
            prep.setString(3, String.valueOf(ev.getLatitude()));
            prep.setString(4, String.valueOf(ev.getLongitude()));
            prep.setString(5, ev.getCountry());
            prep.setString(6, ev.getCity());
            prep.setString(7, ev.getEventType());
            prep.setString(8, String.valueOf(ev.getYear()));
            prep.setString(9, ev.getEventID());
            //prep.addBatch();
            //prep.executeBatch();
            prep.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * deletes event from database
     * @param event_id  id of event to delete
     * @return boolean  success of deleting event
     */
    public boolean delete(String event_id) {
        try{
            Statement stat = connection.createStatement();
            PreparedStatement prep = connection.prepareStatement("delete from event where event_id = ?;");
            prep.setString(1, event_id);
            //prep.addBatch();
            //prep.executeBatch();
            //stat.executeUpdate("delete from auth_token")
            prep.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * deletes all events related to a specific person
     * @param username user whose events you are deleting
     * @return boolean success of deleting events
     */
    public boolean deleteAllRelated(String username) {
        try{
            Statement stat = connection.createStatement();
            PreparedStatement prep = connection.prepareStatement("delete from event where descendant = ?;");
            prep.setString(1, username);
            //prep.addBatch();
            //prep.executeBatch();
            //stat.executeUpdate("delete from auth_token")
            prep.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * finds and returns all events pertaining to a given person
     * @param username id of the user to find all relating events for
     * @return  Event Array containing all events related to the given person
     */
    public ArrayList<Event> getAllRelatedEvents(String username) {
        ArrayList<Event> events = null;
        PreparedStatement prep = null;
        ResultSet rs = null;
        try {
            events = new ArrayList<Event>();
            prep = connection.prepareStatement("select * from event where descendant = ?");
            prep.setString(1, username);
            //prep.addBatch();
            //connection.setAutoCommit(true);
            rs = prep.executeQuery();
            //connection.setAutoCommit(false);
            while (rs.next()) {
                String event_id = rs.getString(1);
                String un = rs.getString(2);
                String person_id = rs.getString(3);
                Double latitude = rs.getDouble(4);
                Double longitude = rs.getDouble(5);
                String country = rs.getString(6);
                String city = rs.getString(7);
                String event_type = rs.getString(8);
                int year = rs.getInt(9);
                events.add(new Event(event_id, username, person_id, latitude, longitude, country, city, event_type, year));
            }
        }
        catch (SQLException e) {
            // ERROR
            e.printStackTrace();
            return null;
        }
        finally {
            try {
                if (rs != null) rs.close();
                if (prep != null) prep.close();
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }
        return events;
    }

    /**
     * clears the Event table in the database
     * @return  boolean success of clearing table
     */
    public boolean clearEvents(){
        try {
            PreparedStatement stat = connection.prepareStatement("delete from event");
            //Statement stat = connection.createStatement();
            //stat.executeUpdate("delete from event;");
            stat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * creates the event table in database
     * @param connection connection to database
     * @return boolean success
     */
    protected static boolean initializeEvent(Connection connection) {
        try{
            Statement stat = connection.createStatement();
            //stat.executeUpdate("drop table if exists event;");
            stat.executeUpdate("create table if not exists event(" +
                    " event_id text not null primary key," +
                    " descendant text not null," +
                    " person text not null," +
                    " latitude decimal not null," +
                    " longitude decimal not null," +
                    " country text not null," +
                    " city text not null," +
                    " event_type text not null," +
                    " year integer not null," +
                    " foreign key(person) references person(person_id)," +
                    " foreign key(descendant) references user(username)," +
                    " constraint ck_event_type check (event_type in ('Birth', 'Baptism', 'Christening', 'Marriage', 'Death')));");
            return true;
        }catch(SQLException sqlException) {
            System.err.println("Could NOT initialize user");
            return false;
        }
    }
}
