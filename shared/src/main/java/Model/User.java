package Model;

/**
 * Created by emmag on 2/10/2017.
 * User Class, corresponding to the User table in the database
 *
 * @author emmag
 * @version 1.0 Feb 10, 2017
 */

public class User {
    /** string containing the unique username */
    private String username;
    /** string containing the password */
    private String password;
    /** String containing the user's email */
    private String email;
    /** String containing the user's first name */
    private String first_name;
    /** String containing the user's last name */
    private String last_name;
    /** char containing the person's gender, must be m or f */
    private char gender;
    /** string containing the person id for the user's corresponding person */
    private String person_id;

    /**
     * constructor for the user class
     * @param un    username
     * @param pw    password
     * @param em    email
     * @param fn    first name
     * @param ln    last name
     * @param g     gender
     * @param per   person id
     */
    public User(String un, String pw, String em, String fn, String ln, char g, String per) {
        username = un;
        password = pw;
        email = em;
        first_name = fn;
        last_name = ln;
        gender = g;
        person_id = per;
    }

    /**
     * gets username
     * @return username
     */
    public String getUsername() {
        return username;
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
     * @return first_name
     */
    public String getFirst_name() {
        return first_name;
    }

    /**
     * gets last name
     * @return last_name
     */
    public String getLast_name() {
        return last_name;
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
     * @return person_id
     */
    public String getPerson_id() {
        return person_id;
    }

    /**
     * checks if a user is valid
     * @return boolean
     */
    public boolean isValidUser() {
        if(username == null || password == null || email == null || first_name == null || last_name == null || person_id == null){
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
        if(!this.getUsername().equals(that.getUsername())){
            return false;
        }
        if(!this.getPassword().equals(that.getPassword())){
            return false;
        }
        if(!this.getEmail().equals(that.getEmail())){
            return false;
        }
        if(!this.getFirst_name().equals(that.getFirst_name())){
            return false;
        }
        if(!this.getLast_name().equals(that.getLast_name())){
            return false;
        }
        if(this.getGender() != that.getGender()){
            return false;
        }
        if(!this.getPerson_id().equals(that.getPerson_id())){
            return false;
        }
        return true;
    }
}
