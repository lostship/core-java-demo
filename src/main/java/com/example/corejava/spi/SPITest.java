package com.example.corejava.spi;

import java.util.ServiceLoader;

public class SPITest {

    public static void main(String[] args) {
        ServiceLoader<Worker> loader = ServiceLoader.load(Worker.class);
        loader.forEach(Worker::work);
    }

}
