package util;

import com.beust.jcommander.IStringConverter;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.FileReader;
import java.io.IOException;

class JSONConverter implements IStringConverter<JsonElement> {
    private final static String clientDir = "[CLIENT_DATA_DIR_PATH]";
    @Override
    public JsonElement convert(String value) {
        JsonElement json = null;
        try(FileReader reader = new FileReader(clientDir + value)) {
            json = JsonParser.parseReader(reader);
        }catch (IOException e){
            e.printStackTrace();
        }
        return json;
    }
}