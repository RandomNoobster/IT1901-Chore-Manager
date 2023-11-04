package springboot.restserver;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import core.data.Collective;
import core.data.Person;
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

    private String uriDecode(String s) {
        return URLDecoder.decode(s, StandardCharsets.UTF_8);
    }

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

    /**
     * Adds a collective to the storage.
     *
     * @param collectiveJSON the collective to add in JSON format
     * @return true if the collective was added successfully, false otherwise
     */
    @CacheEvict(value = "collectives", key = "'all'")
    @PostMapping(path = "/collectives")
    public boolean addCollective(@RequestBody String collectiveJSON) {
        Collective collective = Collective
                .decodeFromJSON(JSONValidator.decodeFromJSONString(collectiveJSON));
        boolean success = this.storageService.getStorage().addCollective(collective);
        this.saveToDisk();
        return success;
    }

    /**
     * Removes a collective from the storage.
     *
     * @param joinCode the collective to remove in JSON format
     * @return true if the collective was added successfully, false otherwise
     */
    @Caching(evict = { @CacheEvict(value = "collectives", key = "'all'"),
            @CacheEvict(value = "collectives", key = "#joinCode") })
    @DeleteMapping(path = "/collectives/{joinCode}")
    public boolean removeCollective(@PathVariable("joinCode") String joinCode) {
        boolean success = this.storageService.getStorage().removeCollective(joinCode);
        this.saveToDisk();
        return success;
    }

    // TODO: Is this needed?
    /**
     * Retrieves all persons from storage.
     *
     * @return a HashMap containing all persons
     */
    @Cacheable(value = "persons", key = "'all'")
    @GetMapping(path = "/persons")
    public String getAllPersons() {
        HashMap<String, Person> persons = this.storageService.getStorage().getAllPersons();
        JSONObject jsonObject = new JSONObject();
        for (String username : persons.keySet()) {
            jsonObject.put(username, Person.encodeToJSONObject(persons.get(username)));
        }
        return jsonObject.toString();
    }

    @GetMapping(path = "/persons/{username}")
    public String getPerson(@PathVariable("username") String username,
            @RequestParam("password") String password) {
        HashMap<String, Person> persons = this.storageService.getStorage().getAllPersons();

        if (!persons.containsKey(username))
            return null;

        Person person = persons.get(username);
        if (!person.getPassword().getPasswordString().equals(password))
            return null;

        return Person.encodeToJSONObject(person).toString();
    }

}
