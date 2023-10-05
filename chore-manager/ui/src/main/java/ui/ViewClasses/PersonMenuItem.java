package ui.ViewClasses;

import core.Data.Person;

public class PersonMenuItem {

    private Person person;

    public PersonMenuItem(Person person) {
        this.person = person;

    }

    public Person getPerson() {
        return this.person;
    }

    @Override
    public String toString() {
        return this.getPerson().getName();
    }

}
