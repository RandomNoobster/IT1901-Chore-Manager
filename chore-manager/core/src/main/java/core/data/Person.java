package core.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * The Person class represents a person in the chore manager. It stores information about the
 * person's name and chores.
 */
@SuppressWarnings("unchecked") // There is no way to parameterize the JSONArray
public class Person {
    private String username;
    private List<Chore> chores;
    private Password password;
    private String displayName;
    private String collectiveJoinCode;

    /**
     * A constructor for the Person class that initializes the state of the object.
     *
     * @param person The person to copy
     */
    public Person(Person person) {
        this(person.getUsername(), person.getCollectiveJoinCode(), person.getPassword(),
                person.getChores(), person.getDisplayName());
    }

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
     * @param username    The unique name of the person
     * @param password    The password of the person
     * @param chores      The chores of the person
     * @param displayName The display name of the person
     */
    public Person(String username, String collectiveJoinCode, Password password, List<Chore> chores,
            String displayName) {
        this.username = username;
        this.displayName = displayName;
        this.chores = new ArrayList<Chore>(chores);
        this.password = password;
        this.collectiveJoinCode = collectiveJoinCode;
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
     * Adds a chore to the person.
     *
     * @param chore The chore to add
     */
    public void addChore(Chore chore) {
        this.chores.add(chore);
    }

    public void deleteChore(Chore chore) {
        this.chores.remove(chore);
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
    public static JSONObject encodeToJSONObject(Person person) {
        HashMap<String, Object> map = new HashMap<String, Object>();

        map.put("username", person.username);

        JSONArray choresJSON = new JSONArray();
        for (Chore chore : person.chores) {
            choresJSON.add(Chore.encodeToJSONObject(chore));
        }

        map.put("chores", choresJSON);
        map.put("password", person.password.getPasswordString());
        map.put("displayName", person.displayName);
        map.put("collectiveJoinCode", person.collectiveJoinCode);

        return new JSONObject(map);
    }

    public static Person decodeFromJSON(JSONObject jsonObject) {
        return null;
    }

    /**
     * Outputs a boolean indicating wether or not the objects have the same username (since this is
     * unique).
     *
     * @return If the usernames are equal
     */
    @Override
    public boolean equals(Object arg0) {
        if (!(arg0 instanceof Person)) {
            return false;
        }
        return this.username.equals(((Person) arg0).getUsername());
    }

    @Override
    public int hashCode() {
        return this.username.hashCode();
    }

    @Override
    public String toString() {
        return this.getUsername();
    }
}
