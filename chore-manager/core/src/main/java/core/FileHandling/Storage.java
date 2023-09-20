package core.FileHandling;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import core.Data.Chore;
import core.Data.Person;

/**
 * This class holds the information about the applications' state.
 * It is used to store and retrieve data from the file system.
 */
public class Storage {

    private static final String FILE_NAME = "chore-manager-data.json";
    private static JSONConverter jsonConverter = new JSONConverter(FILE_NAME);
    private static List<Person> persons = new ArrayList<>();

    // Initialize
    static {
        persons = jsonConverter.getPersonsList();
    }

    public static void save() {
        jsonConverter.writePersonsToJSON(persons);
    }

    public static void deleteFileContent() {
        jsonConverter.deleteFileContent();
    }

    public static List<Person> getPersons() {
        return new ArrayList<Person>(persons);
    }

    public static void addPerson(Person person) {
        persons.add(person);
    }

    public static void removePerson(Person person) {
        persons.remove(person);
    }

    /**
     * This a method to create a test file for the application.
     * This should be called if you do not have any persons in the application.
     * This can be considered test data.
     */
    public static void createTestFile() {
        Person person = new Person("TEST_PERS");
        Chore chore = new Chore("chore1", LocalDate.now(), LocalDate.now(), false, 10);
        person.addChore(chore);
        persons = new ArrayList<>(Arrays.asList(person));
        Storage.save();
    }

}
