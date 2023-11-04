package core.data;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * RestricedCollective is contains non-sensitive information about a collective. It hides the
 * information about the persons inside the collective. This is used to prevent the user from seeing
 * the other persons in the collective, and is used for security reasons. This object is exclusively
 * used in the frontend.
 */
public class RestrictedCollective {

    private static final int maxJoinCode = 999999; // Inclusive
    public static final String LIMBO_COLLECTIVE_JOIN_CODE = "0"
            .repeat(String.valueOf(maxJoinCode).length());
    private static final Random random = new Random();

    /**
     * To avoid duplicate join codes without having to know about the persistence module.
     */
    private static HashSet<String> joinCodes = new HashSet<String>(
            Arrays.asList(LIMBO_COLLECTIVE_JOIN_CODE));

    private String joinCode;
    private String name;

    public RestrictedCollective(String name) {
        this(name, generateJoinCode());
    }

    /**
     * Constructs a RestrictedCollective object with a given name and join code.
     *
     * @param name     The name of the collective
     * @param joinCode The join code of the collective
     */
    public RestrictedCollective(String name, String joinCode) {
        this.name = name;
        this.joinCode = joinCode;

        joinCodes.add(joinCode);
    }

    public String getJoinCode() {
        return this.joinCode;
    }

    public String getName() {
        return this.name;
    }

    /**
     * Check if collective is the limbo collective you get added to when creating a new user.
     *
     * @return true if this is the limbo collective
     */
    public boolean isLimboCollective() {
        return this.joinCode.equals(LIMBO_COLLECTIVE_JOIN_CODE);
    }

    /**
     * String is unique and not 0000000, as it is reserved for limbo collective.
     *
     * @param joinCode The join code to check
     * @return True if the join code is unique, false otherwise
     */
    private static boolean isUniqueJoinCode(String joinCode) {
        return !joinCodes.contains(joinCode);
    }

    private static String generateJoinCode() {
        if (joinCodes.size() >= maxJoinCode) {
            throw new IllegalStateException("All join codes have been used");
        }
        String joinCode = "";
        do {
            // 1 - 999999 (inclusive), 000000 is reserved
            int randomJoinCode = random.nextInt(maxJoinCode) + 1; // 1 - 999999
            joinCode = String.format("%06d", randomJoinCode);
        } while (!isUniqueJoinCode(joinCode));
        return joinCode;
    }

    /**
     * Outputs a {@link JSONObject} representing the {@link RestrictedCollective}. Object variables
     * are turned into key/value pairs.
     *
     * @return A {@link JSONObject} representing the {@link RestrictedCollective}.
     */
    public static JSONObject encodeToJSONObject(RestrictedCollective collective) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("name", collective.getName());
        map.put("joinCode", collective.getJoinCode());
        return new JSONObject(map);
    }

    /**
     * Decodes a {@link RestrictedCollective} object from a {@link JSONObject}.
     *
     * @param jsonObject The {@link JSONObject} to decode.
     * @return The decoded {@link RestrictedCollective} object.
     */
    public static RestrictedCollective decodeFromJSON(JSONObject jsonObject)
            throws IllegalArgumentException {
        try {
            String name = jsonObject.getString("name");
            String joinCode = jsonObject.getString("joinCode");

            return new RestrictedCollective(name, joinCode);
        } catch (JSONException e) {
            throw new IllegalArgumentException(
                    "Invalid JSONObject, could not be converted to Collective object");
        }
    }

}
