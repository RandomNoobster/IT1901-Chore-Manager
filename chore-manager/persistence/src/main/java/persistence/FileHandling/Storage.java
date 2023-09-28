package persistence.FileHandling;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import core.Data.Chore;
import core.Data.Person;

/**
 * This class holds the information about the application's state.
 * It is used to store and retrieve data from the file system.
 * This is a singleton class.
 */
public class Storage {

    private static Storage instance = null;
    private String filePath = "chore-manager-data.json";
    private JSONConverter jsonConverter;
    private HashMap<UUID, Person> persons = new HashMap<UUID, Person>();

    private Storage() {
        this.initialize();
    }

    private Storage(String filePath) {
        this.filePath = filePath;
        this.initialize();
    }

    public void initialize() {
        this.jsonConverter = new JSONConverter(this.filePath);
        if (this.jsonConverter.getCreatedNewFile() || this.jsonConverter.getPersons().isEmpty()) {
            System.out.println("Created new file");
            this.fillFileWithTestData();
        }
        this.persons = this.jsonConverter.getPersons();
    }

    public static Storage getInstance() {
        if (instance == null) {
            instance = new Storage();
        }
        return instance;
    }

    public static Storage getInstance(String filePath) {
        if (instance == null) {
            instance = new Storage(filePath);
        }

        if (!instance.filePath.equals(filePath)) {
            System.out.println("\033[0;31m"
                    + "Storage instance already exists, will not create a new one with the specified path" + "\033[0m");
        }
        return instance;
    }

    public void save() {
        this.jsonConverter.writePersonsToJSON(this.persons);
    }

    public void deleteFileContent() {
        this.jsonConverter.deleteFileContent();
    }

    public HashMap<UUID, Person> getPersons() {
        return new HashMap<UUID, Person>(this.persons);
    }

    public List<Person> getPersonsList() {
        return new ArrayList<Person>(this.persons.values());
    }

    public void addPerson(Person person) {
        this.persons.put(person.getUUID(), person);
    }

    public void removePerson(Person person) {
        this.persons.remove(person.getUUID());
    }

    public void addChore(Chore chore, Person assignedPerson) {
        if (assignedPerson == null) {
            System.out.println("Person is null, cannot add chore");
            return;
        }

        if (this.persons.containsKey(assignedPerson.getUUID())) {
            Person person = this.persons.get(assignedPerson.getUUID());
            person.addChore(chore);
        } else {
            System.out.println("Person does not exist");
        }
    }

    /**
     * This a method to create a test file for the application.
     * This should be called if you do not have any persons in the application.
     * This can be considered test data.
     */
    public void fillFileWithTestData() {
        Person person = new Person("Test Person");
        Chore chore = new Chore("Chore Test", LocalDate.now(), LocalDate.now(), false, 10);
        person.addChore(chore);

        HashMap<UUID, Person> persons = new HashMap<>();
        persons.put(person.getUUID(), person);
        this.persons = persons;
        this.save();
    }

    public boolean deleteFile() {
        boolean deleted = this.jsonConverter.deleteFile();
        instance = null;
        return deleted;
    }

}
