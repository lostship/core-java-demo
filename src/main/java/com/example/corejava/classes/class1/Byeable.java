package com.example.corejava.classes.class1;

import java.io.IOException;

public interface Byeable {

    default void bye(String name) throws IOException {
        System.out.println("byeable bye " + name);
    }

}
