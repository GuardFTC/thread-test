package com.ftc.threadtest;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author: 冯铁城 [17615007230@163.com]
 * @date: 2022-11-03 18:06:04
 * @describe: 线程池测试
 */
public class Executor {
//
//    @Test
//    void testCachedThreadPool() {
//
//        //1.创建一个缓存线程池
//        ExecutorService executor = Executors.newCachedThreadPool();
//
//        //2.循环执行10个任务
//        for (int i = 0; i < 10; i++) {
//            executor.execute(() -> System.out.println(
//                    Thread.currentThread().getName() + " is running")
//            );
//        }
//    }
//
//    @Test
//    void testFixedThreadPool() {
//
//        //1.创建一个Fixed线程池
//        ExecutorService executor = Executors.newFixedThreadPool(2);
//
//        //2.循环执行10个任务
//        for (int i = 0; i < 10; i++) {
//            executor.execute(() -> System.out.println(
//                    Thread.currentThread().getName() + " is running")
//            );
//        }
//    }
//
//    @Test
//    void testSingleThreadPool() {
//
//        //1.创建一个单例线程池
//        ExecutorService executor = Executors.newSingleThreadExecutor();
//
//        //2.循环执行10个任务
//        for (int i = 0; i < 10; i++) {
//            executor.execute(() -> System.out.println(
//                    Thread.currentThread().getName() + " is running")
//            );
//        }
//    }
//
//    @Test
//    @SneakyThrows(InterruptedException.class)
//    void testScheduledThreadPool() {
//
//        //1.创建一个定时线程池
//        ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);
//
//        //2.循环执行10个任务
//        for (int i = 1; i <= 5; i++) {
//
//            //3.定时执行任务
//            executor.schedule(() -> System.out.println(
//                    Thread.currentThread().getName() + " is running.Date is " + DateUtil.now()
//            ), i, TimeUnit.SECONDS);
//        }
//
//        //3.睡一会，确保线程池任务执行完成
//        TimeUnit.SECONDS.sleep(5);
//    }

    @Test
    void testCustomThreadPool() {

        //1.创建线程工厂,定义线程名称
        ThreadFactory threadFactory = new ThreadFactoryBuilder()
                .setNameFormat("custom-pool-%d")
                .build();

        //2.创建线程池
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                3,
                6,
                60,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(10),
                threadFactory,
                new ThreadPoolExecutor.AbortPolicy()
        );

        //3.执行任务
        for (int i = 0; i < 10; i++) {
            executor.execute(() -> System.out.println(
                    Thread.currentThread().getName() + " is running")
            );
        }

        //4.结束线程池
        if (executor.isShutdown()) {
            executor.shutdown();
        }
    }
}
