package com.example.corejava.multithread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ThreadTest {

    public static void main(String[] args) {
        int size = 10;
        int[] arr = new int[size];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i + 1;
        }

        int loop = 10000;

        time(() -> {
            for (int i = 0; i < loop; i++) {
                Arrays.shuffle(arr);
                // Arrays.bubbleSort(arr);
                // quickSort(arr, 0, size - 1);
                // quickSort2(arr, 0, size - 1);
                myQuickSort(arr, 0, size - 1);
                // asyncQuickSort(arr, 0, size - 1);
                // Arrays.print(arr);
            }
        });
    }

    public static void quickSort(final int[] arr, final int start, final int end) {
        if (start >= end) {
            return;
        }

        final int k = arr[end];
        int i = start;
        int j = end - 1;

        while (i < j) {
            while (arr[i] <= k && i < j) {
                i++;
            }
            while (arr[j] >= k && i < j) {
                j--;
            }
            Arrays.swap(arr, i, j);
        }

        // 最终回达到i == j的状态退出循环
        // 因为arr[end]没有参与过位置交换，因此退出循环后需要比较arr[i]和arr[end]，确定arr[end]的位置
        //
        // 1. 如果k是最小值，最终i=j == start，arr[i] > arr[end]，交换arr[i]和arr[end]，之后对[i+1, end]排序即可
        //
        // 2. 如果k是最大值，最终i=j == end-1，arr[i] < arr[end]，不做交换，之后对[start, end-1]排序即可
        //
        // 3. 如果k是中间值，最终start <= i=j <= end-1
        // 如果arr[i] > arr[end]，交换arr[i]和arr[end]，之后对[start, i-1]和[i+1, end]排序即可
        // 如果arr[i] == arr[end]，不做交换，之后对[start, i-1]和[i+1, end]排序即可
        // 不可能出现arr[i] < arr[end]
        //
        //
        // 综上所述，最终：
        // arr[i] > arr[end]，交换arr[i]和arr[end]，之后对[start, i-1]和[i+1, end]排序即可
        // arr[i] == arr[end]，之后对[start, i-1]和[i+1, end]排序即可
        // arr[i] < arr[end]，赋值i = end，之后对[start, i-1]和[i+1, end]排序即可

        if (arr[i] > arr[end]) {
            Arrays.swap(arr, i, end);
        } else if (arr[i] < arr[end]) {
            i = end;
        }

        quickSort(arr, start, i - 1);
        quickSort(arr, i + 1, end);
    }

    public static void quickSort2(final int[] arr, final int start, final int end) {
        if (start >= end) {
            return;
        }

        int L = start;
        int R = end;
        int temp = arr[L];
        while (L != R) {
            while (R > L) {
                if (arr[R] < temp) {
                    arr[L] = arr[R];
                    break;
                }
                R--;
            }
            while (L < R) {
                if (arr[L] > temp) {
                    arr[R] = arr[L];
                    break;
                }
                L++;
            }
        }
        arr[L] = temp;
        quickSort2(arr, start, L - 1);
        quickSort2(arr, L + 1, end);
    }

    public static void myQuickSort(final int[] arr, final int start, final int end) {
        if (start >= end) {
            return;
        }

        int k = arr[end];
        int i = start;
        int j = end;

        while (i < j) {
            while (arr[i] <= k && i < end) {
                i++;
            }
            while (arr[j] > k && j > 0) {
                j--;
            }

            if (i < j) {
                Arrays.swap(arr, i, j);
            }
        }

        // 如果k是最小值，最终i=start + 1，j=start
        // 如果k是最大值，最终i=end，j=end，如果不做j--，会造成无限循环对[start,end]排序，出现StackOverflowError
        // 如果k是中间值，最终start < j < i <= end
        if (j == end) {
            j--;
        }

        myQuickSort(arr, start, j);
        myQuickSort(arr, i, end);
    }

    public static class QuickSort extends RecursiveAction {

        private int[] arr;
        private int start;
        private int end;

        public QuickSort(int[] arr, int start, int end) {
            this.arr = arr;
            this.start = start;
            this.end = end;
        }

        @Override
        protected void compute() {
            if (start >= end) {
                return;
            }

            final int k = arr[end];
            int i = start;
            int j = end - 1;

            while (i < j) {
                while (arr[i] <= k && i < j) {
                    i++;
                }
                while (arr[j] >= k && i < j) {
                    j--;
                }
                Arrays.swap(arr, i, j);
            }

            // 最终回达到i == j的状态退出循环
            // 因为arr[end]没有参与过位置交换，因此退出循环后需要比较arr[i]和arr[end]，确定arr[end]的位置
            //
            // 1. 如果k是最小值，最终i=j == start，arr[i] > arr[end]，交换arr[i]和arr[end]，之后对[i+1, end]排序即可
            //
            // 2. 如果k是最大值，最终i=j == end-1，arr[i] < arr[end]，不做交换，之后对[start, end-1]排序即可
            //
            // 3. 如果k是中间值，最终start <= i=j <= end-1
            // 如果arr[i] > arr[end]，交换arr[i]和arr[end]，之后对[start, i-1]和[i+1, end]排序即可
            // 如果arr[i] == arr[end]，不做交换，之后对[start, i-1]和[i+1, end]排序即可
            // 不可能出现arr[i] < arr[end]
            //
            //
            // 综上所述，最终：
            // arr[i] > arr[end]，交换arr[i]和arr[end]，之后对[start, i-1]和[i+1, end]排序即可
            // arr[i] == arr[end]，之后对[start, i-1]和[i+1, end]排序即可
            // arr[i] < arr[end]，赋值i = end，之后对[start, i-1]和[i+1, end]排序即可

            if (arr[i] > arr[end]) {
                Arrays.swap(arr, i, end);
            } else if (arr[i] < arr[end]) {
                i = end;
            }

            QuickSort sortLeft = new QuickSort(arr, start, i - 1);
            QuickSort sortRight = new QuickSort(arr, i + 1, end);
            invokeAll(sortLeft, sortRight);
            // sortLeft.fork();
            // sortRight.fork();
            // sortLeft.join();
            // sortRight.join();
        }

    }

    public static void asyncQuickSort(final int[] arr, final int start, final int end) {
        ForkJoinPool pool = new ForkJoinPool();
        QuickSort sorter = new QuickSort(arr, start, end);
        pool.invoke(sorter);
        sorter.join();
    }

    public static void time(Runnable runnable) {
        long start = System.currentTimeMillis();
        runnable.run();
        long end = System.currentTimeMillis();
        System.out.println("run time: " + (end - start));
    }

    public void barrierTest() {
        int size = 2;
        CyclicBarrier barrier = new CyclicBarrier(size, () -> System.out.println("all arrive"));
        for (int i = 0; i < size; i++) {
            new Thread(() -> {
                try {
                    Thread.sleep((long) (Math.random() * 5000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(Thread.currentThread().getName() + " wait");
                try {
                    barrier.await();
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                } catch (BrokenBarrierException e1) {
                    e1.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " done");
            }).start();
        }
        System.out.println("no block");
    }

    public void executorComplationServiceTest() throws InterruptedException, ExecutionException {
        List<Callable<String>> tasks = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            int k = i;
            tasks.add(() -> {
                Thread.sleep(2000);
                System.out.println("do " + k);
                return "res " + k;
            });
        }

        System.out.println("start");
        ExecutorService executor = Executors.newCachedThreadPool();
        ExecutorCompletionService<String> service = new ExecutorCompletionService<>(executor);
        for (Callable<String> task : tasks) {
            service.submit(task);
        }
        System.out.println("get result");

        for (int i = 0; i < tasks.size(); i++) {
            System.out.println(service.take().get());
        }
    }

}
