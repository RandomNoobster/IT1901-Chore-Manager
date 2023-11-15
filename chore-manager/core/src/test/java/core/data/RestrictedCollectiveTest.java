package core.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import core.BaseTestClass;

/**
 * Unit tests for the {@link RestrictedCollective} class.
 */
public class RestrictedCollectiveTest extends BaseTestClass {

    /**
     * Test the {@link RestrictedCollective#getJoinCode()} method to ensure that a join code is not
     * null.
     */
    @Test
    public void testGetJoinCode() {
        RestrictedCollective collective = new RestrictedCollective("Test Collective");
        assertNotNull(collective.getJoinCode());
    }

    /**
     * Test the {@link RestrictedCollective#getName()} method to ensure that the correct name is
     * returned.
     */
    @Test
    public void testGetName() {
        RestrictedCollective collective = new RestrictedCollective("Test Collective");
        assertEquals("Test Collective", collective.getName());
    }

    /**
     * Test the {@link RestrictedCollective#isLimboCollective()} method to check if a collective is
     * a limbo collective.
     */
    @Test
    public void testIsLimboCollective() {
        // Creating a limbo collective with a predefined join code
        RestrictedCollective limboCollective = new RestrictedCollective("Limbo Collective",
                RestrictedCollective.LIMBO_COLLECTIVE_JOIN_CODE);
        assertTrue(limboCollective.isLimboCollective());

        // Creating a regular collective without a predefined join code
        RestrictedCollective regularCollective = new RestrictedCollective("Regular Collective");
        assertFalse(regularCollective.isLimboCollective());
    }

    /**
     * Test the {@link RestrictedCollective#encodeToJSONObject()} method to ensure correct encoding
     * to a {@link JSONObject}.
     */
    @Test
    public void testEncodeToJSONObject() {
        RestrictedCollective collective = new RestrictedCollective("Test Collective", "123456");
        JSONObject jsonObject = RestrictedCollective.encodeToJSONObject(collective);

        assertEquals("Test Collective", jsonObject.getString("name"));
        assertEquals("123456", jsonObject.getString("joinCode"));
    }

    /**
     * Test the {@link RestrictedCollective#decodeFromJSON(JSONObject)} method to ensure correct
     * decoding from a {@link JSONObject}.
     */
    @Test
    public void testDecodeFromJSON() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "Test Collective");
        jsonObject.put("joinCode", "654321");

        RestrictedCollective collective = RestrictedCollective.decodeFromJSON(jsonObject);

        assertEquals("Test Collective", collective.getName());
        assertEquals("654321", collective.getJoinCode());
    }

    /**
     * Test the {@link RestrictedCollective#decodeFromJSON(JSONObject)} method with an invalid
     * {@link JSONObject}. Expects an {@link IllegalArgumentException} to be thrown.
     */
    @Test
    public void testDecodeFromJSONInvalidJSONObject() {
        JSONObject jsonObject = new JSONObject();

        // Missing "name" field
        jsonObject.put("joinCode", "654321");

        assertThrows(IllegalArgumentException.class,
                () -> RestrictedCollective.decodeFromJSON(jsonObject));
    }
}