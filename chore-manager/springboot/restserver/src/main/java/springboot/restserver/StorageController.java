package springboot.restserver;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
import core.data.RestrictedCollective;
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

    @Cacheable(value = "collectives", key = "#joinCode")
    @GetMapping(path = "/collectives/{joinCode}")
    public String getCollective(@PathVariable("joinCode") String joinCode) {
        Collective collective = this.storageService.getStorage().getCollective(joinCode);
        return RestrictedCollective.encodeToJSONObject(collective).toString();
    }

    /**
     * Adds a collective to the storage.
     *
     * @param collectiveJSON the collective to add in JSON format
     * @return true if the collective was added successfully, false otherwise
     */
    @CacheEvict(value = "collectives", key = "#restrictedCollective.joinCode")
    @PostMapping(path = "/collectives")
    public boolean addCollective(@RequestBody String collectiveJSON) {
        RestrictedCollective restrictedCollective = RestrictedCollective
                .decodeFromJSON(JSONValidator.decodeFromJSONString(collectiveJSON));

        Collective collective = new Collective(restrictedCollective);
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
    @CacheEvict(value = "collectives", key = "#joinCode")
    @DeleteMapping(path = "/collectives/{joinCode}")
    public boolean removeCollective(@PathVariable("joinCode") String joinCode) {
        boolean success = this.storageService.getStorage().removeCollective(joinCode);
        this.saveToDisk();
        return success;
    }

    /**
     * Retrieves a person from the storage.
     *
     * @param username the username of the person to retrieve
     * @param password the password of the person to retrieve
     * @return the person in JSON format if the username and password are correct, null otherwise
     */
    @GetMapping(path = "/persons/{username}")
    public String getPerson(@PathVariable("username") String username,
            @RequestParam("password") String password) {
        Person person = this.storageService.getStorage().getPerson(username);

        if (person == null)
            return null;

        if (!person.getPassword().getPasswordString().equals(password))
            return null;

        return Person.encodeToJSONObject(person).toString();
    }

    /**
     * Adds a person to the storage.
     *
     * @param requestBody the request body
     * @return true if the person was added successfully, false otherwise
     */
    @PostMapping(path = "/persons/{username}")
    public boolean addPerson(@RequestBody String requestBody) {
        JSONObject jsonObject = JSONValidator.decodeFromJSONString(requestBody);
        Person person = Person.decodeFromJSON(jsonObject.getJSONObject("person"));
        String joinCode = jsonObject.getString("joinCode");

        boolean success = this.storageService.getStorage().addPerson(person, joinCode);
        this.saveToDisk();
        return success;
    }

}
