package persistence.FileHandling;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import core.Data.Chore;
import core.Data.Person;

/**
 * This class holds the information about the application's state.
 * It is used to store and retrieve data from the file system.
 */
public class Storage {

    private static final String FILE_NAME = "chore-manager-data.json";
    private static JSONConverter jsonConverter = new JSONConverter(FILE_NAME);
    private static List<Person> persons = new ArrayList<>();
    private static Person user;

    // Initialize
    static {
        if (jsonConverter.getCreatedNewFile() || jsonConverter.getPersonsList().isEmpty()) {
            System.out.println("Created new file");
            fillFileWithTestData();
        }
        persons = jsonConverter.getPersonsList();
    }

    public static void save() {
        jsonConverter.writePersonsToJSON(persons);
    }

    public static void setuser(Person person) {
        user = person;
    }

    public static Person getUser() {
        return user;
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
    public static void fillFileWithTestData() {
        Person person = new Person("Christian");

        Chore chore = new Chore("Chore Test", LocalDate.now(), LocalDate.now(), false, 10, "#FFFFFF");
        person.addChore(chore);
        persons = new ArrayList<>(
                Arrays.asList(person, new Person("Sebastian"), new Person("Kristoffer"), new Person("Lasse")));
        Storage.save();
    }

}
