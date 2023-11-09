package core.data;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * The RestrictedPerson class represents a person in the chore manager. This stores public
 * information about the person. This is used to prevent the user from seeing the other persons in
 * the collective, and is used for security reasons.
 */
public class RestrictedPerson {

    private String username;
    private String displayName;
    private String collectiveJoinCode;

    public RestrictedPerson(RestrictedPerson person) {
        this(person.getUsername(), person.getCollectiveJoinCode(), person.getDisplayName());
    }

    /**
     * A constructor for the Person class that initializes the state of the object.
     *
     * @param username           The unique name of the person
     * @param collectiveJoinCode The collective that the person is a part of
     */
    public RestrictedPerson(String username, String collectiveJoinCode) {
        this(username, collectiveJoinCode, username);
    }

    /**
     * A constructor for the Person class that initializes the state of the object.
     *
     * @param username           The unique name of the person
     * @param collectiveJoinCode The collective that the person is a part of
     * @param displayName        The display name of the person
     */
    public RestrictedPerson(String username, String collectiveJoinCode, String displayName) {
        if (username == null || username.isEmpty())
            throw new IllegalArgumentException("Username cannot be empty");
        this.username = username;
        this.collectiveJoinCode = collectiveJoinCode;
        this.displayName = displayName;
    }

    /**
     * Outputs the display name of the person.
     *
     * @return The display name of the person
     */
    public String getDisplayName() {
        return this.displayName;
    }

    /**
     * Outputs the name of the person.
     *
     * @return The name of the person
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Outputs the collective that the person is a part of.
     *
     * @return The collective that the person is a part of
     */
    public String getCollectiveJoinCode() {
        return this.collectiveJoinCode;
    }

    /**
     * Sets the collective that the person is a part of.
     */
    public void setCollective(String collectiveJoinCode) {
        this.collectiveJoinCode = collectiveJoinCode;
    }

    public boolean isInEmptyCollective() {
        return this.collectiveJoinCode == null
                || this.collectiveJoinCode.equals(Collective.LIMBO_COLLECTIVE_JOIN_CODE);
    }

    /**
     * Outputs a {@link JSONObject} representing the person. Object variables are turned into
     * key/value pairs.
     *
     * @return A {@link JSONObject} representing the person
     */
    public static JSONObject encodeToJSONObject(RestrictedPerson person) {
        if (person == null)
            throw new IllegalArgumentException("Cannot encode null");

        HashMap<String, Object> map = new HashMap<String, Object>();

        map.put("username", person.username);
        map.put("displayName", person.displayName);
        map.put("collectiveJoinCode", person.collectiveJoinCode);

        return new JSONObject(map);
    }

    /**
     * Decodes a {@link JSONObject} into a {@link Person} object.
     *
     * @param jsonObject The {@link JSONObject} to decode
     * @return A {@link Person} object
     */
    public static RestrictedPerson decodeFromJSON(JSONObject jsonObject)
            throws IllegalArgumentException {
        if (jsonObject == null)
            return null;

        try {
            String username = jsonObject.getString("username");
            String displayName = jsonObject.getString("displayName");
            String collectiveJoinCode = jsonObject.getString("collectiveJoinCode");

            return new RestrictedPerson(username, collectiveJoinCode, displayName);
        } catch (JSONException e) {
            throw new IllegalArgumentException(
                    "Invalid JSONObject, could not be converted to Person object");
        }
    }

    @Override
    public String toString() {
        return this.getUsername();
    }
}
