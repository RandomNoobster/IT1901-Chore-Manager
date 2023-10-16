package persistence.fileHandling;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import core.data.Chore;
import core.data.Person;

/**
 * This class holds the information about the application's state. It is used to store and retrieve
 * data from the file system. This is a singleton class.
 */
public class Storage {

    private static Storage instance = null;
    private String filePath = "chore-manager-data.json";
    private JSONConverter jsonConverter;
    private HashMap<String, Person> persons = new HashMap<String, Person>();
    private static Person user = null;

    private Storage() {
        this.initialize();
    }

    private Storage(String filePath) {
        this.filePath = filePath;
        this.initialize();
    }

    /**
     * This method is used to initialize the storage. It will create a new file if it does not
     * exist.
     */
    public void initialize() {
        this.jsonConverter = new JSONConverter(this.filePath);
        if (this.jsonConverter.getCreatedNewFile() || this.jsonConverter.getPersons().isEmpty()) {
            System.out.println("Created new file");
            this.fillFileWithTestData();
        }
        this.persons = this.jsonConverter.getPersons();
    }

    /**
     * This method is used to get the instance of the storage. If the instance does not exist, it
     * creates a new one.
     */
    public static Storage getInstance() {
        if (instance == null) {
            instance = new Storage();
        }
        return instance;
    }

    /**
     * This method is used to get the instance of the storage at a certain path. If the instance
     * does not exist, a new one is made.
     */
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

    /**
     * This method is used to get the file path of the storage.
     *
     * @return The file path of the storage.
     */
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

    /**
     * This method is used to save the persons to the file system.
     */
    public void save() {
        this.jsonConverter.writePersonsToJSON(this.persons);
    }

    /**
     * This method is used to set the active user of the application.
     */
    public static void setUser(Person person) {
        user = new Person(person);
    }

    /**
     * This method is used to get the active user of the application.
     */
    public static Person getUser() {
        return user;
    }

    /**
     * This method is used to delete the content of the file.
     */
    public void deleteFileContent() {
        this.jsonConverter.deleteFileContent();
    }

    /**
     * This method is used to get the persons from the file system.
     *
     * @return A {@link HashMap} of persons and their unique keys.
     */
    public HashMap<String, Person> getPersons() {
        return new HashMap<String, Person>(this.persons);
    }

    /**
     * This method is used to get the persons from the file system.
     *
     * @return A {@link List} of persons.
     */
    public List<Person> getPersonsList() {
        return new ArrayList<Person>(this.persons.values());
    }

    /**
     * This methods adds a person to the file system.
     *
     * @param person The person to add.
     * @return True if the person was added, false if they are already added.
     */
    public boolean addPerson(Person person) {

        if (this.persons.containsKey(person.getUsername()))
            return false;

        this.persons.put(person.getUsername(), person);
        return true;
    }

    /**
     * This method is used to remove a person from the file system.
     *
     * @param person The person to remove
     */
    public void removePerson(Person person) {
        this.persons.remove(person.getUsername());
    }

    /**
     * This method gets a list of all chores belonging to any user.
     *
     * @return All chores.
     */
    public List<Chore> getChoresList() {
        List<Chore> chores = new ArrayList<Chore>();
        for (Person person : this.persons.values()) {
            chores.addAll(new ArrayList<Chore>(person.getChores()));
        }
        return chores;
    }

    /**
     * This method adds a chore to a person.
     *
     * @param chore          The chore to add.
     * @param assignedPerson The person to add the chore to.
     */
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

    /**
     * This method is used to delete the storage file.
     *
     * @return True if the file was deleted, false otherwise.
     */
    public boolean deleteFile() {
        boolean deleted = this.jsonConverter.deleteFile();
        deleteInstance();
        return deleted;
    }

}
