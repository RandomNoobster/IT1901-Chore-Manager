package ui.viewClasses;

import core.data.Person;

/**
 * The PersonMenuItem class is a class that is used to display a person in a menu.
 */
public class PersonMenuItem {

    private Person person;

    /**
     * A constructor for the PersonMenuItem class.
     *
     * @param person The person to be displayed
     */
    public PersonMenuItem(Person person) {
        this.person = new Person(person);

    }

    /**
     * Returns the person that is displayed.
     */
    public Person getPerson() {
        return new Person(this.person);
    }

    /** 
     * Returns the name of the person.
     */
    @Override
    public String toString() {
        return this.getPerson().getUsername();
    }

}
