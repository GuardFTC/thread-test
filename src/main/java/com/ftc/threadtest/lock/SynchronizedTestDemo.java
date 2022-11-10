package com.ftc.threadtest.lock;

/**
 * @author: 冯铁城 [17615007230@163.com]
 * @date: 2022-11-08 18:58:51
 * @describe: Synchronized关键字测试类
 */
public class SynchronizedTestDemo {

    public synchronized void consoleInfo() {

    }

    public static synchronized void consoleInfoStatic() {

    }

    public void consoleInfoCodeBlockForObject() {
        synchronized (this) {
        }
    }

    public void consoleInfoCodeBlockForClass() {
        synchronized (SynchronizedTestDemo.class) {
        }
    }
}
