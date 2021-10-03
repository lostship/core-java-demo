package com.example.corejava.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Random;

import com.example.corejava.classes.class1.Employee;
import com.example.corejava.classes.class1.Greetable;
import com.example.corejava.classes.class1.Manager;

public class ProxyTest {

    public static void main(String[] args) {
        test2();
    }

    private static void test1() {
        Employee target = new Manager("Tom", 1000, 500);

        InvocationHandler traceHandler = new TraceHandler(target);

        Class[] interfaces = new Class[] { Greetable.class };

        Object proxy = Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), interfaces, traceHandler);

        ((Greetable) proxy).hello("Jerry");
    }

    private static void test2() {
        Object[] arr = new Object[1000];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = Proxy.newProxyInstance(null,
                    new Class[] { Comparable.class },
                    new TraceHandler(Integer.valueOf(i + 1)));
        }

        Integer value = new Random().nextInt(arr.length) + 1;

        int index = Arrays.binarySearch(arr, value);

        if (index >= 0) {
            System.out.println(arr[index]);
        }
    }

}
