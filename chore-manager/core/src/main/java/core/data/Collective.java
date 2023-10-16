package core.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * The Collective class represents a collective in the chore manager.
 */
@SuppressWarnings("unchecked") // There is no way to parameterize the JSONArray
public class Collective {

    /**
     * To avoid duplicate join codes without having to know about the persistence module.
     */
    private static HashSet<String> joinCodes = new HashSet<String>();
    private String joinCode;
    private String name;
    private HashMap<String, Person> persons = new HashMap<String, Person>();

    private static final int maxJoinCode = 1000000; // Exclusive

    public Collective(String name) {
        this(name, generateJoinCode());
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
        this.name = name;
        this.joinCode = joinCode;
        this.persons = persons;

        joinCodes.add(joinCode);
    }

    private static boolean isUniqueJoinCode(String joinCode) {
        return !joinCode.contains(joinCode);
    }

    private static String generateJoinCode() {
        if (joinCodes.size() >= maxJoinCode) {
            throw new IllegalStateException("All join codes have been used");
        }
        String joinCode = "";
        do {
            int randomJoinCode = new Random().nextInt(maxJoinCode);
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
     * This methods adds a person to the file system.
     *
     * @param person The person to add.
     * @return True if the person was added, false if they are already added.
     */
    public boolean addPerson(Person person) {
        if (this.persons.containsKey(person.getUsername()))
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
    public JSONObject encodeToJSON() {
        HashMap<String, Object> map = new HashMap<String, Object>();

        map.put("name", this.name);

        JSONArray personJSON = new JSONArray();
        for (Person person : this.persons.values()) {
            personJSON.add(person.encodeToJSON());
        }

        map.put("persons", personJSON);
        map.put("joinCode", this.joinCode);

        return new JSONObject(map);
    }

}
