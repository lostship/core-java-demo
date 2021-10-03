package com.example.corejava.generic;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DateInterval extends Pair<LocalDate> implements Comparable<Integer> {

    public void run(LocalDate second) {
        System.out.println("child");
    }

    public LocalDate get() {
        return null;
    }

    // 除非父类中泛型变量T限定范围，不会擦除成Object
    // public void run(Object value) {
    // System.out.println("cihld original object");
    // }

    public int compareTo(Integer other) {
        return 1;
    }

    public static void main(String[] args) {
        Pair<LocalDate> pair = new DateInterval();
        pair.run(null);

        List<? super Exception> l1 = new ArrayList<>();
        l1.add(new RuntimeException());
        Object e = l1.get(0);

        DateInterval[] arr = new DateInterval[] {
                new DateInterval(),
                new DateInterval()
        };
        Arrays.sort(arr);
    }

}
