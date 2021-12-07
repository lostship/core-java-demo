package com.example.corejava.multithread.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <code>
 * ThreadPoolExecutor exec = new ThreadPoolExecutor(
 * int, int, long, TimeUnit, BlockingQueue<Runnable>, ThreadFactory, RejectedExecutionHandler);
 * </code>
 * 
 * <p>
 * <code>
 * ScheduledThreadPoolExecutor scheduled = new ScheduledThreadPoolExecutor(
 * int, ThreadFactory, RejectedExecutionHandler);
 * </code>
 * 
 * <p>
 * <strong>corePoolSize</strong> 核心线程数，线程池刚启动时为空，之后每次申请线程时即便有空闲线程，也会创建一个新线程，
 * 直到达到corePoolSize。核心线程即使空闲也一直保持存活，
 * 如果要允许核心线程超时关闭可以设置allowCoreThreadTimeOut为true
 * 
 * <p>
 * <strong>maximumPoolSize</strong> 最大线程数
 * 
 * <p>
 * <strong>keepAliveTime</strong> 超过核心线程数的多余线程，最大空闲时间
 * 
 * <p>
 * <strong>unit</strong> keepAliveTime的时间单位
 * 
 * <p>
 * <strong>workQueue</strong> 用于保存待执行任务的队列，这个队列只会保存execute方法提交的Runnable任务。
 * 提交任务时如果达到核心线程数最大值，且任务队列未满，会添加到任务队列。
 * 如果任务队列已满，且未达到最大线程数，则会创建多余线程
 * 
 * <p>
 * <strong>threadFactory</strong> executor创建新线程时使用的工厂，默认为Executors.defaultThreadFactory()
 * 
 * <p>
 * <strong>handler</strong> 由于达到最大线程数量和队列容量而阻塞执行时，要使用的处理程序，几种常用内部实现如下：
 * AbortPolicy（默认），丢弃任务，抛出运行时异常
 * CallerRunsPolicy，执行任务
 * DiscardPolicy，丢弃任务
 * DiscardOldestPolicy，从队列中踢出最老的任务
 * 
 * <p>
 * 参数的确定，根据每秒提交任务数、每个任务执行所需时间、可容忍最大等待时间等因素来确定。
 */
public class ThreadPoolTest {

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
