package ui.dataAccessLayer;

import java.util.HashMap;

import core.data.Collective;

/**
 * Class that centralizes access to the Storage API. Makes it easier to support transparent use of a
 * REST API.
 */
public interface DataAccess {

    /**
     * Retrieves all collectives from storage.
     *
     * @return a HashMap containing all collectives
     */
    public HashMap<String, Collective> getCollectives();

    /**
     * Gets a collective from the storage API.
     *
     * @param joinCode The join code of the collective to get.
     * @return The collective with the given join code.
     */
    public Collective getCollective(String joinCode);

    /**
     * This method is used to get the limbo collective.
     *
     * @return The limbo collective.
     */
    public Collective getLimboCollective();

    /**
     * Adds a new collective to the storage.
     *
     * @param collective The collective to add.
     * @return True if the collective was added successfully, false if a collective with the same
     *         join code already exists.
     */
    public boolean addCollective(Collective collective);
}
