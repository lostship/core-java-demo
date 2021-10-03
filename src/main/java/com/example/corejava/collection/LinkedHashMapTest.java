package com.example.corejava.collection;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

public class LinkedHashMapTest {

    public static void main(String[] args) {
        System.out.println(System.identityHashCode("123"));
        System.out.println(System.identityHashCode(new String("123")));

        LinkedHashMap<Integer, Integer> map = new LinkedHashMap<Integer, Integer>(5, 0.75f, true) {

            private static final long serialVersionUID = 1L;

            private static final int MAX_ENTRIES = 5;

            @Override
            protected boolean removeEldestEntry(Entry<Integer, Integer> eldest) {
                return this.size() > MAX_ENTRIES;
            }

        };

        map.put(1, 1);
        map.put(2, 2);
        map.put(3, 3);
        map.put(4, 4);
        map.put(5, 5);
        System.out.println(map);

        map.get(1);
        System.out.println(map);

        map.put(6, 6);
        System.out.println(map);
    }

}
