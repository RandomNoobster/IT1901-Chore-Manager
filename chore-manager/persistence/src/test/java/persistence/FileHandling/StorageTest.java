package persistence.FileHandling;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class StorageTest {

    private Storage storage;
    private final String fileName = "chore-manager-storage-test.json";

    @BeforeEach
    public void populateStorage() {
    }

    @AfterEach
    public void deleteFile() {
        this.storage.deleteFile();
    }

}
