package persistence.FileHandling;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import core.Data.Chore;
import core.Data.Person;

public class JSONConverterTest {

    private JSONConverter jsonConverter;
    private final String fileName = "chore-manager-test-jsonconverter.json";
    private final LocalDate date = LocalDate.of(2020, 1, 1);

    @BeforeEach
    public void populateJSONConverter() {
        this.jsonConverter = new JSONConverter(this.fileName);
    }

    @AfterEach
    public void deleteFile() {
        this.jsonConverter.getFile().delete();
    }

    @Test
    public void testCreateNewFile() {
        assertTrue(this.jsonConverter.getCreatedNewFile());

        JSONConverter jsonConverter2 = new JSONConverter(this.fileName);
        assertFalse(jsonConverter2.getCreatedNewFile());
    }

    @Test
    public void writeAndReadToJSONTest() {
        Chore chore = new Chore("test", this.date, this.date, false, 10, "#FFFFFF");
        List<Chore> chores = new ArrayList<Chore>(Arrays.asList(chore));
        Person person = new Person("name", chores);
        HashMap<UUID, Person> persons = new HashMap<UUID, Person>();
        persons.put(person.getUUID(), person);

        this.jsonConverter.writePersonsToJSON(persons);
        assertTrue(this.jsonConverter.getFile().length() > 0);

        HashMap<UUID, Person> personsFromJSON = this.jsonConverter.getPersons();
        assertTrue(personsFromJSON.containsKey(person.getUUID()));
        assertEquals(person, personsFromJSON.get(person.getUUID()));

        assertEquals(person.getChores().get(0).encodeToJSON(),
                personsFromJSON.get(person.getUUID()).getChores().get(0).encodeToJSON());
    }

}
