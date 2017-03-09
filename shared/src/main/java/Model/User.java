package Model;

/**
 * Created by emmag on 2/10/2017.
 * User Class, corresponding to the User table in the database
 *
 * @author emmag
 * @version 1.0 Feb 10, 2017
 */

public class User {
    /** string containing the unique userName */
    private String userName;
    /** string containing the password */
    private String password;
    /** String containing the user's email */
    private String email;
    /** String containing the user's first name */
    private String firstName;
    /** String containing the user's last name */
    private String lastName;
    /** char containing the person's gender, must be m or f */
    private char gender;
    /** string containing the person id for the user's corresponding person */
    private String personID;

    /**
     * constructor for the user class
     * @param un    userName
     * @param pw    password
     * @param em    email
     * @param fn    first name
     * @param ln    last name
     * @param g     gender
     * @param per   person id
     */
    public User(String un, String pw, String em, String fn, String ln, char g, String per) {
        userName = un;
        password = pw;
        email = em;
        firstName = fn;
        lastName = ln;
        gender = g;
        personID = per;
    }

    /**
     * gets userName
     * @return userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * gets password
     * @return password
     */
    public String getPassword() { return password; }

    /**
     * gets email
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * gets first name
     * @return firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * gets last name
     * @return lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * gets gender
     * @return gender
     */
    public char getGender() {
        return gender;
    }

    /**
     * gets person id
     * @return personID
     */
    public String getPersonID() {
        return personID;
    }

    /**
     * checks if a user is valid
     * @return boolean
     */
    public boolean isValidUser() {
        if(userName == null || password == null || email == null || firstName == null || lastName == null || personID == null){
            return false;
        }
        if(gender != 'm' || gender != 'f') {
            return false;
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) {
            return true;
        }
        if(this == null && o != null) {
            return false;
        }
        if(this != null && o == null) {
            return false;
        }
        if(o.getClass() != this.getClass()){
            return false;
        }
        User that = (User) o;
        if(!this.getUserName().equals(that.getUserName())){
            return false;
        }
        if(!this.getPassword().equals(that.getPassword())){
            return false;
        }
        if(!this.getEmail().equals(that.getEmail())){
            return false;
        }
        if(!this.getFirstName().equals(that.getFirstName())){
            return false;
        }
        if(!this.getLastName().equals(that.getLastName())){
            return false;
        }
        if(this.getGender() != that.getGender()){
            return false;
        }
        if(!this.getPersonID().equals(that.getPersonID())){
            return false;
        }
        return true;
    }
}
