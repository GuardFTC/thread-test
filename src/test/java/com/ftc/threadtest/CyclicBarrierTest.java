package com.ftc.threadtest;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

/**
 * @author: 冯铁城 [17615007230@163.com]
 * @date: 2022-11-19 16:41:03
 * @describe: CyclicBarrier单元测试
 */
public class CyclicBarrierTest {

    @Test
    void testCyclicBarrier() {

        //1.创建计数器
        CountDownLatch countDownLatch = new CountDownLatch(3);

        //2.创建循环栅栏，阻塞等待
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3, () -> {
            System.out.println(Thread.currentThread().getName() + ":所有玩家已经进入游戏！");
        });

        //3.开始考试
        System.out.println(Thread.currentThread().getName() + ":等待玩家进入");

        //4.线程1进入游戏
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + ":玩家已进入");
            try {
                countDownLatch.countDown();
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName() + ":玩家开始游戏");
        }).start();

        //5.线程2开始考试
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + ":玩家已进入");
            try {
                countDownLatch.countDown();
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName() + ":玩家开始游戏");
        }).start();

        //6.线程3开始考试
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + ":玩家已进入");
            try {
                countDownLatch.countDown();
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName() + ":玩家开始游戏");
        }).start();

        //7.主线程等待玩家全部到齐
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        //8.输出
        System.out.println(Thread.currentThread().getName() + ":全部玩家已进入，开始游戏！！");
    }

    @Test
    @SneakyThrows(InterruptedException.class)
    void testReset() {

        //1.创建循环栅栏，阻塞等待
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3, () -> {
            System.out.println("开始点菜！");
        });

        //2.创建线程1开始等待
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " 已到达餐厅");
            try {
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                System.out.println(Thread.currentThread().getName() + " 饭局散了");
            }
        }).start();

        //3.创建线程2开始等待
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " 已到达餐厅");
            try {
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                System.out.println(Thread.currentThread().getName() + " 饭局散了");
            }
        }).start();

        //4.创建线程3进行reset操作
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " 抱歉了我去不了了");
            cyclicBarrier.reset();
        }).start();

        TimeUnit.SECONDS.sleep(1);
    }

    @Test
    @SneakyThrows(InterruptedException.class)
    void testAwaitTimeOut() {

        //1.创建循环栅栏，阻塞等待
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3, () -> {
            System.out.println("开始点菜！");
        });

        //2.创建线程1开始等待
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " 已到达餐厅");
            try {
                cyclicBarrier.await(100, TimeUnit.MILLISECONDS);
            } catch (InterruptedException | BrokenBarrierException | TimeoutException e) {
            }
            System.out.println(Thread.currentThread().getName() + " 不等了！开吃开吃！");
        }).start();

        //3.创建线程2开始等待
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " 已到达餐厅");
            try {
                cyclicBarrier.await(100, TimeUnit.MILLISECONDS);
            } catch (InterruptedException | BrokenBarrierException | TimeoutException e) {
            }
            System.out.println(Thread.currentThread().getName() + " 不等了！开吃开吃！");
        }).start();

        //4.创建线程3延迟进行await操作
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " 大家等我一会，我比较慢");

            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            try {
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                System.out.println(Thread.currentThread().getName() + " 你们竟然不等我，伤心了");
            }
        }).start();

        //5.睡一会，确保子线程执行完成
        TimeUnit.SECONDS.sleep(2);
    }
}
