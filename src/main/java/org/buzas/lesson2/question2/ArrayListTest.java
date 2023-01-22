package org.buzas.lesson2.question2;

import java.util.ArrayList;
import java.util.List;

public class ArrayListTest {
    public static void main(String[] args) {
        MyArrayList<String> strings = new MyArrayList<>();
//        List<String> strings = new ArrayList<>();

        strings.add("first");
        strings.add("second");
        strings.add("third");
        System.out.println(strings);

        strings.remove(1);
        System.out.println(strings);

        List<String> str = new ArrayList<>();
        str.add("fourth");
        str.add("fifth");
        str.add("sixth");
        strings.addAll(str);
        System.out.println(strings);

        strings.set(3, "test");
        System.out.println(strings);

        System.out.println(strings.get(2));

        strings.clear();
        System.out.println(strings);

        System.out.println(strings.isEmpty());
    }
}
