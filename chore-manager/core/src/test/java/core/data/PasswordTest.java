package core.data;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the {@link Password} class.
 */
public class PasswordTest {
    private Password password;

    /**
     * Before each test, create a new Password with some sample values.
     */
    @BeforeEach
    public void setUp() {
        this.password = new Password("password");
    }

    /**
     * Test that the constructor can construct a new object without throwing any errors.
     */
    @Test
    public void testConstructor() {
        assertDoesNotThrow(() -> new Password("password"));
    }

    /**
     * Test that {@link Password#updateFlags} doesn't throw any errors.
     */
    @Test
    public void testUpdateFlags() {
        assertDoesNotThrow(() -> this.password.updateFlags());
    }

    /**
     * Test that {@link Password#isLegal} doesn't throw any errors and returns the correct value.
     */
    @Test
    public void testIsLegal() {
        // Password missing integer and uppercase
        password = new Password("password");
        assertDoesNotThrow(() -> password.isLegal());
        assertFalse(password.isLegal());

        // Password with integer, lowercase, uppercase and length of 9
        password = new Password("Password1");
        assertDoesNotThrow(() -> password.isLegal());
        assertTrue(password.isLegal());
    }

    /**
     * Test that {@link Password#getFixMsg()} doesn't throw any errors.
     */
    @Test
    public void testGetFixMsg() {
        assertDoesNotThrow(() -> password.getFixMsg());
    }
}
