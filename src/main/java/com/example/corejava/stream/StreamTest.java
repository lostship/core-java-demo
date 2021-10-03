package com.example.corejava.stream;

import java.util.stream.Stream;

public class StreamTest {

    public static void main(String[] args) {
        // Stream<Double> echos = Stream.generate(() -> new Random().random());
        // echos.limit(5).forEach(System.out::println);
        // echos.limit(5).forEach(System.out::println);

        Stream.iterate(1.0, s -> s * 2).peek(e -> {
            System.out.println(e);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }).limit(20).toArray();
    }

    public static class Random {
        public Random() {
            System.out.println("init");
        }

        public double random() {
            return Math.random();
        }
    }

}
