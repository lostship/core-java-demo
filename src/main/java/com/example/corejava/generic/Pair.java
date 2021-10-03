package com.example.corejava.generic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Predicate;

public class Pair<T> {

    // private T[] arr = new T[10];

    public void run(T value) {
        System.out.println("parent");
    }

    public T get() {
        return null;
    }

    // 除非T限定范围，不会擦除成Object
    // public void run(Object value) {
    // System.out.println("parent original object");
    // }
    // public boolean equals(T other) {
    // return false;
    // }

    public static void print1(ArrayList<? extends Exception> list) {
        Exception e = list.get(0);
        list.add(null);
        // list.add(new Exception()); // error
    }

    public static void print2(ArrayList<? super Exception> list) {
        Object e = list.get(0);
        list.add(null);
        list.add(new Exception());
        list.add(new IOException());
    }

    public static void print3(ArrayList<Object> list) {
        Object e = list.get(0);
        list.add(null);
        list.add(new Object());
        list.add(new Exception());
    }

    public static void print4(ArrayList<?> list) {
        Object e = list.get(0);
        list.add(null);
        // list.add(new Object()); // error
    }

    public static void main(String[] args) {
        print1(new ArrayList<Exception>());
        print1(new ArrayList<IOException>());

        print2(new ArrayList<Exception>());
        print2(new ArrayList<Throwable>());

        Predicate<Pair> p2 = obj -> obj.hashCode() % 2 != 0;
        Predicate<? extends Pair> p1 = p2;
        Predicate<?> p0 = p1;
    }

    public static void swap1(ArrayList<?>[] list) {
        swap(list[0]);
    }

    public static <T> void swap(ArrayList<T> list) {

    }

    public static void removeIf(Predicate<Pair> p) {

    }

}
