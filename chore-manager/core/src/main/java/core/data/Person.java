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
    private Collective collective;

    /**
     * A constructor for the Person class that initializes the state of the object.
     *
     * @param username   The username of the person
     * @param collective The collective that the person is a part of
     */
    public Person(String username, Collective collective) {
        this(username, collective, new Password(), new ArrayList<>(), username);
    }

    /**
     * A constructor for the Person class that initializes the state of the object.
     *
     * @param username   The username of the person
     * @param chores     The chores of the person
     * @param collective The collective that the person is a part of
     */
    public Person(String username, Collective collective, List<Chore> chores) {
        this(username, collective, new Password(), chores, username);
    }

    /**
     * A constructor for the Person class that initializes the state of the object.
     *
     * @param username    The unique name of the person
     * @param collective  The collective that the person is a part of
     * @param password    The password of the person
     * @param displayName The display name of the person
     */
    public Person(String username, Collective collective, Password password, String displayName) {
        this(username, collective, password, new ArrayList<Chore>(), displayName);
    }

    /**
     * A constructor for the Person class that initializes the state of the object.
     *
     * @param username    The unique name of the person
     * @param collective  The collective that the person is a part of
     * @param password    The password of the person
     * @param chores      The chores of the person
     * @param displayName The display name of the person
     */
    public Person(String username, Collective collective, Password password, List<Chore> chores,
            String displayName) {
        this.username = username;
        this.displayName = displayName;
        this.chores = new ArrayList<Chore>(chores);
        this.password = password;
        this.collective = collective;
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
        return this.chores;
    }

    /**
     * Outputs the collective that the person is a part of.
     *
     * @return The collective that the person is a part of
     */
    public Collective getCollective() {
        return this.collective;
    }

    /**
     * Sets the collective that the person is a part of.
     */
    public void setCollective(Collective collective) {
        this.collective = collective;
    }

    public boolean isInEmptyCollective() {
        return this.collective == null
                || this.collective.getJoinCode().equals(Collective.EMPTY_COLLECTIVE_JOIN_CODE);
    }

    /**
     * Outputs a {@link JSONObject} representing the person. Object variables are turned into
     * key/value pairs.
     *
     * @return A {@link JSONObject} representing the person
     */
    public JSONObject encodeToJSON() {
        HashMap<String, Object> map = new HashMap<String, Object>();

        map.put("username", this.username);

        JSONArray choresJSON = new JSONArray();
        for (Chore chore : this.chores) {
            choresJSON.add(chore.encodeToJSON());
        }

        map.put("chores", choresJSON);
        map.put("password", this.password.getPasswordString());
        map.put("displayName", this.displayName);

        return new JSONObject(map);
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
    public String toString() {
        return this.getUsername();
    }
}
