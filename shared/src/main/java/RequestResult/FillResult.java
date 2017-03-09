package RequestResult;

/**
 * Created by emmag on 2/10/2017.
 * Class containing the fill result
 */
public class FillResult implements Result{
    private String message;

    public FillResult(String message) {
        this.message = message;
    }

    public FillResult() {

    }

    public String getMessage() {
        return message;
    }
}
