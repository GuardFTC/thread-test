package com.ftc.threadtest.create;

import java.util.concurrent.Callable;

/**
 * @author: 冯铁城 [17615007230@163.com]
 * @date: 2022-11-02 15:40:31
 * @describe: 自定义线程类 实现Callable
 */
public class CustomCallable implements Callable<String> {

    @Override
    public String call() throws Exception {
        System.out.println(Thread.currentThread().getName() + "->CustomCallable执行成功");
        return Thread.currentThread().getName() + "->CustomCallable执行成功";
    }
}
