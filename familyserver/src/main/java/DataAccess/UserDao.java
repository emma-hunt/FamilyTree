package DataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Model.User;

/**
 * Created by emmag on 2/10/2017.
 * Class to access the User table of the database
 */

public class UserDao {
    Connection connection;

    public UserDao(Connection conn) {
        this.connection = conn;
    }

    /**
     * creates a user in the database
     * @param u             User being added to database
     * @return boolean      success of creating a new user in table
     */
    public boolean createUser(User u){
        //create a new matching person
        try {
            Statement stat = connection.createStatement();
            PreparedStatement prep = connection.prepareStatement("insert into user values (?, ?, ?, ?, ?, ?, ?);");
            prep.setString(1, u.getUsername());
            prep.setString(2, u.getPassword());
            prep.setString(3, u.getEmail());
            prep.setString(4, u.getFirst_name());
            prep.setString(5, u.getLast_name());
            prep.setString(6, String.valueOf(u.getGender()));
            prep.setString(7, u.getPerson_id());
            prep.executeUpdate(); // changed from execute to execute update
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        //todo remember to add matching person in same transaction
        return true;
    }

    /**
     * reads an entry in the user database from a given username, stores data in a User object
     * @param username name of user to read
     * @return User object containing read information
     */
    public User read(String username) {
        User u = null;
        PreparedStatement prep = null;
        ResultSet rs = null;
        try {
            prep = connection.prepareStatement("select * from user where username = ?");//username, password, email, first_name, last_name, gender, person_id
            prep.setString(1, username);
            //prep.addBatch();
            //connection.setAutoCommit(true);
            rs = prep.executeQuery();
            //connection.setAutoCommit(false);
            while (rs.next()) {
                String un = rs.getString(1);
                String password = rs.getString(2);
                String email = rs.getString(3);
                String first_name = rs.getString(4);
                String last_name = rs.getString(5);
                char gender = rs.getString(6).charAt(0);
                String person_id = rs.getString(7);
                u = new User(username, password, email, first_name, last_name, gender, person_id);
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
        return u;
    }

    /**
     * updates the user in the database to match the passed in user object
     * @param u user token to update user to match, find user from username of u
     * @return  boolean of success at updating user
     */
    public boolean update(User u) {
        try {
            Statement stat = connection.createStatement();
            PreparedStatement prep = connection.prepareStatement("update user set password = ?, email = ?, first_name = ?, last_name = ?, gender = ? where username = ?");
            prep.setString(1, u.getPassword());
            prep.setString(2, u.getEmail());
            prep.setString(3, u.getFirst_name());
            prep.setString(4, u.getLast_name());
            prep.setString(5, String.valueOf(u.getGender()));
            prep.setString(6, u.getUsername());
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
     * deletes a user in the database, and all associated people, events and auth codes
     * @param username  username of user to delete
     * @return boolean  success of deleting individual
     */
    public boolean delete(String username) {
        boolean success = true;
        try{
            //success = DatabaseDao.personAccessor.deleteAllRelated(connection, username);
            Statement stat = connection.createStatement();
            PreparedStatement prep = connection.prepareStatement("delete from user where username = ?;");
            prep.setString(1, username);
            //prep.addBatch();
            //prep.executeBatch();
            prep.executeUpdate();
            //stat.executeUpdate("delete from auth_token")
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return success;
    }

    /**
     * clears the User table in the database
     * @return success of clearing User table
     */
    public boolean clearUsers() {
        try {
            PreparedStatement stat = connection.prepareStatement("delete from user;");
            int i = stat.executeUpdate();
            //System.out.println(i);
            //Statement stat = connection.createStatement();
            //stat.executeUpdate("delete from user;");
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * creates the User table
     * @param connection connection to database
     * @return  boolean success of creating table
     */
    protected static boolean initializeUser(Connection connection) {
        try{
            Statement stat = connection.createStatement();
            //stat.executeUpdate("drop table if exists user;");
            stat.executeUpdate("create table if not exists user(username text not null primary key," +
                    " password text not null, email text not null," +
                    " first_name text not null," +
                    " last_name text not null," +
                    " gender text not null," +
                    " person_id text not null," +
                    " constraint ck_gender check (gender in ('m', 'f'))," +
                    " foreign key(person_id) references person(person_id));");
            return true;
        }catch(SQLException sqlException) {
            System.err.println("Could NOT initialize user");
            return false;
        }
    }

}
