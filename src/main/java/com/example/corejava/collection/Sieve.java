package com.example.corejava.collection;

import java.util.BitSet;

public class Sieve {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        int count = countSieve(2000000);
        long end = System.currentTimeMillis();
        System.out.println(count);
        System.out.println(end - start);
    }

    /**
     * Erathostenes benchmark
     * 
     * @param n
     * @return
     */
    public static int countSieve(int n) {
        BitSet b = new BitSet(n + 1);
        b.set(2, n, true);

        int count = 0;
        int i = 2;

        while (i * i <= n) {
            if (b.get(i)) {
                count++;
                int k = i * i;
                while (k <= n) {
                    b.clear(k);
                    k += i;
                }
            }
            i++;
        }

        while (i <= n) {
            if (b.get(i)) {
                count++;
            }
            i++;
        }

        return count;
    }

}
