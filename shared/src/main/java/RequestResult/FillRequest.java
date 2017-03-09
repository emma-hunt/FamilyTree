package RequestResult;

/**
 * Created by emmag on 2/10/2017.
 * Class containing the fill request
 */
public class FillRequest {

    String username;
    int generations;

    public FillRequest(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public int getGenerations() {
        return generations;
    }

    public FillRequest(String un, int gen) {
        this.username = un;
        if(gen == 0) {
            generations = 4;
        }
        else {
            generations = gen;
        }
    }
}
