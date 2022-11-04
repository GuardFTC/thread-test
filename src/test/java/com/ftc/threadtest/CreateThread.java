package com.ftc.threadtest;

import com.ftc.threadtest.create.CustomCallable;
import com.ftc.threadtest.create.CustomRunnable;
import com.ftc.threadtest.create.CustomThread;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author: 冯铁城 [17615007230@163.com]
 * @date: 2022-11-02 18:29:34
 * @describe: 创建线程单元测试
 */
class CreateThread {

    @Test
    void testCreate() {

        //1.创建Thread/Runnable线程，线程处于NEW状态
        CustomThread customThread = new CustomThread();
        Thread customRunnable = new Thread(new CustomRunnable());

        //2.开始Thread/Runnable线程，线程处于RUNNABLE状态
        customThread.start();
        customRunnable.start();

        //3.创建Callable线程，线程处于NEW状态
        FutureTask<String> futureTask = new FutureTask<>(new CustomCallable());
        Thread customCallable = new Thread(futureTask);

        //4.开始Callable线程，线程处于RUNNABLE状态
        customCallable.start();
        try {
            String result = futureTask.get();
            System.out.println(Thread.currentThread().getName() + "->" + result);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testCreateShorthand() {

        //1.创建Thread/Runnable线程，线程处于NEW状态
        Thread customThread = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "->CustomThread执行成功");
        });

        //2.开始Thread/Runnable线程，线程处于RUNNABLE状态
        customThread.start();

        //3.创建Callable线程，线程处于NEW状态
        Callable<String> callable = () -> {
            System.out.println(Thread.currentThread().getName() + "->CustomCallable执行成功");
            return Thread.currentThread().getName() + "->CustomCallable执行成功";
        };
        FutureTask<String> futureTask = new FutureTask<>(callable);
        Thread customCallable = new Thread(futureTask);

        //4.开始Callable线程，线程处于RUNNABLE状态
        customCallable.start();
        try {
            String result = futureTask.get();
            System.out.println(Thread.currentThread().getName() + "->" + result);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
