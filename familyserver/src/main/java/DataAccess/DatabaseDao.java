package DataAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by emmag on 2/10/2017.
 * Class to create the DatabaseDao and Doa's
 */

public class DatabaseDao {
    //responsible for getting database and creating dao's
    protected Connection connection;
    protected UserDao userAccessor;
    protected PersonDao personAccessor;
    public EventDao eventAccessor;
    protected AuthTokenDao authTokenAccessor;

    /**
     * database dao constructor, creates connection and table daos
     */
    public DatabaseDao() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:familyserver.db");
            connection.setAutoCommit(false);
            userAccessor = new UserDao(connection);
            personAccessor = new PersonDao(connection);
            eventAccessor = new EventDao(connection);
            authTokenAccessor = new AuthTokenDao(connection);
        }catch(SQLException e) {
            //unable to open connection
            e.printStackTrace();
        }
    }

    /**
     * gets the database connection
     * @return  database connection object
     */
    public Connection getConnection() {
        //check to see if database exists
        //if not create and initialize it
        //then return connection
        //Connection connection = null;
        return connection;
    }

    /**
     * creates database
     * @return boolean success of creating database
     */
    public static boolean initialize() {
        //create the database and make sure it exists
        try {
            Class.forName("org.sqlite.JDBC");
            DatabaseDao db = new DatabaseDao();
            Connection connection = db.getConnection();
            //Connection connection = DriverManager.getConnection("jdbc:sqlite:familyserver.db");
            boolean successfulTransaction = true;
            successfulTransaction = successfulTransaction && UserDao.initializeUser(connection);
            successfulTransaction = successfulTransaction && PersonDao.initializePerson(connection);
            successfulTransaction = successfulTransaction && EventDao.initializeEvent(connection);
            successfulTransaction = successfulTransaction && AuthTokenDao.initializeAuthToken(connection);
            //end transaction
            db.closeConnection(successfulTransaction);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * closes connection and ends transaction
     * @param successfulTransaction
     */
    public void closeConnection(boolean successfulTransaction) {
        //check if transaction was successful
        try {
            //yes: commit
            if (successfulTransaction) {
                connection.commit();
            }
            //no: rollback
            else {
                connection.rollback();
            }
            //close connection
            connection.close();
            connection = null;
        }catch(SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * clears all tables in the database
     * @return  success of clearing all tables in database
     */
    public boolean clearDatabase() {
        boolean success = authTokenAccessor.clearAuthTokens();
        success = success && eventAccessor.clearEvents();
        success = success && personAccessor.clearPeople();
        success = success && userAccessor.clearUsers();
        return success;
    }
}
