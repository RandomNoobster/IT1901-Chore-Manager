package persistence.fileHandling;

import org.junit.jupiter.api.BeforeAll;

/**
 * Base class for all tests. Sets the environment to test.
 */
public class BaseTestClass {
    
    /**
     * Sets the current environment to test
     */
    @BeforeAll
    public static void setTestEnvironment() {
        System.setProperty("env", "test");
    }
}
