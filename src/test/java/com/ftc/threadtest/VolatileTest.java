package com.ftc.threadtest;

import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author: 冯铁城 [17615007230@163.com]
 * @date: 2022-11-17 10:39:15
 * @describe: volatile关键字单元测试
 */
public class VolatileTest {

    volatile boolean help = false;

    volatile boolean end = false;

    @Test
    void testVolatile() {

        //1.线程自旋，呼救
        new Thread(() -> {
            for (; ; ) {
                if (help) {
                    System.out.println(Thread.currentThread().getName() + ":得救了！哈哈哈哈！！！");
                    end = true;
                    break;
                }
            }
        }).start();

        //2.第二个线程来拯救你
        new Thread(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            help = true;
            System.out.println(Thread.currentThread().getName() + ":我来救你！！！！");
        }).start();

        //3.自旋判定是否结束
        while (!end) {

        }
        System.out.println(Thread.currentThread().getName() + ":终于结束了！！！！");
    }
}
