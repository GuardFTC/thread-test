package com.ftc.threadtest.lock;

import cn.hutool.core.date.DateUtil;
import lombok.SneakyThrows;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: 冯铁城 [17615007230@163.com]
 * @date: 2022-11-10 16:54:59
 * @describe: Reentrantlock测试类
 */
public class ReentrantlockTestEntity {

    /**
     * 创建一个静态常量锁
     */
    public static final ReentrantLock CLASS_LOCK = new ReentrantLock();

    /**
     * 控制台输出线程执行信息
     */
    public void consoleInfo() {

        //1.尝试获取锁
        CLASS_LOCK.lock();
        try {

            //2.睡1s，证明线程的阻塞状态
            TimeUnit.SECONDS.sleep(1);

            //3.控制台输出线程运行信息
            System.out.println(Thread.currentThread().getName() + " is running on " + DateUtil.now());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            CLASS_LOCK.unlock();
        }
    }

    /**
     * 控制台输出是否获取锁成功
     */
    public void consoleIsLock() {

        //1.尝试获取锁并返回获取结果，阻塞时间0s
        boolean success = CLASS_LOCK.tryLock();

        //2.获取锁成功/失败
        if (success) {
            try {
                System.out.println(Thread.currentThread().getName() + " get lock success on " + DateUtil.now());
            } finally {
                CLASS_LOCK.unlock();
            }
        } else {
            System.out.println(Thread.currentThread().getName() + " get lock fail on " + DateUtil.now());
        }
    }

    /**
     * 控制台输出是否获取锁成功-设置时限
     */
    @SneakyThrows(InterruptedException.class)
    public void consoleIsLockTimeLimit() {

        //1.尝试获取锁并返回获取结果，阻塞时间2s
        boolean success = CLASS_LOCK.tryLock(2, TimeUnit.SECONDS);

        //2.获取锁成功/失败
        if (success) {
            try {
                System.out.println(Thread.currentThread().getName() + " get lock success on " + DateUtil.now());
            } finally {
                CLASS_LOCK.unlock();
            }
        } else {
            System.out.println(Thread.currentThread().getName() + " get lock fail on " + DateUtil.now());
        }
    }

    /**
     * 控制台输出线程执行信息
     */
    public void consoleInfoInterrupt() {
        try {

            //1.尝试获取锁并处于可中断状态
            CLASS_LOCK.lockInterruptibly();
            try {

                //2.睡2s,确保占用锁的时间足够久
                TimeUnit.SECONDS.sleep(2);

                //3.控制台输出线程运行信息
                System.out.println(Thread.currentThread().getName() + " is running on " + DateUtil.now());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                CLASS_LOCK.unlock();
            }
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + " get lock interrupt");
        }
    }

    /**
     * 控制台输出线程执行信息
     */
    public void consoleInfoV2() {

        //1.尝试获取锁
        CLASS_LOCK.lock();
        try {
            System.out.println("------------------------------" + Thread.currentThread().getName() + "--------------------------------");

            //2.控制台打印线程运行信息
            System.out.println(Thread.currentThread().getName() + " is running on " + DateUtil.now());

            //3.控制台打印，当前线程共占有了几次锁
            System.out.println(Thread.currentThread().getName() + " get lock time is " + CLASS_LOCK.getHoldCount());

            //4.控制台打印，目前共多少个线程正在阻塞
            System.out.println("Block thread num is " + CLASS_LOCK.getQueueLength());

            //5.调用consoleInfoV3
            consoleInfoV3();
        } finally {
            CLASS_LOCK.unlock();
        }
    }

    /**
     * 控制台输出线程执行信息
     */
    public void consoleInfoV3() {

        //1.尝试获取锁
        CLASS_LOCK.lock();
        try {

            //2.控制台打印线程运行信息
            System.out.println(Thread.currentThread().getName() + " is running on " + DateUtil.now());

            //3.控制台打印，当前线程共占有了几次锁
            System.out.println(Thread.currentThread().getName() + " get lock time is " + CLASS_LOCK.getHoldCount());
        } finally {
            CLASS_LOCK.unlock();
        }
    }
}
