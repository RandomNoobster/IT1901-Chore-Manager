package springboot.restserver;

import java.util.HashMap;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import core.data.Collective;
import core.json.JSONValidator;

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
    @Cacheable(value = "collectives", key = "'all'")
    @GetMapping(path = "/collectives")
    public String getCollectives() {
        HashMap<String, Collective> collectives = this.storageService.getStorage().getCollectives();
        JSONObject jsonObject = new JSONObject();
        System.out.println("CACHE NOT HIT");
        for (String joinCode : collectives.keySet()) {
            jsonObject.put(joinCode, Collective.encodeToJSONObject(collectives.get(joinCode)));
        }

        return jsonObject.toString();
    }

    @Cacheable(value = "collectives", key = "#joinCode")
    @GetMapping(path = "/collectives/{joinCode}")
    public String getCollective(@PathVariable("joinCode") String joinCode) {
        Collective collective = this.storageService.getStorage().getCollective(joinCode);
        return Collective.encodeToJSONObject(collective).toString();
    }
    // @GetMapping(path = "/collectives")
    // public HashMap<String, Collective> getCollectives() {
    // return this.storageService.getStorage().getCollectives();
    // }

    /**
     * Adds a collective to storage.
     *
     * @param collectiveJSON the collective to add in JSON format
     * @return true if the collective was added successfully, false otherwise
     */
    @CacheEvict(value = "collectives", key = "'all'")
    @PostMapping(path = "/collectives")
    public boolean addCollective(@RequestBody String collectiveJSON) {
        System.out.println();
        System.out.println(this.storageService.getStorage().getCollectives().size());
        Collective collective = Collective
                .decodeFromJSON(JSONValidator.decodeFromJSONString(collectiveJSON));
        boolean success = this.storageService.getStorage().addCollective(collective);
        System.out.println(success);
        this.saveToDisk();
        System.out.println(this.storageService.getStorage().getCollectives().size());
        return success;
    }

}
