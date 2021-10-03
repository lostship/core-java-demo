package com.example.corejava.generic;

import com.example.corejava.classes.class1.Byeable;
import com.example.corejava.classes.class1.Greetable;
import com.example.corejava.classes.class1.Manager;
import com.example.corejava.classes.class1.Person;

public class GenericTest {

    public static void main(String[] args) {
        Manager m1 = new Manager("Tom", 1000, 500);
        Manager m2 = new Manager("Jerry", 2000, 800);
        System.out.println(min(m1, m2));
    }

    public static <T extends Person & Comparable<? super T> & Greetable & Byeable> T min(T... a) {
        if (a == null || a.length == 0) {
            return null;
        }
        if (a.length == 1) {
            return a[0];
        }

        T min = a[0];
        for (int i = 1; i < a.length; i++) {
            if (a[i].compareTo(min) < 0) {
                min = a[i];
            }
        }

        return min;
    }

}
