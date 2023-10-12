package persistence.fileHandling;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import core.data.Chore;
import core.data.Person;

public class StorageTest {

    /**
     * We use this.storage to reference the storage instance for testing. If we instead use
     * Storage.getInstance() and delete that instance, if we are not careful we could delete the
     * instance that is used in the main application.
     */
    private Storage storage;
    private static final String fileName = "chore-manager-storage-test.json";
    private static final String fileName2 = "chore-manager-storage-test-new.json";

    private <T> boolean compareTwoLists(Collection<T> list1, Collection<T> list2) {
        return list1.containsAll(list2) && list2.containsAll(list1);
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
        HashMap<String, Person> localPersons = this.storage.getPersons();
        String username = "Peter";
        Person newPerson = new Person("Peter", new ArrayList<>());
        localPersons.put(username, newPerson);

        this.storage.addPerson(newPerson);
        this.storage.save();

        Storage.deleteInstance();
        Storage newStorage = Storage.getInstance(fileName);
        HashMap<String, Person> newPersons = newStorage.getPersons();
        assertEquals(localPersons, newPersons);
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
        this.storage.addPerson(new Person("Peter", new ArrayList<>()));
        HashMap<String, Person> localPersons = this.storage.getPersons();

        this.storage.deleteFileContent();
        this.storage.initialize();
        assertNotEquals(localPersons, this.storage.getPersons());
    }

    @Test
    public void testGetPersons() {
        HashMap<String, Person> localPersons = this.storage.getPersons();
        String username = "Peter";
        Person newPerson = new Person(username, new ArrayList<>());

        localPersons.put(username, newPerson);
        this.storage.addPerson(newPerson);

        assertEquals(localPersons, this.storage.getPersons());
        assertTrue(this.compareTwoLists(localPersons.values(), this.storage.getPersonsList()));
    }

    @Test
    public void testRemovePersons() {
        List<Person> localPersons = this.storage.getPersonsList();
        Person person = localPersons.get(0);

        localPersons.remove(person);
        this.storage.removePerson(person);

        assertTrue(this.compareTwoLists(localPersons, this.storage.getPersonsList()));
    }

    @Test
    public void testAddChore() {
        // Person does exist
        List<Person> localPersons = this.storage.getPersonsList();
        Person person = localPersons.get(0);
        int choreSize = person.getChores().size();

        Chore chore = new Chore("Vaske", null, null, false, 10, "#FFFFFF");
        this.storage.addChore(chore, person);
        assertEquals(choreSize + 1, person.getChores().size());

        // Person does not exist
        int totalChoreSize = this.storage.getPersonsList().stream()
                .mapToInt(p -> p.getChores().size()).sum();
        this.storage.addChore(person.getChores().get(0), null);
        assertEquals(totalChoreSize,
                this.storage.getPersonsList().stream().mapToInt(p -> p.getChores().size()).sum());
    }

}
