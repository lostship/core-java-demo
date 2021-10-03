package com.example.corejava.classes.class1;

public class ManagerTest {

    public static void main(String[] args) throws Exception {
        Employee e = new Employee("Jerry", 1000);
        Employee m = new Manager("Tom", 2000, 500);
        System.out.println(e.getClass());
        System.out.println(m.getClass());

        e.protect();

        System.out.println(e.toString());
        System.out.println(m.toString());

        Employee[] parr = new Manager[5];
        parr[0] = e; // ArrayStoreException
    }

}
