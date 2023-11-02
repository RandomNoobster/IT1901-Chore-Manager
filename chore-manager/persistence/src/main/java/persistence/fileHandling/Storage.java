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

    private static volatile Storage instance = null;
    private String filePath;
    private JSONConverter jsonConverter;
    private HashMap<String, Collective> collectives = new HashMap<String, Collective>();

    private Storage() {
        EnvironmentConfigurator environmentConfigurator = new EnvironmentConfigurator();
        this.filePath = environmentConfigurator.getSaveFilePath();
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
        System.out.println(this.filePath);
        if (this.jsonConverter.getCreatedNewFile()
                || this.jsonConverter.getCollectives().isEmpty()) {
            this.fillFileWithDefaultData();
            System.out.println("FILL");
        }
        this.collectives = this.jsonConverter.getCollectives();
    }

    /**
     * This method is used to get the instance of the storage. If the instance does not exist, it
     * creates a new one. This method creates a Storage on the default save path.
     */
    public static synchronized Storage getInstance() {
        if (instance == null) {
            instance = new Storage();
        }
        return instance;
    }

    /**
     * This method is used to set a new instance of Storage with the specified filePath. This
     * overrides the current instance.
     *
     * @param filePath The file path of the file to read from.
     * @return The new instance of Storage.
     */
    public static synchronized Storage setInstance(String filePath) {
        instance = new Storage(filePath);
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
     * This method is used to get the collectives from the file system.
     *
     * @param Joincode to collective (if a collective with that joincode exists)
     * 
     * @return Collective with code
     */
    public Collective getCollective(String joinCode) {
        return this.collectives.getOrDefault(joinCode, null);
    }

    /**
     * This method is used to get the limbo collective.
     *
     * @return The limbo collective.
     */
    public Collective getEmptyCollective() {
        return this.collectives.get(Collective.LIMBO_COLLECTIVE_JOIN_CODE);
    }

    /**
     * Adds a new collective to the storage.
     *
     * @param collective The collective to add.
     * @return True if the collective was added successfully, false if a collective with the same
     *         join code already exists.
     */
    public boolean addCollective(Collective collective) {
        if (this.collectives.containsKey(collective.getJoinCode()))
            return false;

        this.collectives.put(collective.getJoinCode(), collective);
        return true;
    }

    /**
     * Removes a collective from the storage.
     *
     * @param collective The collective to remove.
     * @return True if the collective was removed successfully
     */
    public boolean removeCollective(Collective collective) {
        return this.collectives.remove(collective.getJoinCode()) != null;
    }

    /**
     * This method is used to get all persons from the file system.
     *
     * @return A {@link List} of all persons.
     */
    public HashMap<String, Person> getAllPersons() {
        HashMap<String, Person> persons = new HashMap<String, Person>();
        for (Collective collective : this.collectives.values()) {
            persons.putAll(new HashMap<String, Person>(collective.getPersons()));
        }
        return persons;
    }

    /**
     * This method is used to get all persons from the file system.
     *
     * @return A {@link List} of all persons.
     */
    public List<Person> getAllPersonsList() {
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
        for (Person person : this.getAllPersonsList()) {
            chores.addAll(new ArrayList<Chore>(person.getChores()));
        }
        return chores;
    }

    /**
     * This method adds a unique person to a collective.
     *
     * @param person             The person to add
     * @param joinCodeCollective The join code of the collective to add the person to
     * @return True if the person was added, false otherwise
     */
    public boolean addPerson(Person person, String joinCodeCollective) {
        if (!this.collectives.containsKey(joinCodeCollective))
            return false;

        if (this.getAllPersons().containsKey(person.getUsername()))
            return false;

        Collective collective = this.collectives.get(joinCodeCollective);
        return collective.addPerson(person);
    }

    /**
     * This is a method to create a file for the application with default data. This should be
     * called if you do not have any persons in the application.
     */
    public void fillFileWithDefaultData() {
        Collective limboCollective = new Collective("Empty Collective",
                Collective.LIMBO_COLLECTIVE_JOIN_CODE);
        Collective collective = new Collective("The Almighty Collective");
        this.collectives.put(collective.getJoinCode(), collective);
        this.collectives.put(limboCollective.getJoinCode(), limboCollective);

        Person person1 = new Person("Christian", limboCollective);
        Person person2 = new Person("Sebastian", limboCollective);
        Person person3 = new Person("Kristoffer", limboCollective);
        Person person4 = new Person("Lasse", limboCollective);

        Chore chore = new Chore("Chore Test", LocalDate.now(), LocalDate.now(), false, 10,
                "#FFFFFF", person1.getUsername());
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
