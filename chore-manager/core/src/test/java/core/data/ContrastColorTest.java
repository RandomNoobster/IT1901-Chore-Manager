package core.data;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Test that ContrastColor has the expected behavior.
 */
public class ContrastColorTest {
    private final String WHITE = "#FFFFFF";
    private final String BLACK = "#000000";

    /**
     * Sets the current environment to test
     */
    @BeforeAll
    public static void setTestEnvironment() {
        System.setProperty("env", "test");
    }
    
    /**
     * Test that {@link ContrastColor#blackText} doesn't throw any errors and returns the expected value.
     */
    @Test
    public void testBlackText() {
        assertDoesNotThrow(() -> ContrastColor.blackText(WHITE));
        assertDoesNotThrow(() -> ContrastColor.blackText(BLACK));
        assertFalse(ContrastColor.blackText(WHITE));
        assertTrue(ContrastColor.blackText(BLACK));
    }
}
