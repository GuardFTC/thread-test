package com.ftc.threadtest.create;

/**
 * @author: 冯铁城 [17615007230@163.com]
 * @date: 2022-11-02 15:39:33
 * @describe: 自定义线程类 实现Runnable
 */
public class CustomRunnable implements Runnable {

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "->CustomRunnable执行成功");
    }
}
