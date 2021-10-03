package com.example.corejava.multithread;

import java.util.Random;

public class Arrays {

    public static void bubbleSort(int[] arr) {
        boolean flag;

        for (int i = 0; i < arr.length - 1; i++) {
            flag = false;

            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1);

                    if (!flag) {
                        flag = true;
                    }
                }
            }

            if (!flag) {
                break;
            }
        }
    }

    public static void shuffle(int[] arr) {
        Random random = new Random();
        for (int i = arr.length - 1, k; i > 0; i--) {
            k = random.nextInt(i);
            swap(arr, k, i);
        }
    }

    public static int[] shuffleOutside(int[] arr) {
        int[] res = java.util.Arrays.copyOf(arr, arr.length);
        Random random = new Random();
        for (int i = 0, k; i < arr.length; i++) {
            k = random.nextInt(i);
            res[i] = res[k];
            res[k] = arr[i];
        }
        return res;
    }

    public static void swap(int[] arr, int i, int j) {
        if (i != j) {
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }

    public static void print(int[] arr) {
        System.out.println(java.util.Arrays.toString(arr));
    }

}
