package core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import core.data.Chore;
import core.data.Collective;
import core.data.Person;

public class StateTest extends BaseTestClass {
    private static Collective collective = new Collective("Test");
    private static Person testPerson = new Person("Test", collective.getJoinCode());
    private static State state = State.getInstance();

    @BeforeAll
    public static void init() {
        collective.addPerson(testPerson);
    }

    @BeforeEach
    public void setUp() {
        State.getInstance().logOutUser();
    }

    /**
     * Test that {@link State#getInstance} works as expected. We always want the same object
     * reference to be returned.
     */
    @Test
    public void testGetInstance() {
        assertEquals(state, State.getInstance());
    }

    /**
     * Test that {@link State#logIn} works as expected.
     */
    @Test
    public void testLoggedInUser() {
        assertEquals(state.getLoggedInUser(), null);

        state.logIn(testPerson, collective);
        assertEquals(state.getLoggedInUser(), testPerson);

        state.logOutUser();
        assertEquals(state.getLoggedInUser(), null);
    }

    /**
     * Test that {@link State#getCurrentCollective} works as expected.
     */
    @Test
    public void testCurrentCollective() {
        assertEquals(state.getCurrentCollective(), null);

        state.logIn(testPerson, collective);
        assertEquals(state.getCurrentCollective(), collective);
    }

    /**
     * Test that {@link State#logOutUser} works as expected.
     */
    @Test
    public void testGetChoreInCurrentCollective() {
        assertThrows(NullPointerException.class,
                () -> state.getChoreInCurrentCollective(UUID.randomUUID()));

        state.logIn(testPerson, collective);
        assertEquals(state.getChoreInCurrentCollective(UUID.randomUUID()), null);

        Chore chore = new Chore("Test", null, null, 1, "#000000", testPerson.getUsername(),
                testPerson.getUsername());
        testPerson.addChore(chore);
        assertEquals(chore, state.getChoreInCurrentCollective(chore.getUUID()));

        state.logOutUser();
        assertThrows(NullPointerException.class,
                () -> state.getChoreInCurrentCollective(UUID.randomUUID()));
    }

    /**
     * Test that {@link State#addChore} works as expected.
     */
    @Test
    public void testAddChore() {
        Chore chore = new Chore("Test", null, null, 1, "#000000", testPerson.getUsername(),
                testPerson.getUsername());

        // Assert false when assignee is null
        assertFalse(state.addChore(chore, null));

        // Assert false while currentCollective is not set
        assertFalse(state.addChore(chore, testPerson));

        // Assert true once collective is set and person is valid
        state.setCurrentCollective(collective);
        assertTrue(state.addChore(chore, testPerson));
        assertEquals(chore.getUUID(), state.getChoreInCurrentCollective(chore.getUUID()).getUUID());
    }
}
