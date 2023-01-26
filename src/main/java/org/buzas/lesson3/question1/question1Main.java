package org.buzas.lesson3.question1;

public class question1Main {

    public static void main(String[] args) {
        PingPongPrinter printer = new PingPongPrinter();
        Thread threadPing = new Thread(printer::printPing);
        Thread threadPong = new Thread(printer::printPong);
        threadPing.start();
        threadPong.start();
    }
}
