package ui.dataAccessLayer;

import java.util.HashMap;
import java.util.List;

import core.data.Chore;
import core.data.Collective;
import core.data.Password;
import core.data.Person;
import core.data.RestrictedCollective;
import core.data.RestrictedPerson;

/**
 * Class that centralizes access to the Storage API. Makes it easier to support transparent use of a
 * REST API.
 */
public interface DataAccess {

    /**
     * Gets a collective from the storage API.
     *
     * @param joinCode The join code of the collective to get.
     * @return The collective with the given join code.
     */
    public RestrictedCollective getCollective(String joinCode);

    /**
     * This method is used to get the limbo collective.
     *
     * @return The limbo collective.
     */
    public RestrictedCollective getLimboCollective();

    /**
     * Adds a new collective to the storage API.
     *
     * @param collective The collective to add.
     * @return True if the collective was added successfully, false if a collective with the same
     *         join code already exists.
     */
    public boolean addCollective(Collective collective);

    /**
     * Removes a collective from the storage API.
     *
     * @param collective The collective to remove.
     * @return True if the collective was removed successfully.
     */
    public boolean removeCollective(Collective collective);

    /**
     * Gets a person from the storage API.
     *
     * @param username The username of the person to get.
     * @param password The password of the person to get.
     * @return The person with the given username and password. Null if username or password is
     *         wrong.
     */
    public Person getPerson(String username, Password password);

    /**
     * This method adds a unique person to a collective in the API.
     *
     * @param person   The person to add
     * @param joinCode The join code of the collective to add the person to
     * @return True if the person was added, false otherwise
     */
    public boolean addPerson(Person person, String joinCode);

    /**
     * This method is used to set the logged in user and the corresponding collective. If the
     * collective or person does not exist in storage, it will fail to log in.
     *
     * @param user         The user to set as the logged in user.
     * @param userPassword The password of the user to set as the logged in user.
     * @param collective   The collective to set as the current collective.
     * @return true if the user was logged in successfully, false if the collective or user does not
     *         exist in storage
     */
    public boolean logIn(Person user, Password userPassword, Collective collective);

    /**
     * This method is used to log out the current user.
     * 
     * @return true if the user was logged out successfully, false otherwise
     */
    public boolean logOut();

    /**
     * This method is used to set the logged in user.
     *
     * @return The logged in user
     */
    public Person getLoggedInUser();

    /**
     * This method is used to get the current collective.
     *
     * @return The current collective.
     */
    public RestrictedCollective getCurrentCollective();

    /**
     * This method is used to add a chore to a person in the current collective.
     *
     * @return true if the chore was added successfully, false otherwise
     */
    public boolean addChore(Chore chore, Person person);

    /**
     * This method is used to get the chores from the current collective.
     * 
     * @return A {@link List} of chores.
     */
    public List<Chore> getChores();

    /**
     * This method is used to get the persons from the current collective.
     *
     * @return A {@link HashMap} of persons, where key is the username.
     */
    public HashMap<String, RestrictedPerson> getPersons();
}
