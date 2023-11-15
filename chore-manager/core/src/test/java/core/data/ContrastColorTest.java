package core.data;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import core.BaseTestClass;

/**
 * Test that ContrastColor has the expected behavior.
 */
public class ContrastColorTest extends BaseTestClass {
    private final String WHITE = "#FFFFFF";
    private final String BLACK = "#000000";

    /**
     * Test that {@link ContrastColor#blackText} doesn't throw any errors and returns the expected
     * value.
     */
    @Test
    public void testBlackText() {
        assertDoesNotThrow(() -> ContrastColor.blackText(WHITE));
        assertDoesNotThrow(() -> ContrastColor.blackText(BLACK));
        assertFalse(ContrastColor.blackText(WHITE));
        assertTrue(ContrastColor.blackText(BLACK));
    }
}
