package springboot.restserver;

import org.springframework.stereotype.Service;

import core.State;

/**
 * This class provides a service for accessing the application's state.
 */
@Service
public class StateService {

    private State state;

    public StateService() {
        this.state = State.getInstance();
    }

    public StateService(State state) {
        this.state = state;
    }

    public State getInstance() {
        return this.state;
    }

}
