package com.example.corejava.thread;

import java.util.stream.Stream;

public class ThreadStart {

    private final static byte[] lock = new byte[0];
    private final static byte[] lock2 = new byte[0];

    public static void main(String[] args) throws InterruptedException {
        test2();
    }

    public static void test() throws InterruptedException {
        Stream.of("t1", "t2").forEach(tname -> new Thread(() -> testWait(), tname).start());
    }

    private static void testWait() {
        synchronized (lock) {
            try {
                String tname = Thread.currentThread().getName();
                System.out.println(tname + "开始执行");
                System.out.println(tname + "等待");
                lock.wait(10000);
                lock.notifyAll();
                System.out.println(tname + "唤醒");
                System.out.println(tname + "结束");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void test1() throws InterruptedException {
        Stream.of("t1", "t2").forEach(tname -> new Thread(() -> testWait1(), tname).start());

        Thread.sleep(1000);
        new Thread(() -> {
            synchronized (lock) {
                System.out.println("t3开始运行");
                lock.notify();
            }
        }, "t3").start();
    }

    private static void testWait1() {
        synchronized (lock) {
            try {
                String tname = Thread.currentThread().getName();
                System.out.println(tname + "开始执行");
                System.out.println(tname + "等待");
                lock.wait(10000);
                System.out.println(tname + "唤醒");
                System.out.println(tname + "结束");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 死锁
     */
    public static void test2() throws InterruptedException {
        Stream.of("t1", "t2").forEach(tname -> new Thread(() -> testWait2(), tname).start());
    }

    private static void testWait2() {
        String tname = Thread.currentThread().getName();

        synchronized (lock) {
            System.out.println(tname + "获得lock");
            synchronized (lock2) {
                System.out.println(tname + "获得lock2");
                try {
                    System.out.println(tname + "开始执行");
                    System.out.println(tname + "等待");
                    lock.wait(10000);
                    // lock.notify();
                    System.out.println(tname + "唤醒");
                    System.out.println(tname + "结束");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 死锁
     */
    public static void test3() throws InterruptedException {
        Stream.of("t1", "t2").forEach(tname -> new Thread(() -> testWait3(), tname).start());
    }

    private static void testWait3() {
        String tname = Thread.currentThread().getName();

        synchronized (lock2) {
            System.out.println(tname + "获得lock2");
            synchronized (lock) {
                System.out.println(tname + "获得lock");
                try {
                    System.out.println(tname + "开始执行");
                    System.out.println(tname + "等待");
                    lock.wait(10000);
                    lock2.wait(10000);
                    // lock.notify();
                    System.out.println(tname + "唤醒");
                    System.out.println(tname + "结束");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
