package springboot.restserver;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import core.data.Collective;
import core.data.Password;
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

    @Autowired
    private CacheManager cacheManager;

    /**
     * Saves the current storage state to disk.
     */
    private void saveToDisk() {
        this.storageService.saveToDisk();
    }

    /**
     * Retrieves a collective from the storage.
     *
     * @param joinCode the join code of the collective to retrieve
     * @return the collective in JSON format if it exists, null otherwise
     */
    @Cacheable(value = "collectives", key = "#joinCode")
    @GetMapping(path = "/collectives/{joinCode}")
    public String getCollective(@PathVariable("joinCode") String joinCode) {
        Collective collective = this.storageService.getStorage().getCollective(joinCode);
        if (collective == null)
            return null;
        return RestrictedCollective.encodeToJSONObject(collective).toString();
    }

    /**
     * Adds a collective to the storage.
     *
     * @param collectiveJSON the collective to add in JSON format
     * @return true if the collective was added successfully, false otherwise
     */
    @PostMapping(path = "/collectives")
    public boolean addCollective(@RequestBody String collectiveJSON) {
        RestrictedCollective restrictedCollective = RestrictedCollective
                .decodeFromJSON(JSONValidator.decodeFromJSONString(collectiveJSON));

        Collective collective = new Collective(restrictedCollective);

        boolean success = this.storageService.getStorage().addCollective(collective);

        String joinCode = restrictedCollective.getJoinCode();
        Cache collectivesCache = this.cacheManager.getCache("collectives");
        if (collectivesCache != null) {
            collectivesCache.evict(joinCode);
        }
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
     * @param username       the username of the person to retrieve
     * @param passwordString the password of the person to retrieve
     * @return the person in JSON format if the username and password are correct, null otherwise
     */
    @GetMapping(path = "/persons/{username}")
    public String getPerson(@PathVariable("username") String username,
            @RequestParam("password") String passwordString) {
        Person person = this.storageService.getStorage().getPerson(username);
        Password password = new Password(passwordString, true);

        if (person == null)
            return null;

        if (!person.getPassword().equals(password))
            return null;

        return Person.encodeToJSONObject(person).toString();
    }

    /**
     * Adds a person to the storage.
     *
     * @param requestBody the request body
     * @return true if the person was added successfully, false otherwise
     */
    @CacheEvict(value = "persons", key = "'all'")
    @PostMapping(path = "/persons/{username}")
    public boolean addPerson(@RequestBody String requestBody) {
        JSONObject jsonObject = JSONValidator.decodeFromJSONString(requestBody);
        Person person = Person.decodeFromJSON(jsonObject.getJSONObject("person"));
        String joinCode = jsonObject.getString("joinCode");

        boolean success = this.storageService.getStorage().addPerson(person, joinCode);
        this.saveToDisk();
        return success;
    }

    /**
     * Updates the collective join code for a person in the storage.
     *
     * @param username    the username of the person to update
     * @param requestBody the request body
     * @return true if the person was updated successfully, false otherwise
     */
    @CacheEvict(value = "persons", key = "'all'")
    @PutMapping(path = "/persons/{username}")
    public boolean movePersonToAnotherCollective(@PathVariable("username") String username,
            @RequestBody String requestBody) {
        JSONObject jsonObject = JSONValidator.decodeFromJSONString(requestBody);
        String oldJoinCode = jsonObject.getString("oldJoinCode");
        String newJoinCode = jsonObject.getString("newJoinCode");
        String passwordString = jsonObject.getString("password");
        Password password = new Password(passwordString, true);

        Person user = this.storageService.getStorage().getPerson(username);

        if (user == null || !user.getPassword().equals(password))
            return false;

        boolean success = this.storageService.getStorage().movePersonToAnotherCollective(user,
                oldJoinCode, newJoinCode);
        this.saveToDisk();
        return success;
    }

    /**
     * This method initializes the API to use the .env-development file. This is the standard mode.
     * On startup you can expect the API to be in this mode.
     */
    @PostMapping(path = "/mode/enter-standard-mode")
    public void enterStandardMode() {
        if (this.storageService.getStorage().getEnvironmentSetting().equals("development"))
            return;

        System.setProperty("env", "development");
        this.storageService.updateStorageService();
    }

    /**
     * This method initializes the API to use test data and the .env-test file.
     */
    @PostMapping(path = "/mode/enter-test-mode")
    public void enterTestMode() {
        if (this.storageService.getStorage().getEnvironmentSetting().equals("test"))
            return;

        System.setProperty("env", "test");
        this.storageService.updateStorageService();
    }

    /**
     * Resets API by deleting Storage and reading from file again.
     */
    @PostMapping(path = "/mode/reset-api")
    public void resetAPI() {
        this.storageService.updateStorageService();
    }

}
