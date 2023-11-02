package springboot.restserver;

import java.util.HashMap;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import core.data.Collective;

/**
 * The controller for interacting with the storage service. Handles incoming HTTP requests related
 * to storage and maps them to the appropriate service method.
 */
@RestController
@RequestMapping(StorageController.STORAGE_SERVICE_PATH)
public class StorageController {

    public static final String STORAGE_SERVICE_PATH = "storage";

    @Autowired
    private StorageService storageService;

    /**
     * Saves the current storage state to disk.
     */
    private void saveToDisk() {
        this.storageService.saveToDisk();
    }

    /**
     * Retrieves all collectives from storage.
     *
     * @return a HashMap containing all collectives
     */
    @GetMapping(path = "/collectives")
    public String getCollectives() {
        HashMap<String, Collective> collectives = this.storageService.getStorage().getCollectives();
        JSONObject jsonObject = new JSONObject();

        for (String joinCode : collectives.keySet()) {
            jsonObject.put(joinCode, Collective.encodeToJSONObject(collectives.get(joinCode)));
        }

        return jsonObject.toString();
    }
    // @GetMapping(path = "/collectives")
    // public HashMap<String, Collective> getCollectives() {
    // return this.storageService.getStorage().getCollectives();
    // }

    /**
     * Adds a collective to storage.
     *
     * @param collective the collective to add
     * @return true if the collective was added successfully, false otherwise
     */
    @PostMapping(path = "/collectives")
    public boolean addCollective(@RequestBody Collective collective) {
        boolean success = this.storageService.getStorage().addCollective(collective);
        this.saveToDisk();
        return success;
    }

}
