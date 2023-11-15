package springboot.restserver;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import core.data.Collective;
import core.data.Person;
import persistence.fileHandling.Storage;

/**
 * Base class for all tests. Sets the environment to test.
 */
public class BaseTestClass {

    protected static final Person testPerson = new Person("testPerson",
            Collective.LIMBO_COLLECTIVE_JOIN_CODE);
    protected static final Collective testCollective = new Collective("testCollective",
            Collective.LIMBO_COLLECTIVE_JOIN_CODE);

    /**
     * Sets the current environment to test.
     */
    @BeforeAll
    public static void setTestEnvironment() {
        System.setProperty("env", "test");
    }

    @BeforeEach
    public void setUp() {
        Storage.getInstance().deleteFile();
    }

    @AfterEach
    public void tearDown() {
        System.setProperty("env", "test");
    }

    @AfterAll
    public static void cleanUp() {
        Storage.getInstance().deleteFile();
    }
}
