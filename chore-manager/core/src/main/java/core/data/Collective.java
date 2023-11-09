package core.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * The Collective class represents a collective in the chore manager. This class stores sensitive
 * data about the users. DO NOT USE THIS CLASS IN THE FRONTEND. USE {@link RestrictedCollective}
 * INSTEAD.
 */
public class Collective extends RestrictedCollective {

    private HashMap<String, Person> persons = new HashMap<String, Person>();

    public Collective(Collective collective) {
        this(collective.getName(), collective.getJoinCode(), collective.getPersons());
    }

    public Collective(RestrictedCollective collective) {
        this(collective.getName(), collective.getJoinCode());
    }

    public Collective(String name) {
        super(name);
    }

    public Collective(String name, String joinCode) {
        this(name, joinCode, new HashMap<String, Person>());
    }

    /**
     * Constructs a new Collective object with the given name, join code, and persons.
     *
     * @param name     The name of the collective.
     * @param joinCode The join code of the collective.
     * @param persons  The persons in the collective.
     */
    public Collective(String name, String joinCode, HashMap<String, Person> persons) {
        super(name, joinCode);
        this.persons = new HashMap<>(persons);
    }

    /**
     * This method is used to get a person from this collective.
     *
     * @return The person with the given username
     */
    public Person getPerson(String username) {
        return this.persons.get(username);
    }

    /**
     * This method is used to get the persons from this collective.
     *
     * @return A {@link HashMap} of persons and their unique keys.
     */
    public HashMap<String, Person> getPersons() {
        return new HashMap<String, Person>(this.persons);
    }

    /**
     * This method is used to get the persons from this collective.
     *
     * @return A {@link List} of persons.
     */
    public List<Person> getPersonsList() {
        return new ArrayList<Person>(this.persons.values());
    }

    /**
     * Checks if a person is in this collective.
     *
     * @param person The person to check
     * @return True if the person is in this collective, false otherwise
     */
    public boolean hasPerson(Person person) {
        return this.persons.containsKey(person.getUsername());
    }

    /**
     * This methods adds a person to the file system.
     *
     * @param person The person to add.
     * @return True if the person was added, false if they are already added.
     */
    public boolean addPerson(Person person) {
        if (this.hasPerson(person))
            return false;

        this.persons.put(person.getUsername(), person);
        return true;
    }

    /**
     * This method is used to remove a person from the file system.
     *
     * @param person The person to remove
     */
    public boolean removePerson(Person person) {
        return this.persons.remove(person.getUsername()) != null;
    }

    /**
     * This method gets a list of all chores belonging to any user.
     *
     * @return All chores.
     */
    public List<Chore> getChoresList() {
        List<Chore> chores = new ArrayList<Chore>();
        for (Person person : this.persons.values()) {
            chores.addAll(new ArrayList<Chore>(person.getChores()));
        }
        return chores;
    }

    /**
     * Outputs a {@link JSONObject} representing the collective. Object variables are turned into
     * key/value pairs.
     *
     * @return A {@link JSONObject} representing the collective
     */
    public static JSONObject encodeToJSONObject(Collective collective) {
        if (collective == null)
            throw new IllegalArgumentException("Cannot encode null");

        HashMap<String, Object> map = new HashMap<String, Object>();

        map.put("name", collective.getName());
        map.put("joinCode", collective.getJoinCode());

        JSONObject personJSON = new JSONObject();
        for (Person person : collective.persons.values()) {
            personJSON.put(person.getUsername(), Person.encodeToJSONObject(person));
        }

        map.put("persons", personJSON);

        return new JSONObject(map);
    }

    /**
     * Decodes a {@link Collective} object from a {@link JSONObject}.
     *
     * @param jsonObject The {@link JSONObject} to decode.
     * @return The decoded {@link Collective} object.
     */
    public static Collective decodeFromJSON(JSONObject jsonObject) throws IllegalArgumentException {
        if (jsonObject == null)
            return null;

        try {
            String name = jsonObject.getString("name");
            String joinCode = jsonObject.getString("joinCode");
            JSONObject personsJSON = jsonObject.getJSONObject("persons");
            HashMap<String, Person> persons = new HashMap<String, Person>();

            for (Object key : personsJSON.keySet()) {
                String username = (String) key;
                JSONObject personJSONObject = personsJSON.getJSONObject(username);
                Person person = Person.decodeFromJSON(personJSONObject);
                persons.put(username, person);
            }

            return new Collective(name, joinCode, persons);
        } catch (JSONException e) {
            throw new IllegalArgumentException(
                    "Invalid JSONObject, could not be converted to Collective object");
        }
    }

}
