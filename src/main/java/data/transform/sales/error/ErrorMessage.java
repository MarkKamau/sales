package data.transform.sales.error;

public class ErrorMessage {

    int number;
    String message;
    String reason;

    public ErrorMessage(){}

    public ErrorMessage(int number, String message)
    {
        this.number=number;
        this.message=message;
    }

    public ErrorMessage(int number, String message, String reason)
    {
        this.number=number;
        this.message=message;
        this.reason=reason;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number=number;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String toJsonString(){
        return "{\"number\" : \"" + this.number + "\"," +
                "\"error\" : \"" + this.message + "\"," +
                "\"reason\" : \"" + this.reason  + "\"}";
    }
}
