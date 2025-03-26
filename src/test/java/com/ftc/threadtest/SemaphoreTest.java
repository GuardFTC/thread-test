package com.ftc.threadtest;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @author: 冯铁城 [17615007230@163.com]
 * @date: 2025-03-25 09:17:42
 * @describe: 信号量单元测试
 */
public class SemaphoreTest {

    @Test
    public void testInit() {

        //1.初始化一个非公平信号量
        Semaphore noFairSemaphore = new Semaphore(3);

        //2.初始化一个公平信号量
        Semaphore fairSemaphore = new Semaphore(3, true);
    }

    @Test
    public void testAcquireAndRelease() throws InterruptedException {

        //1.初始化信号量
        Semaphore semaphore = new Semaphore(3, true);
        CountDownLatch countDownLatch = new CountDownLatch(5);

        //2.模拟线程进行信号量的授权以及释放
        for (int i = 0; i < 5; i++) {

            //3.创建线程
            new Thread(() -> {
                try {

                    //4.获取信号量授权（阻塞式）
                    semaphore.acquire();

                    //5.模拟顾客吃饭
                    System.out.println("顾客" + Thread.currentThread().getName() + "正在吃饭");
                    TimeUnit.SECONDS.sleep(2);
                    System.out.println("顾客" + Thread.currentThread().getName() + "吃完了");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {

                    //6.释放信号量授权
                    semaphore.release();
                    countDownLatch.countDown();
                }
            }).start();

            //7.模拟信号量授权已空
            if (semaphore.availablePermits() == 0) {
                System.out.println("餐馆已经满员");
            }
        }

        //8.主线程等待执行完毕
        countDownLatch.await();
    }

    @Test
    public void testAcquireAndReleaseMulti() throws InterruptedException {

        //1.初始化信号量
        Semaphore semaphore = new Semaphore(3, true);
        CountDownLatch countDownLatch = new CountDownLatch(5);

        //2.模拟线程进行信号量的授权以及释放
        for (int i = 0; i < 5; i++) {

            //3.创建线程
            int finalI = i;
            new Thread(() -> {
                try {

                    //4.获取信号量授权（阻塞式）
                    semaphore.acquire(finalI == 0 ? 2 : 1);

                    //5.模拟顾客吃饭
                    System.out.println("顾客" + Thread.currentThread().getName() + "正在吃饭");
                    TimeUnit.SECONDS.sleep(2);
                    System.out.println("顾客" + Thread.currentThread().getName() + "吃完了");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {

                    //6.释放信号量授权
                    semaphore.release(finalI == 0 ? 2 : 1);
                    countDownLatch.countDown();
                }
            }).start();

            //7.模拟信号量授权已空
            if (semaphore.availablePermits() == 0) {
                System.out.println("餐馆已经满员");
            }
        }

        //8.主线程等待执行完毕
        countDownLatch.await();
    }

    @Test
    public void testDrainPermitsAndTryAcquire() throws InterruptedException {

        //1.初始化信号量
        Semaphore semaphore = new Semaphore(3);
        CountDownLatch countDownLatch = new CountDownLatch(3);

        //2.模拟线程进行信号量的授权以及释放
        for (int i = 0; i < 3; i++) {

            //3.创建线程
            int finalI = i;
            new Thread(() -> {
                try {

                    //4.第一个线程获取全部信号量，其他线程尝试获取信号量
                    if (finalI == 0) {

                        //5.模拟获取全部信号量
                        int remaining = semaphore.drainPermits();

                        //6.模拟顾客吃饭
                        System.out.println("顾客" + Thread.currentThread().getName() + "包桌了！");
                        TimeUnit.SECONDS.sleep(2);
                        System.out.println("顾客" + Thread.currentThread().getName() + "吃完了");

                        //7.释放信号量授权
                        semaphore.release(remaining);
                    } else {
                        boolean isAcquire = semaphore.tryAcquire();
                        if (!isAcquire) {
                            System.out.println("顾客" + Thread.currentThread().getName() + "来晚了！");
                        }
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    countDownLatch.countDown();
                }
            }).start();
        }

        //8.主线程等待执行完毕
        countDownLatch.await();
    }
}
