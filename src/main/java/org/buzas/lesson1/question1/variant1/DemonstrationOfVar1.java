package org.buzas.lesson1.question1.variant1;

import org.buzas.lesson1.question1.variant1.builders.AmericanPersonBuilder;
import org.buzas.lesson1.question1.variant1.builders.RussianPersonBuilder;
import org.buzas.lesson1.question1.variant1.operators.BuilderOperator;

public class DemonstrationOfVar1 {
    public static void main(String[] args) {
        BuilderOperator operator = new BuilderOperator();
        operator.setBuilder(new RussianPersonBuilder());

        System.out.println(operator.buildPerson("FirstName", "LastName", "MiddleName",
                "Address", "Phone", 25, "Gender") + "\n");

        operator.setBuilder(new AmericanPersonBuilder());
        System.out.println("\n" + operator.buildPerson("FirstName", "LastName", "MiddleName",
                "Address", "Phone", 25, "Gender"));
    }
}
