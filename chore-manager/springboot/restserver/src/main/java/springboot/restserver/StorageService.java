package springboot.restserver;

import org.springframework.stereotype.Service;

import persistence.fileHandling.Storage;

/**
 * This class provides a service for accessing the application's storage.
 */
@Service
public class StorageService {

    private Storage storage;

    public StorageService() {
        this.storage = Storage.getInstance();
    }

    public Storage getStorage() {
        return this.storage;
    }

    /**
     * Updates the storage service to use the latest version of the storage. This is useful when
     * changing from development to testing as we want to use different files.
     */
    public void updateStorageService() {
        Storage.deleteInstance();
        this.storage = Storage.getInstance();
    }

    /**
     * Saves the current state of the application to disk. Should be called after each update.
     */
    public void saveToDisk() {
        this.storage.save();
    }
}
