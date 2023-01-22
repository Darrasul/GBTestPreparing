package org.buzas.lesson2.question1;

import java.util.LinkedList;

public class LinkedListTest {
    public static void main(String[] args) {
//        LinkedList<Integer> numbers = new LinkedList<>();
        MyLinkedList<Integer> numbers = new MyLinkedList<>();

        numbers.add(1);
        numbers.add(2);
        numbers.add(3);
        numbers.add(4);
        System.out.println(numbers);
        System.out.println(numbers.get(2));
        System.out.println(numbers.getFirst());
        System.out.println(numbers.getLast());

        numbers.remove(2);
        System.out.println(numbers);

        numbers.addFirst(5);
        System.out.println(numbers);
    }
}
