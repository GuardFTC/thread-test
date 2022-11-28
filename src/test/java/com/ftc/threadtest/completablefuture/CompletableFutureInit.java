package com.ftc.threadtest.completablefuture;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.lang.Assert;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author: 冯铁城 [17615007230@163.com]
 * @date: 2022-11-24 15:43:25
 * @describe: CompletableFuture单元测试-初始化
 */
public class CompletableFutureInit {

    @Test
    void testFuture() throws ExecutionException, InterruptedException {

        //1.创建任务
        Future<String> submit = Util.EXECUTOR.submit(() -> {
            Util.sleepAndConsoleTime(100);
            return Util.DEFAULT_RESULT;
        });

        //2.开始计时
        Util.consoleStartWaiting();
        TimeInterval timer = DateUtil.timer();

        //3.获取结果值并打印
        Assert.isTrue(Util.DEFAULT_RESULT.equals(submit.get()));

        //4.输出总耗时
        Util.consoleEndWaitingAndBlockingTime(timer);
    }

    @Test
    @SneakyThrows(value = {ExecutionException.class, InterruptedException.class})
    void testInitNew() {

        //1.创建一个CompletableFuture
        CompletableFuture<String> completableFuture = new CompletableFuture<>();

        //2.定义返回结果
        completableFuture.complete(Thread.currentThread().getName() + ":new一个对象");

        //3.获取返回结果
        System.out.println(completableFuture.get());
    }

    @Test
    @SneakyThrows(value = {ExecutionException.class, InterruptedException.class})
    void testInitRunAsync() {

        //1.通过runAsync创建CompletableFuture
        CompletableFuture<Void> runAsync = CompletableFuture.runAsync(() -> {
            Util.sleepAndConsoleTime(100);
        }, Util.EXECUTOR);

        //2.开始计时
        Util.consoleStartWaiting();
        TimeInterval timer = DateUtil.timer();

        //3.阻塞式获取结果
        Assert.isNull(runAsync.get());

        //4.输出总耗时
        Util.consoleEndWaitingAndBlockingTime(timer);
    }

    @Test
    @SneakyThrows(value = {ExecutionException.class, InterruptedException.class})
    void testInitSupplyAsync() {

        //1.通过runAsync创建CompletableFuture
        CompletableFuture<String> supplyAsync = CompletableFuture.supplyAsync(() -> {
            Util.sleepAndConsoleTime(100);
            return Util.DEFAULT_RESULT;
        }, Util.EXECUTOR);

        //2.开始计时
        Util.consoleStartWaiting();
        TimeInterval timer = DateUtil.timer();

        //3.阻塞式获取结果
        Assert.isTrue(Util.DEFAULT_RESULT.equals(supplyAsync.get()));

        //4.输出总耗时
        Util.consoleEndWaitingAndBlockingTime(timer);
    }

    @Test
    void testJoinAndGet() {

        //1.创建一个CompletableFuture
        CompletableFuture<String> supplyAsync = CompletableFuture.supplyAsync(() -> {
            return Util.DEFAULT_RESULT;
        }, Util.EXECUTOR);

        //2.get方法-阻塞式获取异步结果-抛出受检时异常
        try {
            Assert.isTrue(Util.DEFAULT_RESULT.equals(supplyAsync.get()));
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

        //3.join方法-阻塞式获取异步结果-未抛出任何异常
        Assert.isTrue(Util.DEFAULT_RESULT.equals(supplyAsync.join()));
    }
}
