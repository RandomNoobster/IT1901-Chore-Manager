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

    private Storage storage;

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
     * Sets the current environment to test
     */
    @BeforeAll
    public static void setTestEnvironment() {
        System.setProperty("env", "test");
        Storage.deleteInstance();
        Storage.getInstance().deleteFile();
    }

    @BeforeEach
    public void setStorage() {
        this.storage = Storage.getInstance();
    }

    @AfterEach
    public void deleteFile() {
        this.storage.deleteFile();
    }

    @Test
    public void testGetInstance() {
        Storage storage2 = Storage.getInstance();
        assertEquals(this.storage, storage2);

        // Should create a new instance
        Storage storage3 = Storage.setInstance("chore-manager-another-file-path.json");
        assertNotEquals(this.storage, storage3);
        storage3.deleteFile();
    }

    @Test
    public void testSave() {
        HashMap<String, Collective> localCollectives = this.storage.getCollectives();
        Collective newCollective = new Collective("Test");
        localCollectives.put(newCollective.getJoinCode(), newCollective);

        this.storage.addCollective(newCollective);
        this.storage.save();

        Storage newStorage = Storage.getInstance();
        HashMap<String, Collective> newCollectives = newStorage.getCollectives();
        assertTrue(this.compareTwoHashCollectives(localCollectives, newCollectives));
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
