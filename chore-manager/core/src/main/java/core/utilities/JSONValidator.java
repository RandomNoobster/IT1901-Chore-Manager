package core.utilities;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * A utility class for validating JSON and JSONObjects.
 */
public final class JSONValidator {
    /**
     * Checks if a JSONObject contains all specified keys.
     *
     * @param jsonObject the JSONObject to check
     * @param keys       the keys to check for
     * @return true if the JSONObject contains all keys, false otherwise
     */
    public static boolean containsAllKeys(JSONObject jsonObject, String... keys) {
        return Arrays.stream(keys).allMatch(jsonObject::containsKey);
    }

    /**
     * Decodes a JSON string into an object of the specified class.
     *
     * @param jsonString the JSON string to decode
     * @param clazz      the class of the object to decode the JSON string into
     * @return the decoded object
     * @throws IllegalArgumentException if the JSON string is invalid or the class does not have a
     *                                  decodeFromJSON method
     */
    public static <T> T decodeFromJSONString(String jsonString, Class<T> clazz) {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject;
        try {
            jsonObject = (JSONObject) parser.parse(jsonString);

            // Get the decodeFromJSON method from the specified class
            Method decodeMethod = clazz.getMethod("decodeFromJSON", JSONObject.class);

            // Invoke the method on the JSON object, method is static so pass null as the object
            return clazz.cast(decodeMethod.invoke(null, jsonObject));
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid JSON string");
        } catch (ReflectiveOperationException e) {
            throw new IllegalArgumentException("Class does not have a decodeFromJSON method");
        }
    }

    /**
     * Decodes a JSON string into an object of the specified class.
     *
     * @param jsonString     the JSON string to decode
     * @param clazz          the class of the object to decode the JSON string into
     * @param throwException whether or not to throw an exception if the JSON string is invalid
     * @return the decoded object, or null if the JSON string is invalid and throwException is false
     * @throws IllegalArgumentException if the JSON string is invalid or the class does not have a
     *                                  decodeFromJSON method and throwException is true
     */
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
        } catch (ParseException e) {
            if (throwException)
                throw new IllegalArgumentException("Invalid JSON string");
            e.printStackTrace();
        } catch (ReflectiveOperationException e) {
            if (throwException)
                throw new IllegalArgumentException("Class does not have a decodeFromJSON method");
            e.printStackTrace();
        }
        return null;
    }
}
