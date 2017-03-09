package Resource;

import java.util.Map;

/**
 * Created by emmag on 3/5/2017.
 */

public class NameData {
    String[] data;

    public String generateName() {
        if(data == null) {
            return null;
        }
        int size = data.length;
        int index = (int) (Math.random() * (size - 1));
        return data[index];
    }

    public void setNames(String[] names) {
        this.data = names;
    }
}
