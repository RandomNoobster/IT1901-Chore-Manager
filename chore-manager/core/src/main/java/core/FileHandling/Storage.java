package core.FileHandling;

import java.util.ArrayList;
import java.util.List;

import core.Data.Person;

public class Storage {

    private static String fileName = "data.json";
    private static JSONConverter jsonConverter = new JSONConverter(fileName);
    private static List<Person> persons = new ArrayList<>();

    public static void initialize() {
        persons = jsonConverter.getPersonsList();
    }

    public static void save() {
        jsonConverter.writePersonsToJSON(persons);
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

}
