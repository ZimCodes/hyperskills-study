package server.response;

public class Response {
    protected final String response;

    public Response() {
        this.response = "OK";
    }

    public Response(String response) {
        this.response = response;
    }

    @Override
    public String toString(){
        return "{\"response\":" + this.response + "}";
    }
}