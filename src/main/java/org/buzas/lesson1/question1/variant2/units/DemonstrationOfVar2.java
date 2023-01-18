package org.buzas.lesson1.question1.variant2.units;

public class DemonstrationOfVar2 {
    public static void main(String[] args) {
        Person person = new Person.PersonBuilder("FirstName")
                .setLastName("LastName")
                .setMiddleName("MiddleName")
                .setCountry("Country")
                .setAddress("Address")
                .setPhone("Phone")
                .setAge(25)
                .setGender("Gender")
                .build();
        System.out.println(person);
    }
}
