package ui.dataAccessLayer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URI;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import core.data.Collective;
import core.data.Password;
import core.data.Person;
import core.data.RestrictedCollective;
import core.data.RestrictedPerson;
import persistence.fileHandling.Storage;

/**
 * JUnit tests for the {@link RemoteDataAccess} class.
 */
class RemoteDataAccessTest {

    private static final URI SERVER_URI = URI.create("http://localhost:8080/");
    private static RemoteDataAccess remoteDataAccess = new RemoteDataAccess(SERVER_URI);

    /**
     * Sets up the current environment for testing.
     */
    @BeforeAll
    public static void setTestEnvironment() {
        System.setProperty("env", "test");
        Storage.deleteInstance();
        Storage.getInstance().deleteFile();
        remoteDataAccess.enterTestMode();
        remoteDataAccess.resetAPI();
    }

    /**
     * Performs setup before each test case.
     */
    @BeforeEach
    private void setup() {
        Storage.getInstance().deleteFile();
        remoteDataAccess.resetAPI();
        remoteDataAccess.enterTestMode();
    }

    /**
     * Tests the {@link RemoteDataAccess#getCollective(String)} method.
     */
    @Test
    void testGetCollective() {
        RestrictedCollective collective = remoteDataAccess.getCollective(Collective.LIMBO_COLLECTIVE_JOIN_CODE);

        assertNotNull(collective);
        assertEquals(Collective.LIMBO_COLLECTIVE_JOIN_CODE, collective.getJoinCode());
    }

    /**
     * Tests the {@link RemoteDataAccess#getLimboCollective()} method.
     */
    @Test
    void testGetLimboCollective() {
        RestrictedCollective limboCollective = remoteDataAccess.getLimboCollective();

        assertNotNull(limboCollective);
        assertEquals(RestrictedCollective.LIMBO_COLLECTIVE_JOIN_CODE, limboCollective.getJoinCode());
    }

    /**
     * Tests the {@link RemoteDataAccess#addCollective(Collective)} method.
     */
    @Test
    void testAddCollective() {
        Collective newCollective = new Collective("New Collective");

        boolean result = remoteDataAccess.addCollective(newCollective);

        assertTrue(result);
    }

    /**
     * Tests the {@link RemoteDataAccess#removeCollective(Collective)} method.
     */
    @Test
    void testRemoveCollective() {
        String joinCode = "TEST456";
        Collective testCollective = new Collective("Test Collective", joinCode);
        remoteDataAccess.addCollective(testCollective);

        boolean result = remoteDataAccess.removeCollective(testCollective);

        assertTrue(result);
    }

    /**
     * Tests the {@link RemoteDataAccess#getPersons()} method.
     */
    @Test
    void testGetPersons() {
        HashMap<String, RestrictedPerson> persons = remoteDataAccess.getPersons();

        assertNotNull(persons);
    }

    /**
     * Tests the {@link RemoteDataAccess#addPerson(Person, String)} method.
     */
    @Test
    void testAddPerson() {
        Collective testCollective = new Collective("Test");
        String joinCode = testCollective.getJoinCode();
        remoteDataAccess.addCollective(testCollective);

        Person newPerson = new Person("Hans", joinCode);

        boolean result = remoteDataAccess.addPerson(newPerson, joinCode);

        assertTrue(result);
    }

    /**
     * Tests the {@link RemoteDataAccess#logIn(Person, Password, RestrictedCollective)} method.
     */
    @Test
    void testLogIn() {
        Collective collective = new Collective("Test Collective", "TEST123");

        String username = "testUser";
        Password password = new Password("password");
        Person person = new Person(username, collective.getJoinCode(), password);

        remoteDataAccess.addCollective(collective);
        remoteDataAccess.addPerson(person, collective.getJoinCode());

        boolean result = remoteDataAccess.logIn(new Person(username, collective.getJoinCode()), password, collective);

        assertTrue(result);
    }
}
