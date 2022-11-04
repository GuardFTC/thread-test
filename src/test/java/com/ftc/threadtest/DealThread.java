package com.ftc.threadtest;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author: 冯铁城 [17615007230@163.com]
 * @date: 2022-11-02 18:29:34
 * @describe: 通过线程进行操作
 */
class DealThread {

    private static int COUNT = 0;

    @Test
    @SneakyThrows(InterruptedException.class)
    void testAdd() {

        //1.循环创建10个线程，对COUNT进行++操作
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                COUNT++;
                System.out.println(Thread.currentThread().getName() + "->COUNT:" + COUNT);
            }).start();
        }

        //2.输出最终结果
        TimeUnit.MILLISECONDS.sleep(100);
        System.out.println("result->" + COUNT);
    }

    @Test
    @SneakyThrows(InterruptedException.class)
    void testAddV2() {

        //1.创建Add对象
        Add add = new Add();

        //2.循环创建10个线程，对Add对象的count进行++操作
        for (int i = 0; i < 10; i++) {
            new Thread(add).start();
        }

        //3.输出最终结果
        TimeUnit.MILLISECONDS.sleep(100);
        add.consoleCount();
    }

    /**
     * 内部类
     */
    private class Add implements Runnable {

        /**
         * count属性
         */
        int count = 0;

        /**
         * 控制台输出count
         */
        public void consoleCount() {
            System.out.println("result->" + count);
        }

        /**
         * count递增
         */
        public void addCount() {
            count++;
            System.out.println(Thread.currentThread().getName() + "->COUNT:" + count);
        }

        /**
         * 线程运行的run方法
         */
        @Override
        public void run() {
            addCount();
        }
    }
}
