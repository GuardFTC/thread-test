package com.ftc.threadtest.create;

/**
 * @author: 冯铁城 [17615007230@163.com]
 * @date: 2022-11-02 15:36:53
 * @describe: 自定义线程类 继承Thread
 */
public class CustomThread extends Thread {

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "->CustomThread执行成功");
    }
}
