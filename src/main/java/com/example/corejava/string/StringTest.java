package com.example.corejava.string;

public class StringTest {

    public static void main(String[] args) {
        // stringPoolTest();
        internTest();
    }

    public static void stringPoolTest() {
        String a = "12";

        String b = "" + 12;

        int n = 12;
        String c = "" + n;

        final int m = 12;
        String d = "" + m;

        String e = Integer.toString(12);

        String f = new String("12");

        String g0 = "1";
        String g1 = "2";
        String g = g0 + g1;

        final String h0 = "1";
        final String h1 = "2";
        String h = h0 + h1;

        System.out.println(a == b); // true
        System.out.println(a == c); // false
        System.out.println(a == d); // true
        System.out.println(a == e); // false
        System.out.println(a == f); // false
        System.out.println(a == g); // false
        System.out.println(a == h); // true
    }

    public static void internTest() {
        // 调用字符串对象的intern()方法
        // 如果常量池中没有该字面量，就会把该字符串对象放到常量池中
        // （StringTable，该对象地址不变，只是加入到常量池HashTable中，即成为拘留字符串），再返回该对象。
        // 而如果常量池中已经有了该字面量，那么就直接返回常量池中的字面量对象。

        String a1 = new String("11"); // 这里已经有字面量11，常量池中已经存在该字面量常量
        String a2 = a1.intern(); // 调用intern()方法，s1不会转换成拘留字符串，而s2指向拘留字符串
        System.out.println(a1 == a2); // false

        System.out.println("======================");
        Integer bi = 1;
        String b1 = bi.toString();
        String b2 = "1"; // 这里先有字面量1，所有常量池中已经有1了
        System.out.println(b1 == b2); // false
        String b3 = b1.intern(); // 在调用intern()方法，si1也还在堆中
        System.out.println(b1 == b3); // false

        System.out.println("======================");
        Integer ci = 2;
        String c1 = ci.toString();
        String c3 = c1.intern(); // 这里先调用intern()方法，sj1指向拘留字符串
        System.out.println(c1 == c3); // true
        String c2 = "2"; // 这里后出现字面量2，sj2指向常量池中已经存在的拘留字符串
        System.out.println(c1 == c2); // true

        System.out.println("======================");
        String d1 = "d1";
        String d2 = "d2";
        String d3 = d1 + d2;
        String d4 = d1 + d2;
        System.out.println(System.identityHashCode(d3));
        System.out.println(System.identityHashCode(d4));

        System.out.println(d3 == d4); // false
        String d5 = d3.intern();
        String d6 = "d1d2";
        System.out.println(System.identityHashCode(d3));
        System.out.println(System.identityHashCode(d4));
        System.out.println(System.identityHashCode(d5));
        System.out.println(System.identityHashCode(d6));
        System.out.println(d3 == d5); // true
        System.out.println(d3 == d6); // true
        System.out.println(d4 == d5); // false

        System.out.println("======================");
        String e1 = "e1";
        String e2 = "e2";
        String e3 = e1 + e2;
        String e4 = e1 + e2;
        System.out.println(System.identityHashCode(e3));
        System.out.println(System.identityHashCode(e4));

        System.out.println(e3 == e4); // false
        String e6 = "e1e2";
        String e5 = e3.intern();
        System.out.println(System.identityHashCode(e3));
        System.out.println(System.identityHashCode(e4));
        System.out.println(System.identityHashCode(e5));
        System.out.println(System.identityHashCode(e6));
        System.out.println(e3 == e5); // false
        System.out.println(e3 == e6); // false
        System.out.println(e4 == e5); // false

        System.out.println("======================");
        String f1 = "f1";
        String f2 = "f2";
        final String f3 = f1 + f2;
        String f4 = "f1f2";
        System.out.println(f3 == f4); // false

        System.out.println("======================");
        final String g1 = "g1";
        final String g2 = "g2";
        final String g3 = g1 + g2;
        String g4 = g1 + g2;
        String g5 = "g1g2";
        System.out.println(g3 == g5); // true
        System.out.println(g4 == g5); // true
    }

}
