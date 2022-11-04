package com.ftc.threadtest.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

/**
 * @author: 冯铁城 [17615007230@163.com]
 * @date: 2022-11-04 15:19:19
 * @describe: 自定义线程池业务类
 */
@Component
public class CustomExecutorService {

    /**
     * 控制台打印线程池名称
     */
    @Async("customExecutor")
    public void consoleThreadName() {
        System.out.println(Thread.currentThread().getName() + " is running");
    }

    /**
     * 控制台打印线程池名称异常
     */
    @Async("customExecutor")
    public void consoleThreadNameError() {
        throw new RuntimeException("mock service error");
    }

    /**
     * 返回线程池名称
     *
     * @return 线程池名称
     */
    @Async("customExecutor")
    public CompletableFuture<String> getThreadName() {
        return CompletableFuture.completedFuture(Thread.currentThread().getName());
    }

    /**
     * 返回线程池名称异常
     *
     * @return 线程池名称
     */
    @Async("customExecutor")
    public CompletableFuture<String> getThreadNameError() {
        int error = 1 / 0;
        return CompletableFuture.completedFuture(Thread.currentThread().getName());
    }
}
