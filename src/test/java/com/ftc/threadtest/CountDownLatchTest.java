package com.ftc.threadtest;

import cn.hutool.core.collection.CollUtil;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author: 冯铁城 [17615007230@163.com]
 * @date: 2022-11-19 10:16:32
 * @describe: CountDownLatch单元测试
 */
public class CountDownLatchTest {

    @Test
    void testCountDownLatchEqualZero() {

        //1.创建计数器
        CountDownLatch count = new CountDownLatch(2);

        //2.开始考试
        System.out.println("开始考试");

        //3.线程1开始考试
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " 考完了！！！");
            count.countDown();
            System.out.println(Thread.currentThread().getName() + " 玩去了玩去了");
        }).start();

        //4.线程2开始考试
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " 考完了！！！");
            count.countDown();
            System.out.println(Thread.currentThread().getName() + " 玩去了玩去了");
        }).start();

        //5.主线程监考
        try {
            count.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("考试完毕");
    }

    @Test
    void testCountDownLatchLessZero() {

        //1.创建计数器
        CountDownLatch count = new CountDownLatch(2);

        //2.开始考试
        System.out.println("开始考试");

        //3.线程1开始考试
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " 考完了！！！");
            count.countDown();
            System.out.println(Thread.currentThread().getName() + " 玩去了玩去了");
        }).start();

        //4.线程监考
        try {
            count.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("考试完毕");
    }

    @Test
    void testCountDownLatchMoreZero() {

        //1.创建计数器
        CountDownLatch count = new CountDownLatch(2);

        //2.开始考试
        System.out.println("开始考试");

        //3.线程1开始考试
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " 考完了！！！");
            count.countDown();
            System.out.println(Thread.currentThread().getName() + " 玩去了玩去了");
        }).start();

        //4.线程2开始考试
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " 考完了！！！");
            count.countDown();
            System.out.println(Thread.currentThread().getName() + " 玩去了玩去了");
        }).start();

        //5.线程3开始考试
        new Thread(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName() + " 考完了！！！");
            count.countDown();
            System.out.println(Thread.currentThread().getName() + " 玩去了玩去了");
        }).start();

        //6.线程监考
        try {
            count.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("考试完毕");
    }

    @Test
    void testCountDownLatchTimeOut() {

        //1.创建计数器
        CountDownLatch count = new CountDownLatch(3);

        //2.开始考试
        System.out.println("开始考试");

        //3.线程1开始考试
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " 考完了！！！");
            count.countDown();
            System.out.println(Thread.currentThread().getName() + " 玩去了玩去了");
        }).start();

        //4.线程2开始考试
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " 考完了！！！");
            count.countDown();
            System.out.println(Thread.currentThread().getName() + " 玩去了玩去了");
        }).start();

        //5.线程3开始考试
        new Thread(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName() + " 考完了！！！");
            count.countDown();
            System.out.println(Thread.currentThread().getName() + " 玩去了玩去了");
        }).start();

        //6.线程监考
        try {
            count.await(300, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("考试完毕");
    }

    @Test
    void testCountDownLatchOneToMore() {

        //1.创建士兵线程计数器
        CountDownLatch countDownLatch = new CountDownLatch(1);

        //2.创建指挥官线程计数器
        CountDownLatch mainCountDownLatch = new CountDownLatch(3);

        //3.创建士兵线程1，等待炸碉堡。炸完碉堡后冲锋
        new Thread(() -> {
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName() + ":冲啊！！！！！");
            mainCountDownLatch.countDown();
        }).start();

        //4.创建士兵线程2，等待炸碉堡。炸完碉堡后冲锋
        new Thread(() -> {
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName() + ":冲啊！！！！！");
            mainCountDownLatch.countDown();
        }).start();

        //5.创建士兵线程3，等待炸碉堡。炸完碉堡后冲锋
        new Thread(() -> {
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName() + ":冲啊！！！！！");
            mainCountDownLatch.countDown();
        }).start();

        //5.创建炸碉堡线程
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + ":我来炸碉堡！");
            countDownLatch.countDown();
            System.out.println(Thread.currentThread().getName() + ":炸完了！");
        }).start();

        //6.指挥官线程阻塞，等待士兵冲锋完毕
        try {
            mainCountDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(Thread.currentThread().getName() + ":我们胜利了！！！！");
    }

    /**
     * A砖垛
     */
    private List<Integer> allBricks = new ArrayList<>();

    /**
     * 从B砖垛搬砖
     *
     * @return 从B砖垛搬的砖
     */
    public List<Integer> getBricks() {

        //1.将100块砖放到小推车
        List<Integer> bricks = CollUtil.newArrayList();
        for (int i = 0; i < 100; i++) {
            bricks.add(i);
        }

        //2.返回
        System.out.println(Thread.currentThread().getName() + " 从A砖垛搬了100块砖");
        return bricks;
    }

    /**
     * 从B砖垛搬的砖放到A砖垛
     *
     * @param bricks 从B砖垛搬的砖
     */
    public synchronized void addBricks(List<Integer> bricks) {
        System.out.println(Thread.currentThread().getName() + " 向B砖垛放了100块砖");
        allBricks.addAll(bricks);
    }

    @Test
    void testCountDownLatchDemo() {

        //1.创建计数器
        CountDownLatch countDownLatch = new CountDownLatch(4);

        //2.搬砖人1号
        new Thread(() -> {
            addBricks(getBricks());
            countDownLatch.countDown();
            System.out.println(Thread.currentThread().getName() + " 老子回家睡觉了");
        }).start();

        //3.搬砖人2号
        new Thread(() -> {
            addBricks(getBricks());
            countDownLatch.countDown();
            System.out.println(Thread.currentThread().getName() + " 老子回家睡觉了");
        }).start();

        //4.搬砖人3号
        new Thread(() -> {
            addBricks(getBricks());
            countDownLatch.countDown();
            System.out.println(Thread.currentThread().getName() + " 老子回家睡觉了");
        }).start();

        //5.搬砖人4号
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            addBricks(getBricks());
            countDownLatch.countDown();
            System.out.println(Thread.currentThread().getName() + " 老子回家睡觉了");
        }).start();

        //6.主线程包工头,只给各位1s
        try {
            countDownLatch.await(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        //7.搬砖完毕
        System.out.println("搬砖完毕！大家一共搬了:" + allBricks.size() + "块砖");
    }
}
