package RequestResult;

/**
 * Created by emmag on 3/3/2017.
 */

public class ErrorResult implements Result{
    private String message;

    public ErrorResult(String message) {
        this.message = message;
    }

    public ErrorResult() {

    }
}
