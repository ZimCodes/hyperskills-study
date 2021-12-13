package server.response;

public class ErrorResponse extends Response {
    private final String reason;

    public ErrorResponse(String reason) {
        this("ERROR", reason);
    }

    public ErrorResponse(String response, String reason) {
        super(response);
        this.reason = reason;
    }

    @Override
    public String toString(){
        return "{\"response\":" + this.response +
                ",\"reason\":" +
                this.reason +
                "}";
    }
}