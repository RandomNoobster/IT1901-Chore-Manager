package springboot.restserver;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.json.JSONArray;
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
import org.springframework.web.bind.annotation.RestController;

import core.data.Chore;
import core.data.Collective;
import core.data.Person;
import core.data.RestrictedCollective;
import core.data.RestrictedPerson;
import core.json.JSONValidator;

/**
 * The controller for interacting with the state service. Handles incoming HTTP requests related to
 * state and maps them to the appropriate service method.
 */
@RestController
@RequestMapping(StateController.STATE_SERVICE_PATH)
public class StateController {

    public static final String STATE_SERVICE_PATH = "state";

    @Autowired
    private StateService stateService;

    @Autowired
    private StorageService storageService; // Only used for getting and saving information

    private void saveToDisk() {
        this.storageService.saveToDisk();
    }

    /**
     * This method is used to get the logged in user.
     *
     * @return The logged in user.
     */
    @Caching(evict = { @CacheEvict(value = "logged-in-user"),
            @CacheEvict(value = "current-collective") })
    @PostMapping(path = "/log-in")
    public boolean logIn(@RequestBody String loginInfo) {

        JSONObject loginInfoJSON = JSONValidator.decodeFromJSONString(loginInfo);
        String username = loginInfoJSON.getString("username");
        String joinCode = loginInfoJSON.getString("joinCode");
        String password = loginInfoJSON.getString("password");

        Person user = this.storageService.getStorage().getPerson(username);
        Collective collective = this.storageService.getStorage().getCollective(joinCode);

        if (user == null || collective == null)
            return false;

        if (!user.getPassword().getPasswordString().equals(password))
            return false;

        this.stateService.getInstance().logIn(user, collective);
        return true;
    }

    @Caching(evict = { @CacheEvict(value = "logged-in-user"),
            @CacheEvict(value = "current-collective") })
    @PostMapping(path = "/log-out")
    public boolean logOut() {
        this.stateService.getInstance().logOutUser();
        return true;
    }

    /**
     * This method is used to get the logged in user.
     *
     * @return The logged in user.
     */
    @Cacheable(value = "logged-in-user")
    @GetMapping(path = "/logged-in-user")
    public String getLoggedInUser() {
        Person user = this.stateService.getInstance().getLoggedInUser();
        return Person.encodeToJSONObject(user).toString();
    }

    /**
     * This method is used to get the current collective.
     *
     * @return The current collective.
     */
    @Cacheable(value = "current-collective")
    @GetMapping(path = "/current-collective")
    public String getCurrentCollective() {
        Collective collective = this.stateService.getInstance().getCurrentCollective();
        return RestrictedCollective.encodeToJSONObject(collective).toString();
    }

    /**
     * This method is used to add a chore to a person within the current collective.
     *
     * @return True if the chore was added successfully, false otherwise.
     */
    @PostMapping(path = "/chores/{uuid}")
    public boolean addChore(@RequestBody String choreInfo) {
        JSONObject jsonObject = JSONValidator.decodeFromJSONString(choreInfo);
        Chore chore = Chore.decodeFromJSON(jsonObject.getJSONObject("chore"));
        String assignedPersonUsername = jsonObject.getString("assignedPerson");

        Person person = this.stateService.getInstance().getCurrentCollective()
                .getPerson(assignedPersonUsername);

        boolean added = this.stateService.getInstance().addChore(chore, person);
        this.saveToDisk();
        return added;
    }

    /**
     * This method is used to remove a chore from a person within the current collective.
     *
     * @param uuidString The uuid of the chore to remove.
     * @return True if the chore was removed successfully, false otherwise.
     */
    @DeleteMapping(path = "/chores/{uuid}")
    public boolean removeChore(@PathVariable("uuid") String uuidString) {
        UUID uuid = UUID.fromString(uuidString);

        // Find chore with uuid
        List<Chore> chores = this.stateService.getInstance().getCurrentCollective().getChoresList();
        Chore chore = null;
        for (Chore c : chores) {
            if (c.getUUID().equals(uuid)) {
                chore = c;
                break;
            }
        }

        if (chore == null)
            return false;

        Person person = this.stateService.getInstance().getCurrentCollective()
                .getPerson(chore.getAssignedTo());
        boolean removed = person.deleteChore(chore);

        this.saveToDisk();
        return removed;
    }

    /**
     * Gets all chores within this collective.
     *
     * @return All chores within this collective.
     */
    @GetMapping(path = "/chores")
    public String getChores() {
        if (this.stateService.getInstance().getCurrentCollective() == null) {
            System.out.println("You haven't logged in");
            return null;
        }

        List<Chore> chores = this.stateService.getInstance().getCurrentCollective().getChoresList();

        JSONArray choresJSON = new JSONArray();
        for (Chore chore : chores) {
            choresJSON.put(Chore.encodeToJSONObject(chore));
        }

        return choresJSON.toString();
    }

    /**
     * Gets all persons within this collective.
     *
     * @return All persons registered to this collective.
     */
    // TODO: @Cacheable(value = "persons")
    @GetMapping(path = "/persons")
    public String getPersons() {
        HashMap<String, Person> persons = this.storageService.getStorage().getAllPersons();

        JSONObject personsJSON = new JSONObject();
        for (Person person : persons.values()) {
            personsJSON.put(person.getUsername(), RestrictedPerson.encodeToJSONObject(person));
        }

        return personsJSON.toString();
    }

}
