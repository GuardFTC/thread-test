package com.ftc.threadtest;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import com.ftc.threadtest.service.CustomExecutorService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;

import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author: 冯铁城 [17615007230@163.com]
 * @date: 2022-11-03 18:06:04
 * @describe: 线程池测试
 */
@SpringBootTest
public class SpringExecutor {

    @Autowired
    private CustomExecutorService customExecutorService;

    @Autowired
    private ThreadPoolTaskScheduler customScheduledExecutor;

    @Test
    @SneakyThrows({ExecutionException.class, InterruptedException.class})
    void testThreadPoolTaskExecutor() {

        //1.运行5次无返回值方法
        for (int i = 0; i < 5; i++) {
            customExecutorService.consoleThreadName();
        }

        //2.运行5次有返回值方法
        for (int i = 0; i < 5; i++) {
            CompletableFuture<String> future = customExecutorService.getThreadName();
            System.out.println(future.get());
        }

        //3.运行异常无返回方法
        customExecutorService.consoleThreadNameError();

        //4.运行异常有返回值方法
        CompletableFuture<String> future = customExecutorService.getThreadNameError();
        ExecutionException exception = assertThrows(ExecutionException.class,
                future::get
        );

        //5.校验异常信息
        String errorMessage = "java.lang.ArithmeticException: / by zero";
        Assert.isTrue(errorMessage.equals(exception.getMessage()));

        //6.睡一会，确保子线程运行完毕
        TimeUnit.SECONDS.sleep(1);
    }

    @Test
    @SneakyThrows(InterruptedException.class)
    void testThreadPoolTaskScheduler() {

        //1.定时执行任务，输出开始时间
        System.out.println("start time" + DateUtil.now());

        //2.在2s后运行任务
        customScheduledExecutor.schedule(() -> {
            System.out.println("running time" + DateUtil.now());
        }, DateUtil.offsetSecond(new Date(DateUtil.current()), 2));

        //3.周期性执行任务,每2s执行一次
        customScheduledExecutor.schedule(() -> {
            System.out.println("corn running time" + DateUtil.now());
        }, new CronTrigger("0/2 * * * * ? "));

        //4.睡一会，确保子线程全部运行成功
        TimeUnit.SECONDS.sleep(5);
    }
}
