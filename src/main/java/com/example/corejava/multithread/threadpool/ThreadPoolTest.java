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
 * 
 * <p>
 * <code>
 * 线程池运行机制：
 * 
 * 1.调用ThreadPoolExecutor.execute(Runnable firstTask)方法提交任务。
 * 2.如果workerCount < corePoolSize，
 *        则直接创建worker执行任务。
 *    否则如果workerQueue可容纳新任务，
 *        则将任务放入workQueue。
 *    否则尝试直接创建worker执行任务，如果失败则执行RejectedExecutionHandler策略。
 * 3.调用ThreadPoolExecutor.addWorker(firstTask)方法。
 * 4.创建Worker implements Runnable的实例，
 *    调用Worker(first)构造方法，其中调用getThreadFactory().newThread(this)，
 *    将Worker实例自身作为该Worker关联的Thread实例的Runnable实参，
 *    因此自会后执行Worker.thread.run()方法实际是执行Worker.run()方法。
 * 5.将Worker实例加入workers集合。
 * 6.调用Worker.thread.start()方法，即调用Worker.run()方法。
 * 7.调用ThreadPoolExecutor.runWorker(worker)方法。
 * 8.执行firstTask。
 * 9.firstTask完成后，循环调用Worker.getTask()方法，从workQueue任务队列中获取待执行的任务，
 *    如果当前线程数超过corePoolSize，或者开启了allowCoreThreadTimeOut，
 *        使用workQueue.poll(keepAliveTime, TimeUnit.NANOSECONDS)获取任务，
 *        这样如果未获取到任务，则该Worker空闲时间超过keepAliveTime，结束获取任务循环，该Worker被回收。
 *    否则使用workQueue.take()，持续等待直到获取到任务，这样不会结束获取任务循环，Worker不会被回收。
 * 10.如果获取到了待执行任务，Worker继续存活没有被回收，就不断重复执行和获取任务这一循环过程。
 * </code>
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
