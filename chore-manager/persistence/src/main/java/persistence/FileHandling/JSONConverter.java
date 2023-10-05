package persistence.FileHandling;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

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

    public void writePersonsToJSON(HashMap<UUID, Person> persons) {
        JSONArray personsJSON = new JSONArray();
        for (Person person : persons.values()) {
            personsJSON.add(person.encodeToJSON());
        }
        this.writeToFile(personsJSON);
    }

    // This assumes that the write file was used
    // TODO: Make it so it checks for formatting errors
    public HashMap<UUID, Person> getPersons() {
        HashMap<UUID, Person> persons = new HashMap<UUID, Person>();
        JSONArray personsJSON = this.readJSONFile();

        if (personsJSON == null) {
            return persons;
        }

        for (Object personObject : personsJSON) {
            JSONObject personJSON = (JSONObject) personObject;

            String name = (String) personJSON.get("name");
            UUID uuid = UUID.fromString((String) personJSON.get("uuid"));
            List<Chore> chores = this.getChoresListFromPerson((JSONArray) personJSON.get("chores"));
            Password password = new Password((String) personJSON.get("password"));

            Person person = new Person(name, uuid, password, chores);
            persons.put(uuid, person);
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
