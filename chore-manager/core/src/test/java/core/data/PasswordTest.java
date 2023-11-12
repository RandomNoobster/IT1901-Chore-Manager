package core.data;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the {@link Password} class.
 */
public class PasswordTest {
    private Password password;
    List<Password> illegalPwdList = new ArrayList<>();

    public PasswordTest() {
        this.illegalPwdList.add(new Password("password"));
        this.illegalPwdList.add(new Password("password1"));
        this.illegalPwdList.add(new Password("PASSWORD1"));
        this.illegalPwdList.add(new Password("PwD1"));
    }

    /**
     * Sets the current environment to test
     */
    @BeforeAll
    public static void setTestEnvironment() {
        System.setProperty("env", "test");
    }

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
        // Test illegal passwords
        for (Password password : this.illegalPwdList) {
            assertDoesNotThrow(() -> password.isValid());
            assertFalse(password.isValid());
        }

        // Test legal password
        Password password = new Password("Password1");
        assertDoesNotThrow(() -> password.isValid());
        assertTrue(password.isValid());
    }

    /**
     * Test that {@link Password#getRequirements()} doesn't throw any errors.
     */
    @Test
    public void testGetFixMsg() {
        for (Password password : this.illegalPwdList) {
            assertDoesNotThrow(() -> password.getRequirements());
        }
    }

    /**
     * Test that the same password string generates the same hash.
     */
    @Test
    public void testMD5Hashing() {
        Password password1 = new Password("advanced-password123");
        Password password2 = new Password("advanced-password123");
        Password password3 = new Password("advanced-password1234");

        assertTrue(password1.equals(password2));
        assertFalse(password1.equals(password3));
    }
}
