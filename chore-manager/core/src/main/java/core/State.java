package core;

import java.util.List;
import java.util.UUID;

import core.data.Chore;
import core.data.Collective;
import core.data.Person;

/**
 * The State class represents the current state of the application. This handles information about
 * the logged in user and the current collective. This is a singleton, and should be retrieved using
 * getInstance().
 * <p>
 * To summarize the difference between State and Storage:
 * </p>
 * State is used to store information about the current collective. <br>
 * Storage is used to store information about all collectives and the general state of the
 * application.
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
     * This method is used to get the current collective.
     *
     * @return The current collective.
     */
    public Collective getCurrentCollective() {
        return this.currentCollective;
    }

    /**
     * This method is used to set the logged in user and the corresponding collective.
     *
     * @param user The user to set as the logged in user.
     */
    public void logIn(Person user, Collective collective) {
        this.loggedInUser = user;
        this.currentCollective = collective;
    }

    /**
     * This method is used to set the logged in user.
     *
     * @param user The user to set as the logged in user.
     */
    public void setCurrentUser(Person user) {
        this.loggedInUser = user;
    }

    /**
     * This method is used to set the current collective.
     *
     * @param collective The collective to set as the current collective.
     */
    public void setCurrentCollective(Collective collective) {
        this.currentCollective = collective;
    }

    /**
     * This method sets user and collective to null.
     */
    public void logOutUser() {
        this.loggedInUser = null;
        this.currentCollective = null;
    }

    /**
     * This methods retrieves the chore with the given uuid from the current collective.
     *
     * @param uuid The uuid of the chore to retrieve.
     * @return The chore with the given uuid.
     */
    public Chore getChoreInCurrentCollective(UUID uuid) {
        List<Chore> chores = this.currentCollective.getChoresList();
        Chore chore = null;
        for (Chore c : chores) {
            if (c.getUUID().equals(uuid)) {
                chore = c;
                break;
            }
        }
        return chore;
    }

    /**
     * This method adds a chore to a person within the current collective.
     *
     * @param chore          The chore to add.
     * @param assignedPerson The person to add the chore to.
     */
    public boolean addChore(Chore chore, Person assignedPerson) {
        if (assignedPerson == null) {
            System.out.println("Person is null, cannot add chore");
            return false;
        }

        if (this.currentCollective == null) {
            System.out.println("Current collective is null, cannot add chore");
            return false;
        }

        if (this.currentCollective.hasPerson(assignedPerson)) {
            Person person = this.currentCollective.getPerson(assignedPerson.getUsername());
            return person.addChore(chore);
        }

        System.out.println("Person does not exist in this collective, could not add chore");
        return false;
    }
}
