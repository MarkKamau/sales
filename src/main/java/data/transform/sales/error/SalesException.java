package data.transform.sales.error;

public class SalesException extends Throwable {

    public SalesException() {
    }

    public SalesException(String message) {
        super(message);
    }

    public SalesException(Throwable cause) {
        super(cause);
    }

    public SalesException(String message, Throwable cause) {
        super(message, cause);
    }

    public String getMessage()
    {
        return super.getMessage();
    }

}
