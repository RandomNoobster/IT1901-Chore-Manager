package core.data;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import core.BaseTestClass;

/**
 * Unit tests for the {@link RestrictedPerson} class.
 */
public class RestrictedPersonTest extends BaseTestClass {
    private static Collective collective = new Collective("Test");
    private static RestrictedPerson testPerson = new RestrictedPerson("Test",
            collective.getJoinCode());

    /**
     * Test that the constructor can construct a new object without throwing any errors.
     */
    @Test
    public void testConstructor() {
        assertDoesNotThrow(
                () -> new RestrictedPerson(testPerson.getUsername(), collective.getJoinCode()));
    }

    /**
     * Test that {@link RestrictedPerson#setCollective} and
     * {@link RestrictedPerson#getCollectiveJoinCode} doesn't throw any errors and returns the
     * expected values.
     */
    @Test
    public void testCollective() {
        // Set the collective for the test person
        testPerson.setCollective(collective.getJoinCode());

        // Verify that the collective join code matches the expected value
        assertEquals(collective.getJoinCode(), testPerson.getCollectiveJoinCode());
    }

    /**
     * Test that {@link RestrictedPerson#encodeToJSONObject} doesn't throw any errors.
     */
    @Test
    public void testEncodeToJSON() {
        // Verify that encoding to JSON does not throw any errors
        assertDoesNotThrow(() -> RestrictedPerson.encodeToJSONObject(testPerson));
    }

    /**
     * Test that {@link RestrictedPerson#decodeFromJSON} doesn't throw any errors and returns the
     * correct {@link RestrictedPerson} object.
     */
    @Test
    public void testDecodeFromJSON() {
        // Encode the test person to JSON
        JSONObject encodedJson = RestrictedPerson.encodeToJSONObject(testPerson);

        // Decode the JSON back to a RestrictedPerson object
        RestrictedPerson decodedPerson = RestrictedPerson.decodeFromJSON(encodedJson);

        // Verify that the decoded person has the same username as the original test person
        assertEquals(testPerson.getUsername(), decodedPerson.getUsername());

        // Verify that decoding from a null JSON returns null
        assertNull(RestrictedPerson.decodeFromJSON(null));
    }

    /**
     * Test the {@link RestrictedPerson#getDisplayName()} method to ensure it returns the correct
     * display name.
     */
    @Test
    public void testGetDisplayName() {
        // Verify that the getDisplayName method returns the correct display name
        assertEquals(testPerson.getUsername(), testPerson.getDisplayName());
    }

    /**
     * Test the {@link RestrictedPerson#isInEmptyCollective()} method when the person is in the
     * limbo collective.
     */
    @Test
    public void testIsInEmptyCollectiveTrue() {
        // Set the collective join code to the limbo collective join code
        testPerson.setCollective(Collective.LIMBO_COLLECTIVE_JOIN_CODE);

        // Verify that isInEmptyCollective returns true for the limbo collective
        assertTrue(testPerson.isInEmptyCollective());
    }

    /**
     * Test the {@link RestrictedPerson#isInEmptyCollective()} method when the person is in a
     * non-empty collective.
     */
    @Test
    public void testIsInEmptyCollectiveFalse() {
        // Set the collective join code to a non-empty collective join code
        testPerson.setCollective("NonEmptyCollective");

        // Verify that isInEmptyCollective returns false for a non-empty collective
        assertFalse(testPerson.isInEmptyCollective());
    }

    /**
     * Test the {@link RestrictedPerson#toString()} method to ensure it returns the correct string
     * representation.
     */
    @Test
    public void testToString() {
        // Verify that toString returns the username of the person
        assertEquals(testPerson.getUsername(), testPerson.toString());
    }
}