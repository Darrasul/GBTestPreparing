package org.buzas.lesson1.question1.variant1.builders;

import org.buzas.lesson1.question1.variant1.units.Person;

public abstract class PersonBuilder {
    Person person;

    public void createPerson() {
        this.person = new Person();
    }

    public abstract void builtFirstName(String firstName);
    public abstract void builtLastName(String lastName);
    public abstract void builtMiddleName(String middleName);
    public abstract void builtCountry();
    public abstract void builtAddress(String address);
    public abstract void builtPhone(String phone);
    public abstract void builtAge(int age);
    public abstract void builtGender(String gender);

    public Person getPerson() {
        return person;
    }
}
