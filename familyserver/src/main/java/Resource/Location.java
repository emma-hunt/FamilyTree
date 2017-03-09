package Resource;

/**
 * Created by emmag on 3/5/2017.
 */

public class Location {
    private String latitude;
    private String longitude;
    private String city;
    private String country;

    public double getDecLat() {
        return Double.parseDouble(latitude);
    }

    public double getDecLon() {
        return Double.parseDouble(longitude);
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }
}
