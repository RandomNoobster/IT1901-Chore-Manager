package persistence.fileHandling;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import core.data.Chore;
import core.data.Collective;
import core.data.Person;

/**
 * Tests for the {@link Storage} class.
 */
public class StorageTest {

    private Storage storage;
    private static final int DEFAULT_COLLECTIVES_SIZE = 2;
    private static final int DEFAULT_PERSONS_SIZE = 4;
    private static final int DEFAULT_CHORES_SIZE = 1;

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

    /**
     * Tests the behavior of {@link Storage#initialize()}.
     */
    @Test
    public void testInitialize() {
        // When the file is created for the first time or is empty, it should be initialized with
        // default data
        this.storage.initialize();
        assertTrue(this.storage.getCollectives().size() > 0);

        // Adding a new collective and saving it
        Collective newCollective = new Collective("Test");
        this.storage.addCollective(newCollective);
        this.storage.save();

        // Initializing again should not overwrite the existing data
        this.storage.initialize();
        assertTrue(this.storage.getCollectives().containsKey(newCollective.getJoinCode()));
    }

    /**
     * Tests the behavior of {@link Storage#setInstance(String)}.
     */
    @Test
    public void testSetInstance() {
        // Creating a new instance with a different file path
        Storage newStorage = Storage.setInstance("chore-manager-another-file-path.json");
        assertNotEquals(this.storage, newStorage);

        // Deleting the file associated with the new instance
        newStorage.deleteFile();

        // Creating a new instance with the default file path should be equal to the original
        // instance
        Storage defaultInstance = Storage.getInstance();
        assertEquals(this.storage.getFilePath(), defaultInstance.getFilePath());
    }

    /**
     * Tests the behavior of {@link Storage#getCollectives()}.
     */
    @Test
    public void testGetCollectives() {
        // Initially, there should be only the default collectives
        assertEquals(DEFAULT_COLLECTIVES_SIZE, this.storage.getCollectives().size());

        // Adding a new collective
        Collective newCollective = new Collective("Test");
        this.storage.addCollective(newCollective);

        // The number of collectives should now be 1 more
        assertEquals(DEFAULT_COLLECTIVES_SIZE + 1, this.storage.getCollectives().size());
    }

    /**
     * Tests the behavior of {@link Storage#getCollective(String)}.
     */
    @Test
    public void testGetCollective() {
        // Adding a new collective
        Collective newCollective = new Collective("Test");
        this.storage.addCollective(newCollective);

        // Retrieving the collective using its join code
        Collective retrievedCollective = this.storage.getCollective(newCollective.getJoinCode());

        // The retrieved collective should be the same as the added one
        assertEquals(newCollective, retrievedCollective);
    }

    /**
     * Tests the behavior of {@link Storage#getLimboCollective()}.
     */
    @Test
    public void testGetLimboCollective() {
        // Retrieving the limbo collective
        Collective limboCollective = this.storage.getLimboCollective();

        // The limbo collective should not be null
        assertNotNull(limboCollective);
        assertEquals(Collective.LIMBO_COLLECTIVE_JOIN_CODE, limboCollective.getJoinCode());
    }

    /**
     * Tests the behavior of {@link Storage#addCollective(Collective)}.
     */
    @Test
    public void testAddCollective() {
        // Adding a new collective
        Collective newCollective = new Collective("Test");
        assertTrue(this.storage.addCollective(newCollective));

        // Adding the same collective again should return false
        assertFalse(this.storage.addCollective(newCollective));
    }

    /**
     * Tests the behavior of {@link Storage#removeCollective(String)}.
     */
    @Test
    public void testRemoveCollective() {
        // Adding a new collective
        Collective newCollective = new Collective("Test");
        this.storage.addCollective(newCollective);

        // Removing the added collective
        assertTrue(this.storage.removeCollective(newCollective.getJoinCode()));

        // Removing a nonexistent collective should return false
        assertFalse(this.storage.removeCollective("NonexistentJoinCode"));
    }

    /**
     * Tests the behavior of {@link Storage#getPerson(String)}.
     */
    @Test
    public void testGetPerson() {
        // Adding a new collective and a person to it
        Collective newCollective = new Collective("Test");
        this.storage.addCollective(newCollective);
        Person newPerson = new Person("John", newCollective.getJoinCode());
        newCollective.addPerson(newPerson);

        // Retrieving the person using their username
        Person retrievedPerson = this.storage.getPerson(newPerson.getUsername());

        // The retrieved person should be the same as the added one
        assertEquals(newPerson, retrievedPerson);
    }

