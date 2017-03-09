package RequestResult;

/**
 * Created by emmag on 2/10/2017.
 * Class containing the load result
 */
public class LoadResult implements Result{
    private String message;

    public LoadResult(String message) {
        this.message = message;
    }

    public LoadResult() {

    }

    public String getMessage() {
        return message;
    }
}

