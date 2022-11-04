package com.ftc.threadtest.deal;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import lombok.SneakyThrows;

import java.util.concurrent.TimeUnit;

/**
 * @author: 冯铁城 [17615007230@163.com]
 * @date: 2022-11-02 18:31:34
 * @describe: 人类类
 */
public class People {

    /**
     * 玩手机次数
     */
    private final static int PLAY_PHONE_TIME = 4;

    /**
     * 玩手机
     */
    @SneakyThrows(InterruptedException.class)
    public synchronized void playPhone() {

        //1.晚上玩手机
        for (int i = 1; i <= PLAY_PHONE_TIME; i++) {
            System.out.println(Thread.currentThread().getName() + "->night I am playing phone step:" + i);
            if (i == 2) {
                this.wait();
            }
        }

        //2.唤醒睡觉线程
        this.notify();

        //3.继续等待被睡觉线程唤醒
        this.wait();

        //4.早上玩手机
        for (int i = 1; i <= PLAY_PHONE_TIME; i++) {
            System.out.println(Thread.currentThread().getName() + "->morning I am playing phone step:" + i);
        }
    }

    /**
     * 刷牙
     */
    @SneakyThrows(InterruptedException.class)
    public synchronized void brushTeeth() {

        //1.晚上刷牙
        System.out.println(Thread.currentThread().getName() + "->night I am brushing teeth");

        //2.唤醒玩手机线程
        this.notify();

        //3.继续等待被睡觉线程唤醒
        this.wait();

        //4.早上刷牙
        System.out.println(Thread.currentThread().getName() + "->morning I am brushing teeth");
    }

    /**
     * 睡觉
     */
    @SneakyThrows(InterruptedException.class)
    public synchronized void sleep() {

        //1.睡眠线程直接等待
        this.wait();

        //2.睡觉
        TimeInterval timer = DateUtil.timer();
        System.out.println(Thread.currentThread().getName() + "->I am sleeping");

        //3.睡1s
        TimeUnit.SECONDS.sleep(1);

        //4.睡醒了
        System.out.println(Thread.currentThread().getName() + "->I am wake up.for time:" + timer.intervalSecond() + "s");

        //5.唤醒刷牙和玩手机线程
        this.notifyAll();
    }
}
