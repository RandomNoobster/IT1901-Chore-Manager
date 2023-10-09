package persistence.FileHandling;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import core.Data.Chore;
import core.Data.Password;
import core.Data.Person;

/**
 * This class contains methods for converting JSON to objects and vice versa.
 */
@SuppressWarnings("unchecked") // There is no way to parameterize the JSONArray
public class JSONConverter extends FileHandler {

    public JSONConverter(String fileName) {
        super(fileName);
    }

    public void writePersonsToJSON(HashMap<String, Person> persons) {
        JSONArray personsJSON = new JSONArray();
        for (Person person : persons.values()) {
            personsJSON.add(person.encodeToJSON());
        }
        this.writeToFile(personsJSON);
    }

    // This assumes that the write file was used
    // TODO: Make it so it checks for formatting errors
    public HashMap<String, Person> getPersons() {
        HashMap<String, Person> persons = new HashMap<String, Person>();
        JSONArray personsJSON = this.readJSONFile();

        if (personsJSON == null) {
            return persons;
        }

        for (Object personObject : personsJSON) {
            JSONObject personJSON = (JSONObject) personObject;

            String name = (String) personJSON.get("name");
            List<Chore> chores = this.getChoresListFromPerson((JSONArray) personJSON.get("chores"));
            Password password = new Password((String) personJSON.get("password"));
            String displayName = (String) personJSON.get("displayname");

            Person person = new Person(name, password, chores, displayName);
            persons.put(name, person);
        }

        return persons;
    }

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
