package core.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;

import core.utilities.JSONValidator;

/**
 * The Collective class represents a collective in the chore manager.
 */
public class Collective {

    private static final int maxJoinCode = 999999; // Inclusive
    public static final String LIMBO_COLLECTIVE_JOIN_CODE = "0"
            .repeat(String.valueOf(maxJoinCode).length());
    public static final Random random = new Random();
    /**
     * To avoid duplicate join codes without having to know about the persistence module.
     */
    private static HashSet<String> joinCodes = new HashSet<String>(
            Arrays.asList(LIMBO_COLLECTIVE_JOIN_CODE));

    private String joinCode;
    private String name;
    private HashMap<String, Person> persons = new HashMap<String, Person>();

    public Collective(Collective collective) {
        this(collective.getName(), collective.getJoinCode(), collective.getPersons());
    }

    public Collective(String name) {
        this(name, generateJoinCode());
    }

    public Collective(String name, String joinCode) {
        this(name, joinCode, new HashMap<String, Person>());
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
     * Constructs a new Collective object with the given name, join code, and persons.
     *
     * @param name     The name of the collective.
     * @param joinCode The join code of the collective.
     * @param persons  The persons in the collective.
     */
    public Collective(String name, String joinCode, HashMap<String, Person> persons) {
        this.name = name;
        this.joinCode = joinCode;
        this.persons = new HashMap<>(persons);

        joinCodes.add(joinCode);
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

    public String getJoinCode() {
        return this.joinCode;
    }

    public String getName() {
        return this.name;
    }

    /**
     * This method is used to get the persons from the file system.
     *
     * @return A {@link HashMap} of persons and their unique keys.
     */
    public HashMap<String, Person> getPersons() {
        return new HashMap<String, Person>(this.persons);
    }

    /**
     * This method is used to get the persons from the file system.
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
    public void removePerson(Person person) {
        this.persons.remove(person.getUsername());
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
        HashMap<String, Object> map = new HashMap<String, Object>();

        map.put("name", collective.name);
        map.put("joinCode", collective.joinCode);

        JSONObject personJSON = new JSONObject();
        for (Person person : collective.persons.values()) {
            personJSON.put(person.getUsername(), Person.encodeToJSONObject(person));
        }

        map.put("persons", personJSON);

        return new JSONObject(map);
    }

    public static Collective decodeFromJSON(String jsonString) {
        JSONObject jsonObject = JSONValidator.decodeStringToJSONObject(jsonString);
        return decodeFromJSON(jsonObject);
    }

    /**
     * Decodes a {@link Collective} object from a {@link JSONObject}.
     *
     * @param jsonObject The {@link JSONObject} to decode.
     * @return The decoded {@link Collective} object.
     */
    public static Collective decodeFromJSON(JSONObject jsonObject) {
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
            throw new IllegalArgumentException("Invalid JSON string, could not decode");
        }
    }

}
