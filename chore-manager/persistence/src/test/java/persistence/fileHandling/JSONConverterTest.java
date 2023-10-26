package persistence.fileHandling;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import core.data.Chore;
import core.data.Collective;
import core.data.Person;

public class JSONConverterTest {

    private JSONConverter jsonConverter;
    private final String fileName = "chore-manager-test-jsonconverter.json";
    private final LocalDate date = LocalDate.of(2020, 1, 1);

    private <T> boolean compareTwoLists(Collection<T> list1, Collection<T> list2) {
        return list1.containsAll(list2) && list2.containsAll(list1);
    }

    /**
     * Sets the current environment to test
     */
    @BeforeAll
    public static void setTestEnvironment() {
        System.setProperty("env", "test");
    }

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
        Chore chore = new Chore("test", this.date, this.date, false, 10, "#FFFFFF", "creator");
        List<Chore> chores = new ArrayList<Chore>(Arrays.asList(chore));
        Collective collective = new Collective("test");
        Person person = new Person("username", collective, chores);
        collective.addPerson(person);
        HashMap<String, Collective> collectives = new HashMap<String, Collective>();
        collectives.put(collective.getJoinCode(), collective);

        this.jsonConverter.writeCollectiveToJSON(collectives);
        assertTrue(this.jsonConverter.getFile().length() > 0);

        HashMap<String, Collective> collectivesFromJSON = this.jsonConverter.getCollectives();
        assertTrue(collectivesFromJSON.containsKey(collective.getJoinCode()));
        assertTrue(this.compareTwoLists(collective.getPersonsList(),
                collectivesFromJSON.get(collective.getJoinCode()).getPersonsList()));

        assertEquals(person.getChores().get(0).encodeToJSON(),
                collectivesFromJSON.get(collective.getJoinCode()).getPersons()
                        .get(person.getUsername()).getChores().get(0).encodeToJSON());
    }

}
