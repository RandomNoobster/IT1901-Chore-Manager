package core.json;

import org.json.JSONArray;
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
    public static JSONObject decodeFromJSONString(String jsonString)
            throws IllegalArgumentException {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            return jsonObject;
        } catch (JSONException e) {
            throw new IllegalArgumentException("Invalid JSON string, could not decode");
        }
    }

    /**
     * Decodes a JSON string into a JSONArray.
     *
     * @param jsonString the JSON string to decode
     * @return the decoded JSONArray
     * @throws IllegalArgumentException if the JSON string is invalid
     */
    public static JSONArray decodeFromJSONStringArray(String jsonString)
            throws IllegalArgumentException {
        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            return jsonArray;
        } catch (JSONException e) {
            throw new IllegalArgumentException("Invalid JSON string, could not decode");
        }
    }

}
