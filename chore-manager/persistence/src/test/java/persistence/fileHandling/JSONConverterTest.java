package persistence.fileHandling;

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

import core.data.Chore;
import core.data.Collective;
import core.data.Person;

public class JSONConverterTest extends BaseTestClass {

    private JSONConverter jsonConverter;
    private final String fileName = "chore-manager-test-jsonconverter.json";
    private final LocalDate date = LocalDate.of(2020, 1, 1);

    // Low-level classes below Storage, needs to have test filenames defined
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
        Chore chore = new Chore("test", this.date, this.date, 10, "#FFFFFF", "creator", "Assignee");
        List<Chore> chores = new ArrayList<Chore>(Arrays.asList(chore));
        Collective collective = new Collective("test");
        Person person = new Person("username", collective.getJoinCode(), chores);
        collective.addPerson(person);
        HashMap<String, Collective> collectives = new HashMap<String, Collective>();
        collectives.put(collective.getJoinCode(), collective);

        this.jsonConverter.writeCollectiveToJSON(collectives);
        assertTrue(this.jsonConverter.getFile().length() > 0);

        HashMap<String, Collective> collectivesFromJSON = this.jsonConverter.getCollectives();
        assertTrue(collectivesFromJSON.containsKey(collective.getJoinCode()));

        HashMap<String, Person> persons1 = collective.getPersons();
        HashMap<String, Person> persons2 = collectivesFromJSON.get(collective.getJoinCode())
                .getPersons();

        // Check that all keys are equal
        assertTrue(persons1.keySet().stream().allMatch(persons2::containsKey));
        assertTrue(persons2.keySet().stream().allMatch(persons1::containsKey));

        assertEquals(Chore.encodeToJSONObject(person.getChores().get(0)).toString(),
                Chore.encodeToJSONObject(collectivesFromJSON.get(collective.getJoinCode())
                        .getPerson(person.getUsername()).getChores().get(0)).toString());
    }

}
