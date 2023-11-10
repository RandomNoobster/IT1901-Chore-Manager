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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import core.data.Chore;
import core.data.Collective;
import core.data.Password;
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
     * This method is used to check if the user is logged in. You cannot perform all actions if you
     * are not logged in
     *
     * @return True if the user is logged in, false otherwise.
     */
    private boolean loggedIn() {
        return this.stateService.getState().getLoggedInUser() != null
                && this.stateService.getState().getCurrentCollective() != null;
    }

    /**
     * This method is used to get the logged in user.
     *
     * @return The logged in user.
     */
    @Caching(evict = { @CacheEvict(value = "logged-in-user", allEntries = true),
            @CacheEvict(value = "current-collective", allEntries = true),
            @CacheEvict(value = "chores", key = "'all'") })
    @PostMapping(path = "/log-in")
    public boolean logIn(@RequestBody String loginInfo) {

        JSONObject loginInfoJSON = JSONValidator.decodeFromJSONString(loginInfo);
        String username = loginInfoJSON.getString("username");
        String joinCode = loginInfoJSON.getString("joinCode");
        String passwordString = loginInfoJSON.getString("password");
        Password password = new Password(passwordString, true);

        Person user = this.storageService.getStorage().getPerson(username);
        Collective collective = this.storageService.getStorage().getCollective(joinCode);

        if (user == null || collective == null)
            return false;

        if (!user.getPassword().equals(password))
            return false;

        this.stateService.getState().logIn(user, collective);
        return true;
    }

    @Caching(evict = { @CacheEvict(value = "logged-in-user", allEntries = true),
            @CacheEvict(value = "current-collective", allEntries = true),
            @CacheEvict(value = "chores", key = "'all'") })
    @PostMapping(path = "/log-out")
    public boolean logOut() {
        this.stateService.getState().logOutUser();
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
        Person user = this.stateService.getState().getLoggedInUser();
        if (user == null)
            return null;
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
        Collective collective = this.stateService.getState().getCurrentCollective();
        if (collective == null)
            return null;
        return RestrictedCollective.encodeToJSONObject(collective).toString();
    }

    /**
     * This method is used to add a chore to a person within the current collective.
     *
     * @return True if the chore was added successfully, false otherwise.
     */
    @CacheEvict(value = "chores", key = "'all'")
    @PostMapping(path = "/chores/{uuid}")
    public boolean addChore(@RequestBody String choreInfo) {
        if (!this.loggedIn())
            return false;

        JSONObject jsonObject = JSONValidator.decodeFromJSONString(choreInfo);
        Chore chore = Chore.decodeFromJSON(jsonObject.getJSONObject("chore"));
        String assignedPersonUsername = jsonObject.getString("assignedPerson");

        Person person = this.stateService.getState().getCurrentCollective()
                .getPerson(assignedPersonUsername);

        boolean added = this.stateService.getState().addChore(chore, person);
        this.saveToDisk();
        return added;
    }

    /**
     * This method is used to remove a chore from a person within the current collective.
     *
     * @param uuidString The uuid of the chore to remove.
     * @return True if the chore was removed successfully, false otherwise.
     */
    @CacheEvict(value = "chores", key = "'all'")
    @DeleteMapping(path = "/chores/{uuid}")
    public boolean removeChore(@PathVariable("uuid") String uuidString) {
        if (!this.loggedIn())
            return false;

        UUID uuid = UUID.fromString(uuidString);
        Chore chore = this.stateService.getState().getChoreInCurrentCollective(uuid);

        if (chore == null)
            return false;

        Person person = this.stateService.getState().getCurrentCollective()
                .getPerson(chore.getAssignedTo());
        boolean removed = person.deleteChore(chore);

        this.saveToDisk();
        return removed;
    }

    /**
     * This method is used to update a chore in the current collective.
     *
     * @param uuidString    The uuid of the chore to update.
     * @param checkedString The new checked value of the chore.
     * @return True if the chore was updated successfully, false otherwise.
     */
    @CacheEvict(value = "chores", key = "'all'")
    @PutMapping(path = "/chores/{uuid}")
    public boolean updateChoreChecked(@PathVariable("uuid") String uuidString,
            @RequestParam("checked") String checkedString) {
        if (!this.loggedIn())
            return false;

        UUID uuid = UUID.fromString(uuidString);
        Chore chore = this.stateService.getState().getChoreInCurrentCollective(uuid);

        if (chore == null)
            return false;

        boolean checked = Boolean.parseBoolean(checkedString);
        chore.setChecked(checked);
        this.saveToDisk();
        return true;
    }

    /**
     * Gets all chores within this collective.
     *
     * @return All chores within this collective.
     */
    @Cacheable(value = "chores", key = "'all'")
    @GetMapping(path = "/chores")
    public String getChores() {
        if (!this.loggedIn())
            return null;

        List<Chore> chores = this.stateService.getState().getCurrentCollective().getChoresList();

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
    @Cacheable(value = "persons", key = "'all'")
    @GetMapping(path = "/persons")
    public String getPersons() {
        if (!this.loggedIn())
            return null;

        HashMap<String, Person> persons = this.stateService.getState().getCurrentCollective()
                .getPersons();

        JSONObject personsJSON = new JSONObject();
        for (Person person : persons.values()) {
            personsJSON.put(person.getUsername(), RestrictedPerson.encodeToJSONObject(person));
        }

        return personsJSON.toString();
    }
}
