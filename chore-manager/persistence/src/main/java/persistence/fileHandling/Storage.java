package persistence.fileHandling;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import core.data.Chore;
import core.data.Collective;
import core.data.Person;

/**
 * This class holds the information about the application's state. It is used to store and retrieve
 * data from the file system. This is a singleton class.
 */
public class Storage {

    private static Storage instance = null;
    private String filePath = "chore-manager-data.json";
    private JSONConverter jsonConverter;
    private HashMap<String, Collective> collectives = new HashMap<String, Collective>();

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
        if (this.jsonConverter.getCreatedNewFile()
                || this.jsonConverter.getCollectives().isEmpty()) {
            System.out.println("Created new file");
            this.fillFileWithTestData();
        }
        this.collectives = this.jsonConverter.getCollectives();
    }

    /**
     * This method is used to get the instance of the storage. If the instance does not exist, it
     * creates a new one.
     */
    public static synchronized Storage getInstance() {
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
        this.jsonConverter.writeCollectiveToJSON(this.collectives);
    }

    /**
     * This method is used to delete the content of the file.
     */
    public void deleteFileContent() {
        this.jsonConverter.deleteFileContent();
    }

    /**
     * This method is used to get the collectives from the file system.
     *
     * @return A {@link HashMap} of collectives and their unique keys.
     */
    public HashMap<String, Collective> getCollectives() {
        return new HashMap<String, Collective>(this.collectives);
    }

    /**
     * This method is used to get all persons from the file system.
     *
     * @return A {@link List} of all persons.
     */
    public List<Person> getAllPersons() {
        List<Person> persons = new ArrayList<Person>();
        for (Collective collective : this.collectives.values()) {
            persons.addAll(new ArrayList<Person>(collective.getPersons().values()));
        }
        return persons;
    }

    /**
     * This method is used to get all chores from the file system.
     *
     * @return A {@link List} of all chores.
     */
    public List<Chore> getAllChores() {
        List<Chore> chores = new ArrayList<Chore>();
        for (Person person : this.getAllPersons()) {
            chores.addAll(new ArrayList<Chore>(person.getChores()));
        }
        return chores;
    }

    /**
     * This is a method to create a test file for the application. This should be called if you do
     * not have any persons in the application. This can be considered test data.
     */
    public void fillFileWithTestData() {
        HashMap<String, Collective> collectives = new HashMap<>();
        Collective collective = new Collective("The Almighty Collective");
        collectives.put(collective.getJoinCode(), collective);

        Person person1 = new Person("Christian");
        Person person2 = new Person("Sebastian");
        Person person3 = new Person("Kristoffer");
        Person person4 = new Person("Lasse");

        Chore chore = new Chore("Chore Test", LocalDate.now(), LocalDate.now(), false, 10,
                "#FFFFFF");
        person1.addChore(chore);

        collective.addPerson(person1);
        collective.addPerson(person2);
        collective.addPerson(person3);
        collective.addPerson(person4);

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
