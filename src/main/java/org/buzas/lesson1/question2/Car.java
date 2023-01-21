package org.buzas.lesson1.question2;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
abstract class Car {
    private Engine engine;
    private String color;
    @Setter(AccessLevel.PROTECTED)
    private String name;

    protected void start() {
        System.out.println("Car starting");
    }

    abstract public void open();
}
