package com.example.corejava.generic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Predicate;

import com.example.corejava.classes.class1.Manager;

public class Pair<T> {
    private T first;
    private T second;

    // 不能实例化泛型变量。
    // 可以让客户端提供一个构造器表达式，比如method(Supplier<T> constr)，传递String::new。
    // 或者提供一个Class<T>使用反射实例化，比如method(Class<T> cl)，传递String.class。
    // private T t = new T();

    // 声明一个泛型数组是可以的，但是不能实例化一个泛型数组
    // （无限定通配符除外，比如List<?>[] arr = new List<?>[1]是可以的，因为在泛型的子类型规则中，无限定通配符参数类型是其它泛型的父类）。
    // 如果仅仅是一个私有域，可以声明为Object[]，并在使用元素时进行强制转换。
    // 如果方法要构造并返回T[]类型，可以让客户端提供一个构造器表达式，比如method(IntFunction<T[]> constr)，传递String[]::new。
    // 或者使用反射Array.newInstance。
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

    /**
     * 子类通配符
     */
    public static void print1(ArrayList<? extends Exception> list) {
        // 调用获取返回值的方法时，可以使用更宽松的类型接收
        if (!list.isEmpty()) {
            Exception e = list.get(0);
            Throwable e1 = list.get(0);
            Object e2 = list.get(0);
        }

        // 调用传递值的方法时，子类限定通配符对修改关闭，拒绝null以外的任何参数
        list.add(null);
        // list.add(new Exception()); // error
        // list.add(new IOException()); // error
        // list.add(new Throwable()); // error
        // list.add(new Object()); // error
        // list.add(new String()); // error
        // list.add(list.get(0)); // error
    }

    /**
     * 超类通配符
     */
    public static void print2(ArrayList<? super Exception> list) {
        // 调用获取返回值的方法时，只能使用Object类型接收
        if (!list.isEmpty()) {
            Object e = list.get(0);
        }

        // 调用传递值的方法时，接收泛型类或子类的参数，拒绝接收更宽松的类型或其它类型
        list.add(null);
        list.add(new Exception());
        list.add(new IOException());
        // list.add(new Throwable()); // error
        // list.add(new Object()); // error
        // list.add(new String()); // error
        // list.add(list.get(0)); // error
    }

    public static void print3(ArrayList<Object> list) {
        // 调用获取返回值的方法时，只能使用Object类型接收
        if (!list.isEmpty()) {
            Object e = list.get(0);
        }

        // 调用传递值的方法时，接收任何类型参数
        list.add(null);
        list.add(new Exception());
        list.add(new IOException());
        list.add(new Throwable());
        list.add(new Object());
        list.add(new String());
        if (!list.isEmpty()) {
            list.add(list.get(0));
        }
    }

    /**
     * 无限定通配符
     */
    public static void print4(ArrayList<?> list) {
        // 调用获取返回值的方法时，只能使用Object类型接收
        if (!list.isEmpty()) {
            Object e = list.get(0);
        }

        // 调用传递值的方法时，无限定通配符对修改关闭，拒绝null以外的任何参数
        list.add(null);
        // list.add(new Exception()); // error
        // list.add(new IOException()); // error
        // list.add(new Throwable()); // error
        // list.add(new Object()); // error
        // list.add(new String()); // error
        // list.add(list.get(0)); // error
    }

    public static void main(String[] args) {
        print1(new ArrayList<Exception>());
        print1(new ArrayList<IOException>());
        // print1(new ArrayList<Throwable>()); // error

        print2(new ArrayList<Exception>());
        print2(new ArrayList<Throwable>());
        // print2(new ArrayList<IOException>()); // error

        // subtyping 子类型规则
        Predicate<Pair> p1 = obj -> obj.hashCode() % 2 != 0;
        Predicate<Object> p2 = null;

        Predicate<? extends Pair> p3 = p1;

        Predicate<? super Pair> p4 = p1;
        p4 = p2;

        Predicate<?> p5 = p1;
        p5 = p2;
        p5 = p3;
        p5 = p4;

        Predicate p6 = p1;
        p6 = p2;
        p6 = p3;
        p6 = p4;
        p6 = p5;
    }

    /**
     * 无限定通配符功能上有很大局限性，但是在处理简单操作时非常有用，
     * 例如下边的方法声明明显比<T> boolean hasNull(Pair<T> p)可读性更强。
     * 如果要使用泛型，但不确定或者不关心实际的类型参数，就可以使用无限定通配符。
     */
    public static boolean hasNull(Pair<?> p) {
        return false;
    }

    /**
     * 通配符捕获
     */
    public static void minmaxPair(Manager[] a, Pair<? super Manager> result) {
        // TODO maxminPair(a, result); // 注意，这个方法的结果是max-min，与我们想要的结果相反，需要对结果进行位置交换

        // error 不可能这样做，因为不能用“?”作为一种类型
        // ? t = result.first;
        // result.first = result.second;
        // result.second = t;

        // 因此需要使用通配符捕获
        swapHelper(result);
    }

    /**
     * 通配符捕获
     */
    public static <T> void swapHelper(Pair<T> p) {
        T t = p.first;
        p.first = p.second;
        p.second = t;
    }

    public static void removeIf(Predicate<Pair> p) {

    }
}
