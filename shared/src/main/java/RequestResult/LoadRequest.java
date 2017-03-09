package RequestResult;

import java.util.ArrayList;

import EncoderDecoder.LoadData;
import Model.Event;
import Model.Person;
import Model.User;

/**
 * Created by emmag on 2/10/2017.
 * class containing the load request
 */
public class LoadRequest {
    private LoadData data;

    public LoadRequest(LoadData data) {
        this.data = data;
    }

    public LoadRequest(){}

    public LoadData getData() {
        return data;
    }

    public void setData(LoadData data) {
        this.data = data;
    }
}
