package com.ftc.threadtest.lock;

import cn.hutool.core.date.DateUtil;
import lombok.SneakyThrows;

import java.util.concurrent.TimeUnit;

/**
 * @author: 冯铁城 [17615007230@163.com]
 * @date: 2022-11-08 18:58:51
 * @describe: Synchronized关键字测试类
 */
public class SynchronizedTestEntity implements Runnable {

    @Override
    public void run() {
        consoleInfoStatic();
    }

    @SneakyThrows(InterruptedException.class)
    public synchronized void consoleInfo() {

        //1.线程睡50ms，从而体现出线程的顺序执行
        TimeUnit.SECONDS.sleep(1);

        //2.获取当前时间
        String now = DateUtil.now();

        //3.控制台打印线程信息
        System.out.println(Thread.currentThread().getName() + " is running on " + now);
    }

    @SneakyThrows(InterruptedException.class)
    public static synchronized void consoleInfoStatic() {

        //1.线程睡50ms，从而体现出线程的顺序执行
        TimeUnit.SECONDS.sleep(1);

        //2.获取当前时间
        String now = DateUtil.now();

        //3.控制台打印线程信息
        System.out.println(Thread.currentThread().getName() + " is running on " + now);
    }

    @SneakyThrows(InterruptedException.class)
    public void consoleInfoCodeBlockForObject() {
        synchronized (this) {

            //1.线程睡50ms，从而体现出线程的顺序执行
            TimeUnit.SECONDS.sleep(1);

            //2.获取当前时间
            String now = DateUtil.now();

            //3.控制台打印线程信息
            System.out.println(Thread.currentThread().getName() + " is running on " + now);
        }
    }

    @SneakyThrows(InterruptedException.class)
    public void consoleInfoCodeBlockForClass() {
        synchronized (SynchronizedTestEntity.class) {

            //1.线程睡50ms，从而体现出线程的顺序执行
            TimeUnit.SECONDS.sleep(1);

            //2.获取当前时间
            String now = DateUtil.now();

            //3.控制台打印线程信息
            System.out.println(Thread.currentThread().getName() + " is running on " + now);
        }
    }
}
