package persistence.fileHandling;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import core.data.Collective;

public class StorageTest {

    /**
     * We use this.storage to reference the storage instance for testing. If we instead use
     * Storage.getInstance() and delete that instance, if we are not careful we could delete the
     * instance that is used in the main application.
     */
    private Storage storage;
    private static final String fileName = "chore-manager-storage-test.json";
    private static final String fileName2 = "chore-manager-storage-test-new.json";

    // Instead of overwriting @equals, we just compare the unique keys
    private boolean compareTwoHashCollectives(HashMap<String, Collective> collective1,
            HashMap<String, Collective> collective2) {
        if (collective1.size() != collective2.size())
            return false;

        for (Collective collective : collective1.values()) {
            if (!collective2.containsKey(collective.getJoinCode()))
                return false;
        }
        return true;
    }

    /**
     * If for some reason a previous test failed to delete the file, we have an extra check to
     * ensure that this test run independently of previous test runs (by deleting all files we will
     * use in this test).
     */
    @BeforeAll
    public static void resetAllFiles() {
        Storage.getInstance(fileName).deleteFile();
        Storage.getInstance(fileName2).deleteFile();
    }

    @BeforeEach
    public void populateStorage() {
        this.storage = Storage.getInstance(fileName);
    }

    @AfterEach
    public void deleteFile() {
        this.storage.deleteFile();
    }

    @Test
    public void testGetInstance() {
        Storage storage2 = Storage.getInstance(fileName);
        Storage storage3 = Storage.getInstance();
        assertEquals(this.storage, storage2);
        assertEquals(this.storage, storage3);

        // Should not create a new instance
        Storage storage4 = Storage.getInstance("chore-manager-another-file-path.json");
        assertEquals(this.storage, storage4);
    }

    @Test
    public void testSave() {
        HashMap<String, Collective> localCollectives = this.storage.getCollectives();
        Collective newCollective = new Collective("Test");
        localCollectives.put(newCollective.getJoinCode(), newCollective);

        this.storage.addCollective(newCollective);
        this.storage.save();

        Storage.deleteInstance();
        Storage newStorage = Storage.getInstance(fileName);
        HashMap<String, Collective> newCollectives = newStorage.getCollectives();
        assertTrue(this.compareTwoHashCollectives(localCollectives, newCollectives));
    }

    @Test
    public void testDeleteInstance() {
        Storage.deleteInstance();
        Storage storage2 = Storage.getInstance(fileName2);
        assertNotEquals(this.storage, storage2);
        this.storage = storage2; // To delete the file with @AfterEach
    }

    @Test
    public void testDeleteFileContent() {
        Collective newCollective = new Collective("Test");
        this.storage.addCollective(newCollective);
        HashMap<String, Collective> localCollectives = this.storage.getCollectives();

        this.storage.deleteFileContent();
        this.storage.initialize();
        assertNotEquals(localCollectives, this.storage.getCollectives());
    }

    @Test
    public void testGetCollectives() {
        HashMap<String, Collective> localCollectives = this.storage.getCollectives();
        Collective newCollective = new Collective("Test");

        localCollectives.put(newCollective.getJoinCode(), newCollective);
        this.storage.addCollective(newCollective);

        assertTrue(this.compareTwoHashCollectives(localCollectives, this.storage.getCollectives()));
    }

    @Test
    public void testRemoveCollective() {
        HashMap<String, Collective> localCollectives = this.storage.getCollectives();
        Collective collective = localCollectives.values().iterator().next();

        localCollectives.remove(collective.getJoinCode());
        this.storage.removeCollective(collective);
        assertTrue(this.compareTwoHashCollectives(localCollectives, this.storage.getCollectives()));
    }
}
