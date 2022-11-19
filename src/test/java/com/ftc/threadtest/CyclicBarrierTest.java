package com.ftc.threadtest;

import org.junit.jupiter.api.Test;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

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
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3,()->{
            System.out.println("所有玩家已经进入游戏！");
        });

        //3.开始考试
        System.out.println("等待玩家进入");

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
        System.out.println("全部玩家已进入，开始游戏！！");
    }
}
