package core;

import java.util.HashMap;

import core.data.Chore;
import core.data.Collective;
import core.data.Person;

/**
 * <p>
 * The State class represents the current state of the application. This handles information about
 * the logged in user and the current collective. This is a singleton, and should be retrieved using
 * getInstance().
 * </p>
 * <ul>
 * To summarize the difference between State and Storage:
 * <li>State is used to store information about the current collective</li>
 * <li>Storage is used to store information about all collectives and the general state of the
 * application</li>
 * </ul>
 */
public class State {

    private static State instance = null;
    private Person loggedInUser;
    private Collective currentCollective;

    private State() {
    }

    /**
     * This method outputs the singleton instance of the State class.
     *
     * @return The singleton instance of the State class.
     */
    public static synchronized State getInstance() {
        if (instance == null) {
            instance = new State();
        }
        return instance;
    }

    /**
     * This method is used to get the logged in user.
     *
     * @return The logged in user.
     */
    public Person getLoggedInUser() {
        return this.loggedInUser;
    }

    /**
     * This method is used to set the logged in user and the corresponding collective.
     *
     * @param user The user to set as the logged in user.
     */
    public void setLoggedInUser(Person user) {
        this.loggedInUser = user;
        this.currentCollective = user.getCollective();
    }

    /**
     * This method is used to get the current collective.
     *
     * @return The current collective.
     */
    public Collective getCurrentCollective() {
        return this.currentCollective;
    }

    /**
     * This method adds a chore to a person.
     *
     * @param chore          The chore to add.
     * @param assignedPerson The person to add the chore to.
     */
    public void addChore(Chore chore, Person assignedPerson) {
        if (assignedPerson == null) {
            System.out.println("Person is null, cannot add chore");
            return;
        }

        HashMap<String, Person> persons = this.currentCollective.getPersons();
        if (persons.containsKey(assignedPerson.getUsername())) {
            Person person = persons.get(assignedPerson.getUsername());
            person.addChore(chore);
        } else {
            System.out.println("Person does not exist");
        }
    }
}
