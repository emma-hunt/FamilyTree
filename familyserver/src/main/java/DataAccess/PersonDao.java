package DataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Model.Person;

/**
 * Created by emmag on 2/10/2017.
 * Class to access the Person table of the database
 */

public class PersonDao {
    Connection connection;

    public PersonDao(Connection conn) {
        this.connection = conn;
    }

    /**
     * creates a person in the database
     * @param p             person to add to database
     * @return  boolean     success of creating new person
     */
    public boolean createPerson(Person p){
        try {
            Statement stat = connection.createStatement();
            PreparedStatement prep = connection.prepareStatement("insert into person values (?, ?, ?, ?, ?, ?, ?, ?);");
            prep.setString(1, p.getPerson_id());
            prep.setString(2, p.getDescendant());
            prep.setString(3, p.getFirst_name());
            prep.setString(4, p.getLast_name());
            prep.setString(5, String.valueOf(p.getGender()));
            prep.setString(6, p.getFather());
            prep.setString(7, p.getMother());
            prep.setString(8, p.getSpouse());
            prep.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * reads information on the given person id and stores it in a Person object
     * @param person_id id of person in database to read
     * @return person object containing read information from database
     */
    public Person read(String person_id) {
        Person p = null;
        PreparedStatement prep = null;
        ResultSet rs = null;
        try {
            prep = connection.prepareStatement("select * from person where person_id = ?");
            prep.setString(1, person_id);
            //prep.addBatch();
            //connection.setAutoCommit(true);
            rs = prep.executeQuery();
            //connection.setAutoCommit(false);
            while (rs.next()) {
                String id = rs.getString(1);
                String descendant = rs.getString(2);
                String first_name = rs.getString(3);
                String last_name = rs.getString(4);
                char gender = rs.getString(5).charAt(0);
                String father = rs.getString(6);
                String mother = rs.getString(7);
                String spouse = rs.getString(8);
                p = new Person(person_id, descendant, first_name, last_name, gender, father, mother, spouse);
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
        return p;
    }

    /**
     * updates the given person in the database
     * @param p person in the database to be updated, contains id of person to be updated and updated information
     * @return  boolean success of update
     */
    public boolean update(Person p) {
        try {
            Statement stat = connection.createStatement();
            PreparedStatement prep = connection.prepareStatement("update person set descendant = ?, first_name = ?, last_name = ?, gender = ?, father = ?, mother = ?, spouse = ? where person_id = ?");
            prep.setString(1, p.getDescendant());
            prep.setString(2, p.getFirst_name());
            prep.setString(3, p.getLast_name());
            prep.setString(4, String.valueOf(p.getGender()));
            prep.setString(5, p.getFather());
            prep.setString(6, p.getMother());
            prep.setString(7, p.getSpouse());
            prep.setString(8, p.getPerson_id());
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
     * deletes the given person from the database, and all corresponding events
     * @param person_id id of person to delete from database
     * @return  boolean success of deletion
     */
    public boolean delete(String person_id) {
        boolean success = true;
        try{
            //success = DatabaseDao.eventAccessor.deleteAllRelated(connection, person_id);
            Statement stat = connection.createStatement();
            PreparedStatement prep = connection.prepareStatement("delete from person where person_id = ?;");
            prep.setString(1, person_id);
            //prep.addBatch();
            //prep.executeBatch();
            //stat.executeUpdate("delete from auth_token")
            prep.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return success;
    }

    /**
     * deletes all people related to a specific user
     * @param username user whose people you are deleting
     * @return boolean success of deleting events
     */
    public boolean deleteAllRelated(String username) {
        try{
            Statement stat = connection.createStatement();
            PreparedStatement prep = connection.prepareStatement("delete from person where descendant = ?;");
            prep.setString(1, username);
            //prep.addBatch();
            //prep.executeBatch();
            prep.executeUpdate();
            //stat.executeUpdate("delete from auth_token")
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * returns all people related to a user
     * @param user_id   id of user whose relatives are being returned
     * @return Person Array containing all People related to the user
     */
    public ArrayList<Person> getAllRelatedPeople(String user_id) {
        ArrayList<Person> people = null;
        PreparedStatement prep = null;
        ResultSet rs = null;
        try {
            people = new ArrayList<Person>();
            prep = connection.prepareStatement("select * from person where descendant = ?");
            prep.setString(1, user_id);
            //prep.addBatch();
            //connection.setAutoCommit(true);
            rs = prep.executeQuery();
            //connection.setAutoCommit(false);
            while (rs.next()) {
                String person_id = rs.getString(1);
                String des = rs.getString(2);
                String first_name = rs.getString(3);
                String last_name = rs.getString(4);
                char gender = rs.getString(5).charAt(0);
                String father = rs.getString(6);
                String mother = rs.getString(7);
                String spouse = rs.getString(8);
                people.add(new Person(person_id, user_id, first_name, last_name, gender, father, mother, spouse));
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
        return people;
    }

    /**
     * clears the Person table in the database
     * @return  boolean success of clearing the person table
     */
    public boolean clearPeople(){
        try {
            PreparedStatement stat = connection.prepareStatement("delete from person;");
            //Statement stat = connection.createStatement();
           //stat.executeUpdate("delete from person;");
            stat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * creates the Person table
     * @param connection connection to database
     * @return  boolean success of creating table
     */
    protected static boolean initializePerson(Connection connection) {
        try{
            Statement stat = connection.createStatement();
            stat.executeUpdate("drop table if exists person;");
            stat.executeUpdate("create table if not exists person(" +
                    " person_id text not null primary key," +
                    " descendant text not null," +
                    " first_name text not null," +
                    " last_name text not null," +
                    " gender text not null," +
                    " father text," +
                    " mother text," +
                    " spouse text," +
                    " foreign key(descendant) references user(username)," +
                    " foreign key(father) references person(person_id)," +
                    " foreign key(mother) references person(person_id)," +
                    " foreign key(spouse) references person(person_id)," +
                    " constraint ck_gender check (gender in ('m', 'f')));");
            return true;
        }catch(SQLException sqlException) {
            System.err.println("Could NOT initialize person");
            return false;
        }
    }

}
