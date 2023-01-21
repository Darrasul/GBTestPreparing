package org.buzas.lesson1.question2;

public class Lorry extends Car implements Moveable, Stopable {
    public void move(){
        System.out.println("Car is moving");
    }

    public void stop(){
        System.out.println("Car is stop");
    }

    @Override
    public void open() {
//        System.out.println("This is missing method");
    }
}

