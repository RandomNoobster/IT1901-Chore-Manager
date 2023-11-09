package ui.viewClasses;

import core.data.RestrictedPerson;

/**
 * The PersonMenuItem class is a class that is used to display a person in a menu.
 */
public class PersonMenuItem {

    private RestrictedPerson person;

    /**
     * A constructor for the PersonMenuItem class.
     *
     * @param person The person to be displayed
     */
    public PersonMenuItem(RestrictedPerson person) {
        this.person = new RestrictedPerson(person);

    }

    /**
     * Returns the person that is displayed.
     */
    public RestrictedPerson getPerson() {
        return new RestrictedPerson(this.person);
    }

    /**
     * Returns the name of the person.
     */
    @Override
    public String toString() {
        return this.getPerson().getUsername();
    }

}
