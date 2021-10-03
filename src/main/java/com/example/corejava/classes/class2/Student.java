package com.example.corejava.classes.class2;

import com.example.corejava.classes.class1.Employee;

public class Student extends Employee {

    public Student(String name, double salary) {
        super(name, salary);
    }

    public static void main(String[] args) throws Exception {
        Student e = new Student("Tom", 1000);
        e.protect();
    }

}
