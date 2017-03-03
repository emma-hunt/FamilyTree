package Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by emmag on 2/10/2017.
 * AuthToken Class, corresponding to the AuthToken table in the database
 *
 * @author emmag
 * @version 1.0 Feb. 10 2017
 */

public class AuthToken {
    /** String containing the unique authorization code */
    private String auth_code;
    /** String containing the username associated with the code */
    private String user; //references a user object
    /** String containing the timestamp associated with when the authToken was last used*/
    private String time_stamp;
    /** format for date objects */
    public static final String dateFormat = "dd/MM/yyyy/HH:mm:ss";
    public static SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);

    /**
     * constructor for AuthToken
     * @param code  auth_code
     * @param u     username
     * @param time  time_stamp
     */
    public AuthToken(String code, String u, Date time) {
        auth_code = code;
        user = u;
        if(time == null) {
            time_stamp = null;
        }
        else {
            String stime = new java.text.SimpleDateFormat("dd/MM/yy/hh:mm:ss").format(time);
            time_stamp = stime;
        }
    }

    /**
     * gets the AuthToken's authorization code
     * @return auth_code
     */
    public String getAuth_code() {
        return auth_code;
    }

    /**
     * gets the AuthToken's user
     * @return user
     */
    public String getUser() {
        return this.user;
    }

    /**
     * gets the time stamp
     * @return  time_stamp
     */
    public Date getTime_stamp(){
        Date parsedTime = null;
        try {
            parsedTime = AuthToken.sdf.parse(time_stamp);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        return parsedTime;
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
        AuthToken that = (AuthToken) o;
        if(!this.getAuth_code().equals(that.getAuth_code())) {
            return false;
        }
        if(!this.getUser().equals(that.getUser())){
            return false;
        }
        if(!this.getTime_stamp().equals(that.getTime_stamp())) {
            return false;
        }
        return true;
    }
}
