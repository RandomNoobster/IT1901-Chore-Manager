package springboot.restserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
