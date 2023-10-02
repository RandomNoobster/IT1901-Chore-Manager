package persistence.FileHandling;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import core.Data.Chore;
import core.Data.Person;

public class StorageTest {

    private Storage storage;
    private final String fileName = "chore-manager-storage-test.json";
    private final String fileName2 = "chore-manager-storage-test-new.json";

    private <T> boolean compareTwoLists(Collection<T> list1, Collection<T> list2) {
        return list1.containsAll(list2) && list2.containsAll(list1);
    }

    @BeforeEach
    public void populateStorage() {
        this.storage = Storage.getInstance(this.fileName);
    }

    @AfterEach
    public void deleteFile() {
        this.storage.deleteFile();
    }

    @Test
    public void testGetInstance() {
        Storage storage2 = Storage.getInstance(this.fileName);
        Storage storage3 = Storage.getInstance();
        assertEquals(this.storage, storage2);
        assertEquals(this.storage, storage3);

        // Should not create a new instance
        Storage storage4 = Storage.getInstance("chore-manager-another-file-path.json");
        assertEquals(this.storage, storage4);
    }

    @Test
    public void testSave() {
        HashMap<UUID, Person> localPersons = this.storage.getPersons();
        UUID randomId = UUID.randomUUID();
        Person newPerson = new Person("test", randomId, new ArrayList<>());
        localPersons.put(randomId, newPerson);

        this.storage.addPerson(newPerson);
        this.storage.save();

        Storage.deleteInstance();
        Storage newStorage = Storage.getInstance(this.fileName);
        HashMap<UUID, Person> newPersons = newStorage.getPersons();
        assertEquals(localPersons, newPersons);
    }

    @Test
    public void testDeleteInstance() {
        Storage.deleteInstance();
        Storage storage2 = Storage.getInstance(this.fileName2);
        assertNotEquals(this.storage, storage2);
    }

    @Test
    public void testDeleteFileContent() {
        this.storage.addPerson(new Person("test", UUID.randomUUID(), new ArrayList<>()));
        HashMap<UUID, Person> localPersons = this.storage.getPersons();

        this.storage.deleteFileContent();
        this.storage.initialize();
        assertNotEquals(localPersons, this.storage.getPersons());
    }

    @Test
    public void testGetPersons() {
        HashMap<UUID, Person> localPersons = this.storage.getPersons();
        UUID randomId = UUID.randomUUID();
        Person newPerson = new Person("test", randomId, new ArrayList<>());

        localPersons.put(randomId, newPerson);
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
