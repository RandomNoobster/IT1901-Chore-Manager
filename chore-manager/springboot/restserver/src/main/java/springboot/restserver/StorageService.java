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

    public StorageService(Storage storage) {
        this.storage = storage;
    }

    public Storage getStorage() {
        return this.storage;
    }

    /**
     * Saves the current state of the application to disk. Should be called after each update.
     */
    public void saveToDisk() {
        this.storage.save();
    }
}
