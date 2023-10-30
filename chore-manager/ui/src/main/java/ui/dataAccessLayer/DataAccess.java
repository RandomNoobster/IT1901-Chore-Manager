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

}
