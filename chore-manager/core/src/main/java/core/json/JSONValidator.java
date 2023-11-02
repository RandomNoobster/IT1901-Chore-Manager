package core.json;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A utility class for validating and decoding JSON.
 */
public final class JSONValidator {

    /**
     * Decodes a JSON string into a JSONObject.
     *
     * @param jsonString the JSON string to decode
     * @return the decoded JSONObject
     * @throws IllegalArgumentException if the JSON string is invalid
     */
    public static JSONObject decodeFromJSON(String jsonString) throws IllegalArgumentException {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            return jsonObject;
        } catch (JSONException e) {
            throw new IllegalArgumentException("Invalid JSON string, could not decode");
        }
    }

}
