package core.Data;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PersonTest {
    Person person;

    @BeforeEach
    public void polulatePerson() {
        this.person = new Person("John");
    }

    @Test
    public void testConstructor() {
        // Test that the constructor does not throw an exception
        assertDoesNotThrow(() -> new Person("John"));

        // Test that the constructor properly adds chores
        List<Chore> chores = new ArrayList<Chore>();
        Chore chore = new Chore("Vaske", null, null, false, 10);
        chores.add(chore);
        assertDoesNotThrow(() -> new Person("John", chores));
        assertEquals(chore, new Person("John", chores).getChores().get(0));

    }

    @Test
    public void testGetName() {
        assertEquals("John", this.person.getName());
    }

    @Test
    public void testChores() {
        // Test that addChore adds the chore to the list
        Chore chore = new Chore("Vaske", null, null, false, 10);
        this.person.addChore(chore);
        assertEquals(chore, this.person.getChores().get(0));

        // Test adding multiple chores
        Chore chore2 = new Chore("Støvsuge", null, null, false, 10);
        this.person.addChore(chore2);
        assertEquals(chore, this.person.getChores().get(0));
        assertEquals(chore2, this.person.getChores().get(1));
    }

    @Test
    public void testUniqueID() {
        Person person2 = new Person("Henry");
        assertFalse(person2.getUUID().equals(this.person.getUUID()));
    }

}
