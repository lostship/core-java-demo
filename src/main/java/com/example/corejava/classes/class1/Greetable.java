package com.example.corejava.classes.class1;

public interface Greetable {

    default void hello(String name) {
        System.out.println("greetable hello " + name);
    }

    default void bye(String name) {
        System.out.println("greetable bye " + name);
    }

}
