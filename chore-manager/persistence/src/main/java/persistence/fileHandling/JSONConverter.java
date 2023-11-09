package persistence.fileHandling;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import core.data.Collective;

/**
 * This class contains methods for converting JSON to objects and vice versa.
 */
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
            collectivesJSON.put(Collective.encodeToJSONObject(collective));
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
            Collective collective = Collective.decodeFromJSON(collectiveJSON);
            collectives.put(collective.getJoinCode(), collective);
        }

        return collectives;
    }
}
