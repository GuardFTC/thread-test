package com.ftc.threadtest;

import com.ftc.threadtest.lock.SynchronizedTestEntity;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

/**
 * @author: 冯铁城 [17615007230@163.com]
 * @date: 2022-11-08 16:35:20
 * @describe: synchronized关键字单元测试
 */
public class SynchronizedTest {

    @Test
    @SneakyThrows(InterruptedException.class)
    void testObjectMethod() {

        //1.创建两个测试对象
        SynchronizedTestEntity synchronizedTest1 = new SynchronizedTestEntity();
        SynchronizedTestEntity synchronizedTest2 = new SynchronizedTestEntity();

        //2.创建3个线程
        Thread thread11 = new Thread(synchronizedTest1::consoleInfo);
        Thread thread12 = new Thread(synchronizedTest1::consoleInfo);
        Thread thread2 = new Thread(synchronizedTest2::consoleInfo);

        //3.线程运行
        thread11.start();
        thread12.start();
        thread2.start();

        //4.join主线程，确保子线程运行完，主线程再运行
        thread11.join();
        thread12.join();
        thread2.join();
    }

    @Test
    @SneakyThrows(InterruptedException.class)
    void testObjectStaticMethod() {

        //1.创建两个测试对象
        SynchronizedTestEntity synchronizedTest1 = new SynchronizedTestEntity();
        SynchronizedTestEntity synchronizedTest2 = new SynchronizedTestEntity();

        //2.创建3个线程
        Thread thread11 = new Thread(synchronizedTest1);
        Thread thread12 = new Thread(synchronizedTest1);
        Thread thread2 = new Thread(synchronizedTest2);

        //3.线程运行
        thread11.start();
        thread12.start();
        thread2.start();

        //4.join主线程，确保子线程运行完，主线程再运行
        thread11.join();
        thread12.join();
        thread2.join();
    }

    @Test
    @SneakyThrows(InterruptedException.class)
    void testObjectCodeBlock() {

        //1.创建两个测试对象
        SynchronizedTestEntity synchronizedTest1 = new SynchronizedTestEntity();
        SynchronizedTestEntity synchronizedTest2 = new SynchronizedTestEntity();

        //2.创建3个线程
        Thread thread11 = new Thread(synchronizedTest1::consoleInfoCodeBlockForObject);
        Thread thread12 = new Thread(synchronizedTest1::consoleInfoCodeBlockForObject);
        Thread thread2 = new Thread(synchronizedTest2::consoleInfoCodeBlockForObject);

        //3.线程运行
        thread11.start();
        thread12.start();
        thread2.start();

        //4.join主线程，确保子线程运行完，主线程再运行
        thread11.join();
        thread12.join();
        thread2.join();
    }

    @Test
    @SneakyThrows(InterruptedException.class)
    void testClassCodeBlock() {

        //1.创建两个测试对象
        SynchronizedTestEntity synchronizedTest1 = new SynchronizedTestEntity();
        SynchronizedTestEntity synchronizedTest2 = new SynchronizedTestEntity();

        //2.创建3个线程
        Thread thread11 = new Thread(synchronizedTest1::consoleInfoCodeBlockForClass);
        Thread thread12 = new Thread(synchronizedTest1::consoleInfoCodeBlockForClass);
        Thread thread2 = new Thread(synchronizedTest2::consoleInfoCodeBlockForClass);

        //3.线程运行
        thread11.start();
        thread12.start();
        thread2.start();

        //4.join主线程，确保子线程运行完，主线程再运行
        thread11.join();
        thread12.join();
        thread2.join();
    }
}
