package core.data;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PersonTest {
    private Person person;
    private Collective collective;

    @BeforeEach
    public void polulatePerson() {
        this.collective = new Collective("Test Collective");
        this.person = new Person("John", this.collective);
        this.collective.addPerson(this.person);
    }

    @Test
    public void testConstructor() {
        // Test that the constructor does not throw an exception
        assertDoesNotThrow(() -> new Person("John", this.collective));

        // Test that the constructor properly adds chores
        List<Chore> chores = new ArrayList<Chore>();
        Chore chore = new Chore("Vaske", null, null, false, 10, "#FFFFFF");
        chores.add(chore);
        assertDoesNotThrow(() -> new Person("John", this.collective, chores));
        assertEquals(chore, new Person("John", this.collective, chores).getChores().get(0));
    }

    @Test
    public void testGetName() {
        assertEquals("John", this.person.getUsername());
    }

    @Test
    public void testChores() {
        // Test that addChore adds the chore to the list
        Chore chore = new Chore("Vaske", null, null, false, 10, "#FFFFFF");
        this.person.addChore(chore);
        assertEquals(chore, this.person.getChores().get(0));

        // Test adding multiple chores
        Chore chore2 = new Chore("Støvsuge", null, null, false, 10, "#FFFFFF");
        this.person.addChore(chore2);
        assertEquals(chore, this.person.getChores().get(0));
        assertEquals(chore2, this.person.getChores().get(1));
    }

}
