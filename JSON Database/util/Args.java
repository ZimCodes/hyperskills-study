package util;
import com.beust.jcommander.Parameter;
import com.google.gson.JsonElement;

import java.io.Serializable;

public class Args implements Serializable{
    private static final long serialVersionUID = 1L;

    @Parameter(names="-t", description = "The type of request")
    public String type;

    @Parameter(names="-k", description = "Index of the cell")
    public String key;

    @Parameter(names="-v", description = "The value to save in the database")
    public String value;

    @Parameter(names="-in",description="input file", converter = JSONConverter.class)
    public JsonElement input;

    public JsonElement keyJSON;
    public JsonElement valueJSON;

    @Override
    public String toString(){
        StringBuilder str = new StringBuilder("{\"type\":");
        str.append(this.type);
        if (key != null){
            str.append(",\"key\":").append("\"").append(this.key).append("\"");
        }
        if (value != null) {
            str.append(",\"value\":").append(this.value);
        }
        str.append("}");
        return str.toString();
    }
}