    /**
     * Tests the behavior of {@link Storage#getAllPersons()}.
     */
    @Test
    public void testGetAllPersons() {
        // Adding a new collective and multiple persons to it
        Collective newCollective = new Collective("Test");
        this.storage.addCollective(newCollective);
        Person person1 = new Person("John", newCollective.getJoinCode());
        Person person2 = new Person("Jane", newCollective.getJoinCode());
        newCollective.addPerson(person1);
        newCollective.addPerson(person2);

        // Retrieving all persons
        List<Person> allPersons = this.storage.getAllPersonsList();

        // The number of retrieved persons should match the added ones + the default ones
        assertEquals(DEFAULT_PERSONS_SIZE + 2, allPersons.size());
        assertTrue(allPersons.contains(person1));
        assertTrue(allPersons.contains(person2));
    }

    /**
     * Tests the behavior of {@link Storage#getAllChores()}.
     */
    @Test
    public void testGetAllChores() {
        // Adding a new collective, person, and chore to it
        Collective newCollective = new Collective("Test");
        this.storage.addCollective(newCollective);
        Person newPerson = new Person("John", newCollective.getJoinCode());
        Chore chore = new Chore("Test Chore", LocalDate.now(), LocalDate.now(), false, 5, "#000000",
                newPerson.getUsername(), newPerson.getUsername());
        newPerson.addChore(chore);
        newCollective.addPerson(newPerson);

        // Retrieving all chores
        List<Chore> allChores = this.storage.getAllChores();

        // The number of retrieved chores should match the added ones + the default ones
        assertEquals(DEFAULT_CHORES_SIZE + 1, allChores.size());
        assertTrue(allChores.contains(chore));
    }

    /**
     * Tests the behavior of {@link Storage#addPerson(Person, String)}.
     */
    @Test
    public void testAddPerson() {
        // Adding a new collective
        Collective newCollective = new Collective("Test");
        this.storage.addCollective(newCollective);

        // Adding a person to the collective
        Person newPerson = new Person("John", newCollective.getJoinCode());
        assertTrue(this.storage.addPerson(newPerson, newCollective.getJoinCode()));

        // Adding the same person again should return false
        assertFalse(this.storage.addPerson(newPerson, newCollective.getJoinCode()));
    }

    /**
     * Tests the behavior of {@link Storage#movePersonToAnotherCollective(Person, String, String)}.
     */
    @Test
    public void testMovePersonToAnotherCollective() {
        // Adding two collectives
        Collective collective1 = new Collective("Collective 1");
        Collective collective2 = new Collective("Collective 2");
        this.storage.addCollective(collective1);
        this.storage.addCollective(collective2);

        // Adding a person to the first collective
        Person person = new Person("John", collective1.getJoinCode());
        collective1.addPerson(person);

        // Moving the person to the second collective
        assertTrue(this.storage.movePersonToAnotherCollective(person, collective1.getJoinCode(),
                collective2.getJoinCode()));

        // The person's collective should be the second collective
        assertEquals(collective2.getJoinCode(), person.getCollectiveJoinCode());

        // Moving a person from a non-existent collective should return false
        assertFalse(this.storage.movePersonToAnotherCollective(person, "NonexistentJoinCode",
                collective2.getJoinCode()));

        // Moving a person to a non-existent collective should return false
        assertFalse(this.storage.movePersonToAnotherCollective(person, collective1.getJoinCode(),
                "NonexistentJoinCode"));
    }

    /**
     * Tests the behavior of {@link Storage#fillFileWithDefaultData()}.
     */
    @Test
    public void testFillFileWithDefaultData() {
        // Filling the file with default data is done via the getInstance call in the @BeforeEach
        // setup method

        // Retrieving the collectives and persons
        HashMap<String, Collective> collectives = this.storage.getCollectives();
        List<Person> persons = this.storage.getAllPersonsList();

        // There should be two collectives and four persons
        assertEquals(DEFAULT_COLLECTIVES_SIZE, collectives.size());
        assertEquals(DEFAULT_PERSONS_SIZE, persons.size());
    }

    /**
     * Tests the behavior of {@link Storage#deleteFile()}.
     */
    @Test
    public void testDeleteFile() {
        // Deleting the file
        assertTrue(this.storage.deleteFile());
    }

}
