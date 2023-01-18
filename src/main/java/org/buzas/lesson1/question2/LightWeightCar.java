package org.buzas.lesson1.question2;

public class LightWeightCar extends Car implements Moveable{
    @Override
    public void open() {
        System.out.println("Car is open");
    }

    @Override
    public void move() {
        System.out.println("Car is moving");
    }

}
