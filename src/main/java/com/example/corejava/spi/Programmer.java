package com.example.corejava.spi;

public class Programmer implements Worker {

    @Override
    public void work() {
        System.out.println("programming");
    }

}
