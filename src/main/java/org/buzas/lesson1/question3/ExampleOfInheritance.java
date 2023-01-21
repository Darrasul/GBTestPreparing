package org.buzas.lesson1.question3;

public class ExampleOfInheritance {
    public static void main(String[] args) {
        Shape shape = new Shape();
        Shape round = new Round();
        Shape square = new Square();
        Shape triangle = new Triangle();
        System.out.println("Shape");
        shape.tellAboutShape();
        shape.tellThatIsShape();
        System.out.println("Round");
        round.tellAboutShape();
        round.tellThatIsShape();
        System.out.println("Square");
        square.tellAboutShape();
        square.tellThatIsShape();
        System.out.println("Triangle");
        triangle.tellAboutShape();
        triangle.tellThatIsShape();
    }
}
