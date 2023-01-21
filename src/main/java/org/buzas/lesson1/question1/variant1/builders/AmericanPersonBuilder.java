package org.buzas.lesson1.question1.variant1.builders;

public class AmericanPersonBuilder extends PersonBuilder{
    @Override
    public void builtFirstName(String firstName) {
        this.person.setFirstName(firstName);
    }

    @Override
    public void builtLastName(String lastName) {
        this.person.setLastName(lastName);
    }

    @Override
    public void builtMiddleName(String middleName) {
        this.person.setMiddleName(middleName);
    }

    @Override
    public void builtCountry() {
        this.person.setCountry("United States of America");
    }

    @Override
    public void builtAddress(String address) {
        this.person.setAddress(address);
    }

    @Override
    public void builtPhone(String phone) {
        this.person.setPhone(phone);
    }

    @Override
    public void builtAge(int age) {
        this.person.setAge(age);
    }

    @Override
    public void builtGender(String gender) {
        this.person.setGender(gender);
    }
}
