package core.data;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import core.BaseTestClass;

/**
 * Unit tests for the {@link Person} class.
 */
public class PersonTest extends BaseTestClass {
    private Person person;
    private Collective collective;

    /**
     * Before each test, create a new Person with some sample values.
     */
    @BeforeEach
    public void populatePerson() {
        this.collective = new Collective("Test Collective");
        this.person = new Person("John", this.collective.getJoinCode());
        this.collective.addPerson(this.person);
    }

    /**
     * Test that the constructor can construct a new object without throwing any errors. Also test
     * if the constructor is able to add Chores correctly.
     */
    @Test
    public void testConstructor() {
        // Test that the constructor does not throw an exception
        assertDoesNotThrow(
                () -> new Person("John", this.collective.getJoinCode(), new ArrayList<Chore>()));
        assertDoesNotThrow(
                () -> new Person("John", this.collective.getJoinCode(), new Password(), "John"));
        assertDoesNotThrow(() -> new Person("John", this.collective.getJoinCode()));

        // Test that the constructor properly adds chores
        List<Chore> chores = new ArrayList<Chore>();
        Chore chore = new Chore("Vaske", null, null, false, 10, "#FFFFFF", "Creator", "Assignee");
        chores.add(chore);
        assertDoesNotThrow(() -> new Person("John", this.collective.getJoinCode(), chores));
        assertEquals(chore,
                new Person("John", this.collective.getJoinCode(), chores).getChores().get(0));
    }

    /**
     * Test that {@link Person#getUsername} doesn't throw any errors and returns the expected value.
     */
    @Test
    public void testGetName() {
        assertDoesNotThrow(() -> this.person.getUsername());
        assertEquals("John", this.person.getUsername());
    }

    /**
     * Test that {@link Person#addChore} and {@link Person#deleteChore} doesn't throw any errors and
     * sets the value correctly.
     */
    @Test
    public void testChores() {
        // Test that addChore adds the chore to the list
        Chore chore = new Chore("Vaske", null, null, false, 10, "#FFFFFF", "Creator", "Assignee");
        this.person.addChore(chore);
        assertEquals(chore, this.person.getChores().get(0));

        // Test adding multiple chores
        Chore chore2 = new Chore("Støvsuge", null, null, false, 10, "#FFFFFF", "Creator",
                "Assignee");
        this.person.addChore(chore2);
        assertEquals(chore, this.person.getChores().get(0));
        assertEquals(chore2, this.person.getChores().get(1));

        // Test removing chores
        assertDoesNotThrow(() -> this.person.deleteChore(chore));
        assertEquals(chore2, this.person.getChores().get(0));
        assertTrue(this.person.deleteChore(chore2.getUUID()));
        assertEquals(0, this.person.getChores().size());
        assertFalse(this.person.deleteChore(chore2.getUUID()));
    }

    /**
     * Test that {@link Person#getCollective} doesn't throw any errors and returns the expected
     * value.
     */
    @Test
    public void testGetCollective() {
        assertDoesNotThrow(() -> this.person.getCollectiveJoinCode());
        assertEquals(this.collective.getJoinCode(), this.person.getCollectiveJoinCode());
    }

    /**
     * Test that {@link Person#isInEmptyCollective} doesn't throw any errors and returns the
     * expected value.
     */
    @Test
    public void testIsInEmptyCollective() {
        assertDoesNotThrow(() -> this.person.isInEmptyCollective());
        assertFalse(this.person.isInEmptyCollective());

        Person person2 = new Person("John", Collective.LIMBO_COLLECTIVE_JOIN_CODE);
        assertDoesNotThrow(() -> person2.isInEmptyCollective());
        assertTrue(person2.isInEmptyCollective());

        Person person3 = new Person("Jimmy", null);
        assertDoesNotThrow(() -> person3.isInEmptyCollective());
        assertTrue(person3.isInEmptyCollective());
    }

    /**
     * Test that {@link Person#getPassword()} doesn't throw any errors and returns the expected
     * value.
     */
    @Test
    public void testGetPassword() {
        assertDoesNotThrow(() -> this.person.getPassword());
        assertNotNull(this.person.getPassword());
    }

    /**
     * Test that {@link Person#toString()} works without throwing exceptions.
     */
    @Test
    public void testToString() {
        assertDoesNotThrow(() -> this.person.toString());
    }

    /**
     * Test that {@link Person#encodeToJSONObject} doesn't throw any errors.
     */
    @Test
    public void testEncodeToJSON() {
        assertDoesNotThrow(() -> Person.encodeToJSONObject(this.person));
        assertThrows(IllegalArgumentException.class, () -> Person.encodeToJSONObject(null));
    }

    /**
     * Test that {@link Person#decodeFromJSON} doesn't throw any errors and returns the correct
     * {@link Person} object.
     */
    @Test
    public void testDecodeFromJSON() {
        // Encode the test person to JSON
        JSONObject encodedJson = Person.encodeToJSONObject(this.person);

        // Decode the JSON back to a Person object
        Person decodedPerson = Person.decodeFromJSON(encodedJson);

        // Verify that the decoded person has the same username as the original test person
        assertEquals(this.person.getUsername(), decodedPerson.getUsername());

        // Verify that decoding from a null JSON returns null
        assertNull(Person.decodeFromJSON(null));
    }
}
