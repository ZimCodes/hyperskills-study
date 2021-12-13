package server.response;

import com.google.gson.JsonElement;

public class ValResponse extends Response{
    private final JsonElement value;

    public ValResponse(JsonElement value) {
        super();
        this.value = value;
    }
}