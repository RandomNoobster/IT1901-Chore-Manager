package core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import core.data.Chore;
import core.data.Collective;
import core.data.Person;

public class StateTest extends BaseTestClass{
    private static Collective collective = new Collective("Test");
    private static Person person = new Person("Test", collective.getJoinCode());
    private static State state = State.getInstance();

    @BeforeAll
    public static void init() {
        collective.addPerson(person);
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

        state.logIn(person, collective);
        assertEquals(state.getLoggedInUser(), person);

        state.logOutUser();
        assertEquals(state.getLoggedInUser(), null);
    }

    /**
     * Test that {@link State#getCurrentCollective} works as expected.
     */
    @Test
    public void testCurrentCollective() {
        assertEquals(state.getCurrentCollective(), null);

        state.logIn(person, collective);
        assertEquals(state.getCurrentCollective(), collective);
    }

    /**
     * Test that {@link State#logOutUser} works as expected.
     */
    @Test
    public void testGetChoreInCurrentCollective() {
        assertThrows(NullPointerException.class,
                () -> state.getChoreInCurrentCollective(UUID.randomUUID()));

        state.logIn(person, collective);
        assertEquals(state.getChoreInCurrentCollective(UUID.randomUUID()), null);

        Chore chore = new Chore("Test", null, null, false, 1, "#000000", person.getUsername(),
                person.getUsername());
        person.addChore(chore);
        assertEquals(chore, state.getChoreInCurrentCollective(chore.getUUID()));

        state.logOutUser();
        assertThrows(NullPointerException.class,
                () -> state.getChoreInCurrentCollective(UUID.randomUUID()));
    }
}
