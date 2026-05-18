package org.tamu.medbackend.utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import static java.util.Objects.isNull;

public class JsonUtils {

    public static JSONObject toSimpleJsonObject(String object) throws RuntimeException {
        if(isNull(object) || object.isEmpty())
            return new JSONObject();

        try {
            JSONParser jsonParser = new JSONParser();
            return (JSONObject) jsonParser.parse(object);
        } catch (ParseException e) {
            throw new RuntimeException();
        }
    }
}
