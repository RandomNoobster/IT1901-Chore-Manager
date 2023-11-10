package core.data;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import core.BaseTestClass;

public class CollectiveTest extends BaseTestClass {
    private Collective limboCollective;
    private Collective otherCollective;

    /**
     * This method is executed before each test. It initializes two Collective instances: -
     * 'limboCollective' with the name "Limbo" and a predefined join code - 'otherCollective' with the
     * name "Other" and a default join code. These instances are used as test data for the
     * subsequent test methods.
     */
    @BeforeEach
    public void setUp() {
        this.limboCollective = new Collective("Limbo", Collective.LIMBO_COLLECTIVE_JOIN_CODE);
        this.otherCollective = new Collective("Other");
    }

    /**
     * This test method validates the constructors of the Collective class. It checks the following:
     * - The default constructor can create a new Collective instance without throwing any
     * exceptions. - The constructor that accepts a name and a join code can create a new Collective
     * instance without throwing any exceptions. - The copy constructor can create a new Collective
     * instance based on an existing one without throwing any exceptions.
     */
    @Test
    public void testConstructor() {
        assertDoesNotThrow(() -> new Collective("Test"));
        assertDoesNotThrow(() -> new Collective("Test", "abcdef"));
        assertDoesNotThrow(() -> new Collective(this.limboCollective));
    }

    /**
     * This test method checks the following: - The isLimboCollective() method does not
     * throw an exception when called on a Collective instance. - The isLimboCollective() method
     * correctly identifies a Collective instance as a Limbo Collective. - The isLimboCollective()
     * method correctly identifies a Collective instance as not a Limbo Collective.
     */
    @Test
    public void testIsLimboCollective() {
        assertDoesNotThrow(() -> this.limboCollective.isLimboCollective());
        assertTrue(this.limboCollective.isLimboCollective());

        assertDoesNotThrow(() -> this.otherCollective.isLimboCollective());
        assertFalse(this.otherCollective.isLimboCollective());
    }

    /**
     * This test method checks the following: - The getJoinCode() method does not throw
     * an exception when called on a Collective instance. - The getJoinCode() method correctly
     * retrieves the join code of a Collective instance.
     */
    @Test
    public void testGetJoinCode() {
        assertDoesNotThrow(() -> this.limboCollective.getJoinCode());
        assertEquals(Collective.LIMBO_COLLECTIVE_JOIN_CODE, this.limboCollective.getJoinCode());
    }

    /**
     * This test method checks the following: - The getName() method does not throw an
     * exception when called on a Collective instance. - The getName() method correctly retrieves
     * the name of a Collective instance.
     */
    @Test
    public void testGetName() {
        assertDoesNotThrow(() -> this.limboCollective.getName());
        assertEquals("Limbo", this.limboCollective.getName());
    }

    /**
     * This test method checks the following: - The getPersons() method does not throw an
     * exception. - A new Person can be added to the collective using the addPerson() method. - The
     * hasPerson() method correctly identifies when a Person is in the Collective - The addPerson() method does
     * not allow duplicate Persons to be added. - A Person can be removed from the collective using
     * the removePerson() method. - After removal, the hasPerson() method correctly identifies that
     * the Person is no longer in the collective.
     */
    @Test
    public void testPersons() {
        assertDoesNotThrow(() -> this.limboCollective.getPersons());

        Person person1 = new Person("Jimmy", this.limboCollective.getJoinCode());
        assertTrue(this.limboCollective.addPerson(person1));
        assertTrue(this.limboCollective.hasPerson(person1));
        assertFalse(this.limboCollective.addPerson(person1));
        assertDoesNotThrow(() -> this.limboCollective.removePerson(person1));
        assertFalse(this.limboCollective.hasPerson(person1));
    }

    /**
     * This test method checks the following: - The getChoresList() method does not throw
     * an exception. - A new Person can be added to the collective using the addPerson() method - A new
     * Chore can be added to the person using the addChore() method. - The getChoresList() method
     * correctly retrieves the list of chores assigned to the person.
     */
    @Test
    public void testGetChoresList() {
        assertDoesNotThrow(() -> this.otherCollective.getChoresList());
        Person person1 = new Person("James", this.otherCollective.getJoinCode());
        this.otherCollective.addPerson(person1);
        person1.addChore(new Chore(null, null, null, false, 0, null, null, null));
        assertTrue(this.otherCollective.getChoresList().equals(person1.getChores()));
    }
}
