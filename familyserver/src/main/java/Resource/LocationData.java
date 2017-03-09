package Resource;

/**
 * Created by emmag on 3/5/2017.
 */

public class LocationData {
    Location[] data;

    public Location getLocation() {
        if(data == null) {
            return null;
        }
        int size = data.length;
        int index = (int) (Math.random() * (size - 1));
        return data[index];
    }

    public void setData(Location[] data) {
        this.data = data;
    }
}
