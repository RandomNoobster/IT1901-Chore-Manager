package core.data;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CollectiveTest {
    private Collective limboCollective;
    private Collective otherCollective;

    @BeforeEach
    public void setUp() {
        this.limboCollective = new Collective("Limbo", Collective.LIMBO_COLLECTIVE_JOIN_CODE);
        this.otherCollective = new Collective("Other");
    }

    @Test
    public void testConstructor() {
        assertDoesNotThrow(() -> new Collective("Test"));
        assertDoesNotThrow(() -> new Collective("Test", "abcdef"));
        assertDoesNotThrow(() -> new Collective(this.limboCollective));
    }

    @Test
    public void testIsLimboCollective() {
        assertDoesNotThrow(() -> this.limboCollective.isLimboCollective());
        assertTrue(this.limboCollective.isLimboCollective());

        assertDoesNotThrow(() -> this.otherCollective.isLimboCollective());
        assertFalse(this.otherCollective.isLimboCollective());
    }

    @Test
    public void testGetJoinCode() {
        assertDoesNotThrow(() -> this.limboCollective.getJoinCode());
    }

    @Test
    public void testGetName() {
        assertDoesNotThrow(() -> this.limboCollective.getName());
    }

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

    @Test
    public void testGetChoresList() {
        assertDoesNotThrow(() -> this.otherCollective.getChoresList());
        Person person1 = new Person("James", this.otherCollective.getJoinCode());
        this.otherCollective.addPerson(person1);
        person1.addChore(new Chore(null, null, null, false, 0, null, null, null));
        assertTrue(this.otherCollective.getChoresList().equals(person1.getChores()));
    }
}
