package persistence.FileHandling;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import core.Data.Chore;
import core.Data.Person;

/**
 * This class holds the information about the application's state. It is used to store and retrieve
 * data from the file system. This is a singleton class.
 */
public class Storage {

    private static Storage instance = null;
    private String filePath = "chore-manager-data.json";
    private JSONConverter jsonConverter;
    private HashMap<String, Person> persons = new HashMap<String, Person>();
    private static Person user;

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
                    + "NOTE: Storage instance already exists, will not create a new one with the specified path"
                    + "\033[0m");
        }
        return instance;
    }

    public String getFilePath() {
        return this.filePath;
    }

    /**
     * Use with care. This method will delete the current instance, since it is a singleton, this
     * will affect all other classes using this singleton for information.
     */
    public static void deleteInstance() {
        instance = null;
    }

    public void save() {
        this.jsonConverter.writePersonsToJSON(this.persons);
    }

    public static void setUser(Person person) {
        user = person;
    }

    public static Person getUser() {
        return user;
    }

    public void deleteFileContent() {
        this.jsonConverter.deleteFileContent();
    }

    public HashMap<String, Person> getPersons() {
        return new HashMap<String, Person>(this.persons);
    }

    public List<Person> getPersonsList() {
        return new ArrayList<Person>(this.persons.values());
    }

    public boolean addPerson(Person person) {

        if (this.persons.containsKey(person.getUsername()))
            return false;

        this.persons.put(person.getUsername(), person);
        return true;
    }

    public void removePerson(Person person) {
        this.persons.remove(person.getUsername());
    }

    public List<Chore> getChoresList() {
        List<Chore> chores = new ArrayList<Chore>();
        for (Person person : this.persons.values()) {
            chores.addAll(new ArrayList<Chore>(person.getChores()));
        }
        return chores;
    }

    public void addChore(Chore chore, Person assignedPerson) {
        if (assignedPerson == null) {
            System.out.println("Person is null, cannot add chore");
            return;
        }

        if (this.persons.containsKey(assignedPerson.getUsername())) {
            Person person = this.persons.get(assignedPerson.getUsername());
            person.addChore(chore);
        } else {
            System.out.println("Person does not exist");
        }
    }

    /**
     * This is a method to create a test file for the application. This should be called if you do
     * not have any persons in the application. This can be considered test data.
     */
    public void fillFileWithTestData() {
        Person person1 = new Person("Christian");
        Person person2 = new Person("Sebastian");
        Person person3 = new Person("Kristoffer");
        Person person4 = new Person("Lasse");

        Chore chore = new Chore("Chore Test", LocalDate.now(), LocalDate.now(), false, 10,
                "#FFFFFF");
        person1.addChore(chore);

        HashMap<String, Person> persons = new HashMap<>();
        persons.put(person1.getUsername(), person1);
        persons.put(person2.getUsername(), person2);
        persons.put(person3.getUsername(), person3);
        persons.put(person4.getUsername(), person4);
        this.persons = persons;
        this.save();
    }

    public boolean deleteFile() {
        boolean deleted = this.jsonConverter.deleteFile();
        deleteInstance();
        return deleted;
    }

}
