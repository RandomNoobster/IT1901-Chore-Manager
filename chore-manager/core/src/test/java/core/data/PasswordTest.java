package core.data;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import core.BaseTestClass;

/**
 * Tests for the {@link Password} class.
 */
public class PasswordTest extends BaseTestClass {

    private String validPasswordString = "Password123";
    private String invalidPasswordString = "password";

    List<String> illegalPasswords = new ArrayList<>();

    public PasswordTest() {
        this.illegalPasswords.add(null); // Null
        this.illegalPasswords.add("1As"); // Too short
        this.illegalPasswords.add("password1"); // No uppercase
        this.illegalPasswords.add("PASSWORD1"); // No lowercase
        this.illegalPasswords.add("Password"); // No digit
    }

    /**
     * Test that the constructor can construct a new object without throwing any errors.
     */
    @Test
    public void testConstructor() {
        assertDoesNotThrow(() -> new Password());
        assertDoesNotThrow(() -> new Password(this.validPasswordString));
        assertDoesNotThrow(() -> new Password(this.validPasswordString, true));

        // If it is already hashed, we do not check if it is valid, as we cannot guarantee that the
        // hash follows our password rules.
        assertDoesNotThrow(() -> new Password(this.invalidPasswordString, true));
        assertThrows(IllegalArgumentException.class,
                () -> new Password(this.invalidPasswordString));

    }

    /**
     * Test that {@link Password#getPasswordString} returns the correct value.
     */
    @Test
    public void testGetPasswordString() {
        assertTrue(new Password(this.validPasswordString, true).getPasswordString()
                .equals(this.validPasswordString));
    }

    /**
     * Test that {@link Password#isValid} doesn't throw any errors and returns the correct value.
     */
    @Test
    public void testIsValid() {
        for (String password : this.illegalPasswords) {
            assertDoesNotThrow(() -> Password.isValid(password));
            assertFalse(Password.isValid(password));
        }

        // Test legal password
        assertTrue(Password.isValid("Password1"));
    }

    /**
     * Test that {@link Password#getRequirements} doesn't throw any errors.
     */
    @Test
    public void testGetRequirements() {
        // Test illegal passwords
        for (String password : this.illegalPasswords) {
            assertTrue(Password.getRequirements(password).length() > 0);
        }

        // Test legal password
        assertTrue(Password.getRequirements(this.validPasswordString).length() == 0);
    }

    /**
     * Test that the same password string generates the same hash.
     */
    @Test
    public void testMD5Hashing() {
        Password password1 = new Password("advanced-passworD123");
        Password password2 = new Password("advanced-passworD123");
        Password password3 = new Password("advanced-passworD1234");

        assertTrue(password1.equals(password2));
        assertFalse(password1.equals(password3));
    }

    /**
     * Test that the equals method compares passwords correctly.
     */
    @Test
    public void testEquals() {
        Password password1 = new Password("Password123");
        Password password2 = new Password("Password123");
        Password password3 = new Password("DifferentPassword098");

        assertTrue(password1.equals(password2));
        assertFalse(password1.equals(password3));
    }

    /**
     * Test that the hashCode method generates hash codes correctly.
     */
    @Test
    public void testHashCode() {
        Password password1 = new Password("Password123");
        Password password2 = new Password("Password123");
        Password password3 = new Password("DifferentPassword098");

        assertTrue(password1.hashCode() == password2.hashCode());
        assertFalse(password1.hashCode() == password3.hashCode());
    }
}
