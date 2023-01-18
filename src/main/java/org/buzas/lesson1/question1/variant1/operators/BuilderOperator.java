package org.buzas.lesson1.question1.variant1.operators;

import lombok.Setter;
import org.buzas.lesson1.question1.variant1.builders.PersonBuilder;
import org.buzas.lesson1.question1.variant1.units.Person;

@Setter
public class BuilderOperator {
    private PersonBuilder builder;

//    Здесь можно было обойтись без единной строчки к записи, но Person подразумевает человека с конкретными уникальными данными.
//    Делай мы builder к примеру камней, у нас бы сейчас был RockBuilder без единного параметра в абстрактных методах builtSmtng() и все эти данные базово
//    задавались для каждого типа камня. К примеру в условном SiliciumRockBuilder метод builtDensity() задавал бы "2,33 г/см^3" и т.д. Здесь много данных и вынести
//    вышло лишь страну. Из-за неудачной задачи не вышло достаточно явно показать разницу со вторым видом builder'ов, которые подходят лишь для одного класса
    public Person buildPerson(String firstName, String lastName, String middleName,
                              String address, String phone, int age, String gender) {
        builder.createPerson();

        builder.builtFirstName(firstName);
        builder.builtLastName(lastName);
        builder.builtMiddleName(middleName);
        builder.builtCountry();
        builder.builtAddress(address);
        builder.builtPhone(phone);
        builder.builtAge(age);
        builder.builtGender(gender);

        return builder.getPerson();
    }
}
