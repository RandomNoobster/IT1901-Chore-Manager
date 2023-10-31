package core.data;

import java.lang.reflect.Method;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONObjectValidator {
    public static boolean containsAllKeys(JSONObject jsonObject, String... keys) {
        for (String key : keys) {
            if (!jsonObject.containsKey(key)) {
                return false;
            }
        }
        return true;
    }

    public static <T> T decodeFromJSONString(String jsonString, Class<T> clazz) {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject;
        try {
            jsonObject = (JSONObject) parser.parse(jsonString);

            // Get the decodeFromJSON method from the specified class
            Method decodeMethod = clazz.getMethod("decodeFromJSON", JSONObject.class);

            // Invoke the method on the JSON object, method is static so pass null as the object
            return clazz.cast(decodeMethod.invoke(null, jsonObject));
        } catch (ParseException | ReflectiveOperationException e) {
            throw new IllegalArgumentException("Invalid JSON string");
        }
    }

    public static <T> T decodeFromJSONString(String jsonString, Class<T> clazz,
            boolean throwException) {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject;
        try {
            jsonObject = (JSONObject) parser.parse(jsonString);

            // Get the decodeFromJSON method from the specified class
            Method decodeMethod = clazz.getMethod("decodeFromJSON", JSONObject.class);

            // Invoke the method on the JSON object, method is static so pass null as the object
            return clazz.cast(decodeMethod.invoke(null, jsonObject));
        } catch (ParseException | ReflectiveOperationException e) {
            if (throwException) {
                throw new IllegalArgumentException("Invalid JSON string");
            }
            e.printStackTrace();
        }
        return null;
    }
}
