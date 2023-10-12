package persistence.fileHandling;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import core.data.Chore;
import core.data.Password;
import core.data.Person;

/**
 * This class contains methods for converting JSON to objects and vice versa.
 */
@SuppressWarnings("unchecked") // There is no way to parameterize the JSONArray
public class JSONConverter extends FileHandler {

    public JSONConverter(String fileName) {
        super(fileName);
    }

    /**
     * This method is used to write a list of persons to a JSON file.
     *
     * @param persons A HashMap of persons.
     */
    public void writePersonsToJSON(HashMap<String, Person> persons) {
        JSONArray personsJSON = new JSONArray();
        for (Person person : persons.values()) {
            personsJSON.add(person.encodeToJSON());
        }
        this.writeToFile(personsJSON);
    }

    // TODO: Make it so it checks for formatting errors
    /**
     * This method converts the stored JSON data into Java objects. This method assumes that
     * {@link #writePersonsToJSON} has already been run.
     */
    public HashMap<String, Person> getPersons() {
        HashMap<String, Person> persons = new HashMap<String, Person>();
        JSONArray personsJSON = this.readJSONFile();

        if (personsJSON == null) {
            return persons;
        }

        for (Object personObject : personsJSON) {
            JSONObject personJSON = (JSONObject) personObject;

            String username = (String) personJSON.get("username");
            List<Chore> chores = this.getChoresListFromPerson((JSONArray) personJSON.get("chores"));
            Password password = new Password((String) personJSON.get("password"));
            String displayName = (String) personJSON.get("displayName");

            Person person = new Person(username, password, chores, displayName);
            persons.put(username, person);
        }

        return persons;
    }

    /**
     * This method is used to read a list of chores from a {@link JSONArray}.
     *
     * @param choreJSON A list of chores.
     * @return A list of chores.
     */
    public List<Chore> getChoresListFromPerson(JSONArray choreJSON) {
        List<Chore> chores = new ArrayList<>();
        for (Object chore : choreJSON) {
            JSONObject choreObject = (JSONObject) chore;

            String choreName = (String) choreObject.get("choreName");
            LocalDate timeFrom = LocalDate.parse((String) choreObject.get("timeFrom"));
            LocalDate timeTo = LocalDate.parse((String) choreObject.get("timeTo"));
            boolean isWeekly = (boolean) choreObject.get("isWeekly");
            int points = ((Long) choreObject.get("points")).intValue();
            String color = (String) choreObject.get("color");

            Chore newChore = new Chore(choreName, timeFrom, timeTo, isWeekly, points, color);
            chores.add(newChore);
        }

        return chores;
    }

}
