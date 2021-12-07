package com.example.corejava.multithread.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolTest {

    /**
     * ThreadPoolExecutor exec = new ThreadPoolExecutor(
     * int, int, long, TimeUnit, BlockingQueue<Runnable>, ThreadFactory, RejectedExecutionHandler);
     * 
     * ScheduledThreadPoolExecutor scheduled = new ScheduledThreadPoolExecutor(
     * int, ThreadFactory, RejectedExecutionHandler);
     * 
     * @param corePoolSize    核心线程数，线程池刚启动时为空，之后每次申请线程时即便有空闲线程，也会创建一个新线程，
     *                        直到达到corePoolSize。核心线程即使空闲也一直保持存活，
     *                        如果要允许核心线程超时关闭可以设置allowCoreThreadTimeOut为true
     * @param maximumPoolSize 最大线程数
     * @param keepAliveTime   超过核心线程数的多余线程，最大空闲时间
     * @param unit            keepAliveTime的时间单位
     * @param workQueue       用于保存待执行任务的队列，这个队列只会保存execute方法提交的Runnable任务
     * @param threadFactory   executor创建新线程时使用的工厂
     * @param handler         由于达到最大线程数量和队列容量而阻塞执行时，要使用的处理程序
     */

    // Executors.newCachedThreadPool();
    ExecutorService cached = new ThreadPoolExecutor(
            0, Integer.MAX_VALUE,
            60L, TimeUnit.SECONDS,
            new SynchronousQueue<Runnable>());

    // Executors.newFixedThreadPool(10);
    ExecutorService fixed = new ThreadPoolExecutor(
            10, 10,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>());

    // Executors.newSingleThreadExecutor();
    ExecutorService single = new ThreadPoolExecutor(
            1, 1,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>());

    // Executors.newScheduledThreadPool(10);
    ScheduledExecutorService scheduled = new ScheduledThreadPoolExecutor(10);

    // Executors.newSingleThreadScheduledExecutor();
    ScheduledExecutorService singleScheduled = new ScheduledThreadPoolExecutor(1);

}
