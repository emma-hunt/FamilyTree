package RequestResult;

/**
 * Created by emmag on 2/10/2017.
 * Class containing the clear Result
 */
public class ClearResult implements Result{
    private String message;
    public ClearResult(){};

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
