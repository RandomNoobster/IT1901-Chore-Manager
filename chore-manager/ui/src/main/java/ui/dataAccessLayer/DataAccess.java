package ui.dataAccessLayer;

import core.data.Collective;
import core.data.Password;
import core.data.Person;
import core.data.RestrictedCollective;

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
}
