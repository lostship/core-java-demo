package com.example.corejava;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Test {

    public static void main(String[] args) throws IOException {
        System.out.println(Class.class.getClassLoader());
        System.out.println(Thread.currentThread().getContextClassLoader());
    }

    private void test() {
        String str1 = "12";
        String str2 = "2" + (char) 19;
        // System.out.println(str1.hashCode());
        // System.out.println(str2.hashCode());

        int h;
        h = str2.hashCode();
        System.out.println(h);
        System.out.println(h >>> 16);
        System.out.println(h ^ (h >>> 16));

        Map<String, Integer> map = new HashMap<>();
        map.put(str1, 1);
        map.put(str2, 2);
        System.out.println(map.get(str2));

        int factor = 4;
        int buckets = 1 << (factor - 1) - 1; // 16
        int mask = buckets - 1; // 15
        System.out.println(buckets);

        int[] arr = { 1, 2, 3, 4 };
        System.out.println(Objects.hash(arr));
        System.out.println(Arrays.hashCode(arr));
    }

}
