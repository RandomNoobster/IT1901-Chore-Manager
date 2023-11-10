package core;

import org.junit.jupiter.api.BeforeAll;

public class BaseTestClass {
    /**
     * Sets the current environment to test
     */
    @BeforeAll
    public static void setTestEnvironment() {
        System.setProperty("env", "test");
    }
}
