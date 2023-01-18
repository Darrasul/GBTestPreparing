package org.buzas.lesson1.question2;

class Lorry extends Car, Moveable, Stopable {
    public void move(){
        System.out.println("Car is moving");
    }

    public void stop(){
        System.out.println("Car is stop");
    }
}

