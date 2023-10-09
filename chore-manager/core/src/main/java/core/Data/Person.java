package core.Data;

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
    private String name;
    private List<Chore> chores;
    private Password password;
    private String displayName;

    /**
     * A constructor for the Person class that initializes the state of the object.
     *
     * @param name The name of the person
     */
    public Person(String name) {
        this(name, new Password(), new ArrayList<>(), name);
    }

    /**
     * A constructor for the Person class that initializes the state of the object.
     *
     * @param name   The name of the person
     * @param chores The chores of the person
     */
    public Person(String name, List<Chore> chores) {
        this(name, new Password(), chores, name);
    }

    // When making a brand new person
    public Person(String name, Password password, String displayName) {
        this(name, password, new ArrayList<Chore>(), displayName);
    }

    // Load person
    public Person(String name, Password password, List<Chore> chores, String displayName) {
        this.name = name;
        this.displayName = displayName;
        this.chores = new ArrayList<Chore>(chores);
        this.password = password;
    }

    public Password getPassword() {
        return this.password;
    }

    public String displayName() {
        return this.displayName;
    }

    /**
     * Outputs the name of the person.
     *
     * @return The name of the person
     */
    public String getName() {
        return this.name;
    }

    /**
     * Adds a chore to the person.
     *
     * @param chore The chore to add
     */
    public void addChore(Chore chore) {
        this.chores.add(chore);
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
     * Outputs a {@link JSONObject} representing the person. Object variables are turned into
     * key/value pairs.
     *
     * @return A {@link JSONObject} representing the person
     */
    public JSONObject encodeToJSON() {
        HashMap<String, Object> map = new HashMap<String, Object>();

        map.put("name", this.name);

        JSONArray choresJSON = new JSONArray();
        for (Chore chore : this.chores) {
            choresJSON.add(chore.encodeToJSON());
        }

        map.put("chores", choresJSON);
        map.put("password", this.password.getPasswordString());
        map.put("displayname", this.displayName);

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
        return this.name.equals(((Person) arg0).getName());
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
