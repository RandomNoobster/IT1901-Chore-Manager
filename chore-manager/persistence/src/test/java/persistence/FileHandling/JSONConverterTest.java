package persistence.FileHandling;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

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
        HashMap<String, Person> persons = new HashMap<String, Person>();
        persons.put(person.getName(), person);

        this.jsonConverter.writePersonsToJSON(persons);
        assertTrue(this.jsonConverter.getFile().length() > 0);

        HashMap<String, Person> personsFromJSON = this.jsonConverter.getPersons();
        assertTrue(personsFromJSON.containsKey(person.getName()));
        assertEquals(person, personsFromJSON.get(person.getName()));

        assertEquals(person.getChores().get(0).encodeToJSON(),
                personsFromJSON.get(person.getName()).getChores().get(0).encodeToJSON());
    }

}
