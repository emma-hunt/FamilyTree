package DataAccess;

import com.sun.org.apache.bcel.internal.generic.ATHROW;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import Model.AuthToken;
import sun.java2d.pipe.SpanShapeRenderer;

/**
 * Created by emmag on 2/10/2017.
 * Class to access the AuthToken table in the database
 */

public class AuthTokenDao {
    Connection connection;

    public AuthTokenDao(Connection conn) {
        this.connection = conn;
    }

    /**
     * creates a new AuthToken in the database
     * @param token AuthToken object to add
     * @return boolean success
     */
    public boolean insertAuthToken(AuthToken token){
        try {
            Statement stat = connection.createStatement();
            PreparedStatement prep = connection.prepareStatement("insert into auth_token values (?, ?, ?);");
            prep.setString(1, token.getAuth_code());
            prep.setString(2, token.getUser());
            String time = AuthToken.sdf.format(token.getTime_stamp());
            prep.setString(3, time);
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
     * checks to see if an authorization code is valid, changes nothing within database
     * @param authCode  code we are looking to authenticate
     * @return boolean validity
     */
    public AuthToken checkAuthToken(String authCode) {
        AuthToken a = null;
        PreparedStatement prep = null;
        ResultSet rs = null;
        try {
            prep = connection.prepareStatement("select * from auth_token where auth_code = ?");
            prep.setString(1, authCode);
           // prep.addBatch();
            //connection.setAutoCommit(true);
            rs = prep.executeQuery();
            //connection.setAutoCommit(false);
            while (rs.next()) {
                String code = rs.getString(1);
                String auth_user = rs.getString(2);
                String time_stamp = rs.getString(3);
                Date parsedTime = AuthToken.sdf.parse(time_stamp);
                a = new AuthToken(authCode, auth_user, parsedTime);
            }
        }
        catch (SQLException e) {
            // ERROR
            e.printStackTrace();
            return null;
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (prep != null) prep.close();
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }
        return a;
    }

    /**
     * updates the timestamp on an auth_token entry
     * @param a authToken object with update info
     * @return  boolean success
     */
    public boolean update(AuthToken a) {
        try {
            //SimpleDateFormat form = a.sdf;
            String time = new java.text.SimpleDateFormat("dd/MM/yy/hh:mm:ss").format(a.getTime_stamp());
            Statement stat = connection.createStatement();
            PreparedStatement prep = connection.prepareStatement("update auth_token set auth_user = ?, time_stamp = ? where auth_code = ?");
            prep.setString(1, a.getUser());
            prep.setString(2, time);
            prep.setString(3, a.getAuth_code());
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
     * clears authorization tokens that have been inactive since last clear
     * @return boolean success of deleting tokens
     */
    public boolean clearInactiveAuthTokens(){
        boolean success = true;
        PreparedStatement prep = null;
        ResultSet rs = null;
        try {
            Statement stat = connection.createStatement();
            //connection.setAutoCommit(true);
            rs = stat.executeQuery("select * from auth_token;");
           // connection.setAutoCommit(false);
            while (rs.next()) {
                String auth_code = rs.getString(1);
                String user = rs.getString(2);
                String time = rs.getString(3);
                Date parsed_time = AuthToken.sdf.parse(time);
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.HOUR, -1);
                Date cutoff_time = cal.getTime();
                if(parsed_time.before(cutoff_time)){
                    success = success && delete(auth_code);
                }
            }
        }
        catch (SQLException e) {
            // ERROR
            e.printStackTrace();
            return false;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (rs != null) rs.close();
                if (prep != null) prep.close();
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
        return success;
    }


    /**
     * reads given authCode from table and returns authToken object
     * @return  AuthToken object containing information
     */
    /*
    public AuthToken read(String authCode) {
        AuthToken a = null;
        PreparedStatement prep = null;
        ResultSet rs = null;
        try {
            prep = connection.prepareStatement("select * from auth_code where auth_code = ?");
            prep.setString(1, authCode);
            //prep.addBatch();
            //connection.setAutoCommit(true);
            rs = prep.executeQuery();
            //connection.setAutoCommit(false);
            //stmt = connection.prepareStatement(sql);
            //rs = stmt.executeQuery();
            while (rs.next()) {
                String code = rs.getString(1);
                String user = rs.getString(2);
                String time = rs.getString(3);
                Date time_stamp = a.sdf.parse(time);
                a = new AuthToken(authCode, user, time_stamp);
            }
        }
        catch (SQLException e) {
            // ERROR
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (prep != null) prep.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return a;
    }
    */


    /**
     * delete corresponding authToken from database
     * @param authCode  authCode of token to delete
     * @return  boolean success of deleting object
     */
    public boolean delete(String authCode) {
        try{
            Statement stat = connection.createStatement();
            PreparedStatement prep = connection.prepareStatement("delete from auth_token where auth_code = ?;");
            prep.setString(1, authCode);
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
     * clears the AuthToken table in the database
     * @return  boolean success of clearing table
     */
    public boolean clearAuthTokens() {
        try {
            Statement stat = connection.createStatement();
            stat.executeUpdate("delete from auth_token;");
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * creates the AuthToken table
     * @param connection connection to database
     * @return  boolean success of creating table
     */
    protected static boolean initializeAuthToken(Connection connection) {
        try{
            Statement stat = connection.createStatement();
            //stat.executeUpdate("drop table if exists auth_token;");
            stat.executeUpdate("create table if not exists auth_token(" +
                    " auth_code text not null primary key," +
                    " auth_user text not null," +
                    " time_stamp text not null," +
                    " foreign key(auth_user) references user(username));");
            return true;
        }catch(SQLException sqlException) {
            System.err.println("Could NOT initialize auth_token");
            return false;
        }
    }
}
