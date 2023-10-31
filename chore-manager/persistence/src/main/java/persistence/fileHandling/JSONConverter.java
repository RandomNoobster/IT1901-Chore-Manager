package persistence.fileHandling;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import core.data.Chore;
import core.data.Collective;
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
     * This method is used to write a list of collectives to a JSON file.
     *
     * @param collectives A HashMap of persons.
     */
    public void writeCollectiveToJSON(HashMap<String, Collective> collectives) {
        JSONArray collectivesJSON = new JSONArray();
        for (Collective collective : collectives.values()) {
            collectivesJSON.add(Collective.encodeToJSONObject(collective));
        }
        this.writeToFile(collectivesJSON);
    }

    /**
     * This method converts the stored JSON data into Java objects. This method assumes that
     * {@link #writeCollectiveToJSON} has already been run.
     */
    public HashMap<String, Collective> getCollectives() {
        HashMap<String, Collective> collectives = new HashMap<String, Collective>();
        JSONArray collectivesJSON = this.readJSONFile();

        if (collectivesJSON == null) {
            return collectives;
        }

        for (Object collectiveObject : collectivesJSON) {
            JSONObject collectiveJSON = (JSONObject) collectiveObject;

            String name = (String) collectiveJSON.get("name");
            String joinCode = (String) collectiveJSON.get("joinCode");
            HashMap<String, Person> persons = this
                    .getPersonsFromCollective((JSONArray) collectiveJSON.get("persons"));

            Collective collective = new Collective(name, joinCode, persons);
            collectives.put(joinCode, collective);
        }

        return collectives;
    }

    /**
     * This method is used to read a hashmap of persons from a {@link JSONArray}.
     *
     * @param personsJSON A JSONArray of JSONObjects of Person .
     * @return A HashMap of persons.
     */
    public HashMap<String, Person> getPersonsFromCollective(JSONArray personsJSON) {
        HashMap<String, Person> persons = new HashMap<String, Person>();

        for (Object personObject : personsJSON) {
            JSONObject personJSON = (JSONObject) personObject;

            String username = (String) personJSON.get("username");
            List<Chore> chores = this.getChoresListFromPerson((JSONArray) personJSON.get("chores"));
            Password password = new Password((String) personJSON.get("password"));
            String displayName = (String) personJSON.get("displayName");
            String collectiveJoinCode = (String) personJSON.get("collectiveJoinCode");

            Person person = new Person(username, collectiveJoinCode, password, chores, displayName);
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
            Boolean checked = (Boolean) choreObject.get("checked");
            int daysIncompleted = ((Long) choreObject.get("daysIncompleted")).intValue();
            String creator = (String) choreObject.get("creator");

            Chore newChore = new Chore(choreName, timeFrom, timeTo, isWeekly, points, color,
                    checked, daysIncompleted, creator);
            chores.add(newChore);
        }

        return chores;
    }

}
