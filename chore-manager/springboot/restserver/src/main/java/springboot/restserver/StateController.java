package springboot.restserver;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import core.data.Collective;
import core.data.Person;

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
    private StorageService storageService; // Only used for GETTING information about collectives

    /**
     * This method is used to get the logged in user.
     *
     * @return The logged in user.
     */
    @PostMapping(path = "/log-in")
    public boolean logIn(@RequestBody String loginInfo) {

        JSONObject loginInfoJSON = new JSONObject(loginInfo);
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

    /**
     * This method is used to get the logged in user.
     *
     * @return The logged in user.
     */
    @GetMapping(path = "/logged-in-user")
    public String getLoggedInUser() {
        Person user = this.stateService.getInstance().getLoggedInUser();
        return Person.encodeToJSONObject(user).toString();
    }

}
