package core.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * The Person class represents a person in the chore manager.
 * It stores information about the person's name and chores.
 */
@SuppressWarnings("unchecked") // There is no way to parameterize the JSONArray
public class Person {
    private UUID uuid;
    private String name;
    private List<Chore> chores = new ArrayList<>();

    /**
     * A constructor for the Person class that initializes the state of the object.
     *
     * @param name The name of the person
     */
    public Person(String name) {
        this.uuid = UUID.randomUUID();
        this.name = name;
    }

    /**
     * A constructor for the Person class that initializes the state of the object.
     *
     * @param name   The name of the person
     * @param chores The chores of the person
     */
    public Person(String name, List<Chore> chores) {
        this.uuid = UUID.randomUUID();
        this.name = name;
        this.chores = new ArrayList<Chore>(chores);
    }

    /**
     * A constructor for the Person class that initializes the state of the object.
     *
     * @param name   The name of the person
     * @param uuid   The UUID of the person
     * @param chores The chores of the person
     */
    public Person(String name, UUID uuid, List<Chore> chores) {
        this.uuid = uuid;
        this.name = name;
        this.chores = new ArrayList<Chore>(chores);
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
     * Outputs the UUID of the person.
     *
     * @return The UUID of the person
     */
    public UUID getUUID() {
        return this.uuid;
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
     * Removes a chore from the person.
     *
     * @param chore The chore to remove
     */
    public List<Chore> getChores() {
        return this.chores;
    }

    /**
     * Outputs a {@link JSONObject} representing the person. Object variables are
     * turned into key/value pairs.
     *
     * @return A {@link JSONObject} representing the person
     */
    public JSONObject encodeToJSON() {
        HashMap<String, Object> map = new HashMap<String, Object>();

        map.put("uuid", this.uuid.toString());
        map.put("name", this.name);

        JSONArray choresJSON = new JSONArray();
        for (Chore chore : this.chores) {
            choresJSON.add(chore.encodeToJSON());
        }

        map.put("chores", choresJSON);

        return new JSONObject(map);
    }

    /**
     * Outputs a boolean indicating wether or not the objects have the same UUID.
     *
     * @return If the UUIDs are equal
     */
    @Override
    public boolean equals(Object arg0) {
        if (!(arg0 instanceof Person)) {
            return false;
        }
        return this.uuid.equals(((Person) arg0).getUUID());
    }
}
