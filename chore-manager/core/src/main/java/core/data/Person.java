package core.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * The Person class represents a person in the chore manager. This class stores sensitive data. DO
 * NOT USE THIS CLASS IN THE FRONTEND UNLESS IT IS ABOUT THE CURRENT USER. USE
 * {@link RestrictedPerson} INSTEAD.
 */
public class Person extends RestrictedPerson {

    private List<Chore> chores;
    private Password password;

    /**
     * A constructor for the Person class that initializes the state of the object.
     *
     * @param username           The username of the person
     * @param collectiveJoinCode The collective that the person is a part of
     */
    public Person(String username, String collectiveJoinCode) {
        this(username, collectiveJoinCode, new Password(), new ArrayList<>(), username);
    }

    /**
     * A constructor for the Person class that initializes the state of the object.
     *
     * @param username           The username of the person
     * @param chores             The chores of the person
     * @param collectiveJoinCode The collective that the person is a part of
     */
    public Person(String username, String collectiveJoinCode, List<Chore> chores) {

        this(username, collectiveJoinCode, new Password(), chores, username);
    }

    /**
     * A constructor for the Person class that initializes the state of the object.
     *
     * @param username           The unique name of the person
     * @param collectiveJoinCode The collective that the person is a part of
     * @param password           The password of the person
     * @param displayName        The display name of the person
     */
    public Person(String username, String collectiveJoinCode, Password password,
            String displayName) {

        this(username, collectiveJoinCode, password, new ArrayList<Chore>(), displayName);
    }

    /**
     * A constructor for the Person class that initializes the state of the object.
     *
     * @param username           The unique name of the person
     * @param collectiveJoinCode The collective that the person is a part of
     * @param password           The password of the person
     * @param chores             The chores of the person
     * @param displayName        The display name of the person
     */
    public Person(String username, String collectiveJoinCode, Password password, List<Chore> chores,
            String displayName) {
        super(username, collectiveJoinCode, displayName);
        this.chores = new ArrayList<Chore>(chores);
        this.password = password;
    }

    /**
     * Outputs the password of the person.
     *
     * @return The password of the person
     */
    public Password getPassword() {
        return this.password;
    }

    /**
     * Adds a chore to the person.
     *
     * @param chore The chore to add
     * @return True if the chore was added successfully, false otherwise
     */
    public boolean addChore(Chore chore) {
        return this.chores.add(chore);
    }

    /**
     * Removes a chore from the person.
     *
     * @param chore The chore to remove
     * @return True if the chore was removed successfully, false otherwise
     */
    public boolean deleteChore(Chore chore) {
        return this.chores.remove(chore);
    }

    /**
     * Removes a chore from the person.
     *
     * @param uuid The uuid of the chore to remove
     * @return True if the chore was removed successfully, false otherwise
     */
    public boolean deleteChore(UUID uuid) {
        for (Chore chore : this.chores) {
            if (chore.getUUID().equals(uuid)) {
                return this.chores.remove(chore);
            }
        }
        return false;
    }

    /**
     * Outputs a list of chores that the person is assigned to.
     *
     * @return A list of chores that the person is assigned to
     */
    public List<Chore> getChores() {
        return new ArrayList<>(this.chores);
    }

    /**
     * Outputs a {@link JSONObject} representing the person. Object variables are turned into
     * key/value pairs.
     *
     * @return A {@link JSONObject} representing the person
     */
    public static JSONObject encodeToJSONObject(Person person) {
        if (person == null)
            throw new IllegalArgumentException("Cannot encode null");

        HashMap<String, Object> map = new HashMap<String, Object>();

        map.put("username", person.getUsername());

        JSONArray choresJSON = new JSONArray();
        for (Chore chore : person.chores) {
            choresJSON.put(Chore.encodeToJSONObject(chore));
        }

        map.put("chores", choresJSON);
        map.put("password", person.password.getPasswordString());
        map.put("displayName", person.getDisplayName());
        map.put("collectiveJoinCode", person.getCollectiveJoinCode());

        return new JSONObject(map);
    }

    /**
     * Decodes a {@link JSONObject} into a {@link Person} object.
     *
     * @param jsonObject The {@link JSONObject} to decode
     * @return A {@link Person} object
     */
    public static Person decodeFromJSON(JSONObject jsonObject) throws IllegalArgumentException {
        if (jsonObject == null)
            return null;

        try {
            String username = jsonObject.getString("username");
            String password = jsonObject.getString("password");
            String displayName = jsonObject.getString("displayName");
            String collectiveJoinCode = jsonObject.getString("collectiveJoinCode");
            JSONArray choresJSON = jsonObject.getJSONArray("chores");

            List<Chore> chores = new ArrayList<Chore>();
            for (Object choreObject : choresJSON) {
                Chore chore = Chore.decodeFromJSON((JSONObject) choreObject);
                chores.add(chore);
            }

            return new Person(username, collectiveJoinCode, new Password(password), chores,
                    displayName);
        } catch (JSONException e) {
            throw new IllegalArgumentException(
                    "Invalid JSONObject, could not be converted to Person object");
        }
    }
}
