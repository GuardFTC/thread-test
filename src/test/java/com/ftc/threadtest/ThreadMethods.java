package com.ftc.threadtest;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author: 冯铁城 [17615007230@163.com]
 * @date: 2022-11-02 18:29:34
 * @describe: Wait和Sleep方法
 */
public class ThreadMethods {
//
//    @Test
//    @SneakyThrows
//    void testWaitAndSleep() {
//
//        //1.创建人类对象
//        People people = new People();
//
//        //2.创建3个线程
//        Thread thread1 = new Thread(new Receiver(people, "玩手机"), Receiver.PLAY_PHONE);
//        Thread thread2 = new Thread(new Receiver(people, "刷牙"), Receiver.BRUSH_TEETH);
//        Thread thread3 = new Thread(new Receiver(people, "睡觉"), Receiver.SLEEP);
//
//        //3.开启线程
//        thread1.start();
//        thread2.start();
//        thread3.start();
//
//        //4.睡1s，确保其他线程能够运行完毕
//        TimeUnit.SECONDS.sleep(3);
//    }
//
//    @Test
//    void testInterrupted() {
//
//        //1.创建自定义线程
//        Thread customThread = new Thread(() -> {
//            System.out.println(Thread.currentThread().getName() + " is running");
//        });
//
//        //2.运行自定义线程
//        customThread.start();
//
//        //3.设置自定义线程中断标识为true
//        customThread.interrupt();
//
//        //4.校验自定义线程中断标识是否为true
//        Assert.isTrue(customThread.isInterrupted());
//
//        //5.判定当前线程中断标识是否为true
//        Thread.currentThread().interrupt();
//        Assert.isTrue(Thread.interrupted());
//        Assert.isFalse(Thread.currentThread().isInterrupted());
//    }
//
//    @Test
//    void testYield() {
//
//        //1.创建第一个线程
//        new Thread(() -> {
//            for (int i = 0; i < 5; i++) {
//                System.out.println(Thread.currentThread().getName() + "->" + i);
//
//                //2.i==2时，让出CPU，重新进入Ready状态
//                if (i == 2) {
//                    Thread.yield();
//                }
//            }
//        }).start();
//
//        //2.创建第二个线程
//        new Thread(() -> {
//            for (int i = 0; i < 5; i++) {
//                System.out.println(Thread.currentThread().getName() + "->" + i);
//            }
//        }).start();
//    }

    @Test
    @SneakyThrows(InterruptedException.class)
    void testJoin() {

        //1.创建自定义线程
        Thread customThread = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.out.println(Thread.currentThread().getName() + " run end");
        });

        //2.自定义线程运行
        customThread.start();

        //3.自定义线程join到主线程之前
        customThread.join();

        //4.主线程输出
        System.out.println("Main thread run end");
    }

    @Test
    void testDaemon() {
        Thread daemonThread = new Thread();
        daemonThread.start();
        daemonThread.setDaemon(true);
    }
}
