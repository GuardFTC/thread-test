package com.ftc.threadtest.completablefuture;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author: 冯铁城 [17615007230@163.com]
 * @date: 2022-11-24 15:43:25
 * @describe: CompletableFuture单元测试-回调
 */
public class CompletableFutureCallback {

    @Test
    @SneakyThrows(value = {ExecutionException.class, InterruptedException.class})
    void testThenRun() {

        //1.创建CompletableFuture，设置同步回调
        CompletableFuture<Void> sync = CompletableFuture.supplyAsync(() -> {
            Util.sleepAndConsoleTime(100);
            return Util.DEFAULT_RESULT;
        }, Util.EXECUTOR).thenRun(() -> {
            Util.sleepAndConsoleTime(100);
        });

        //2.阻塞式获取同步结果
        Util.consoleStartWaiting();
        TimeInterval timer = DateUtil.timer();
        Assert.isNull(sync.get());
        Util.consoleEndWaitingAndBlockingTime(timer);

        //3.打印分隔线
        Util.consoleDividingLine();

        //4.创建CompletableFuture，设置异步回调
        CompletableFuture<Void> async = CompletableFuture.supplyAsync(() -> {
            Util.sleepAndConsoleTime(100);
            return Util.DEFAULT_RESULT;
        }, Util.EXECUTOR).thenRunAsync(() -> {
            Util.sleepAndConsoleTime(100);
        }, Util.EXECUTOR);

        //5.阻塞式获取异步结果
        Util.consoleStartWaiting();
        timer = DateUtil.timer();
        Assert.isNull(async.get());
        Util.consoleEndWaitingAndBlockingTime(timer);

        //6.打印分隔线
        Util.consoleDividingLine();

        //7.创建CompletableFuture, 模拟发生异常
        CompletableFuture<Void> withException = CompletableFuture.supplyAsync(() -> {
            Util.sleepAndConsoleTime(100);
            throw new RuntimeException("mock");
        }, Util.EXECUTOR).thenRunAsync(() -> {
            Util.sleepAndConsoleTime(100);
        }, Util.EXECUTOR);

        //8.阻塞式获取异步结果
        Util.consoleStartWaiting();
        ExecutionException runtimeException = assertThrows(ExecutionException.class,
                () -> withException.get()
        );
        Assert.isTrue("java.lang.RuntimeException: mock".equals(runtimeException.getMessage()));
    }

    @Test
    @SneakyThrows(value = {ExecutionException.class, InterruptedException.class})
    void testThenAccept() {

        //1.创建CompletableFuture，设置同步回调
        CompletableFuture<Void> sync = CompletableFuture.supplyAsync(() -> {
            Util.sleepAndConsoleTime(100);
            return Util.DEFAULT_RESULT;
        }, Util.EXECUTOR).thenAccept((result) -> {
            Assert.isTrue(Util.DEFAULT_RESULT.equals(result));
            Util.sleepAndConsoleTime(100);
        });

        //2.阻塞式获取同步结果
        Util.consoleStartWaiting();
        TimeInterval timer = DateUtil.timer();
        Assert.isNull(sync.get());
        Util.consoleEndWaitingAndBlockingTime(timer);

        //3.打印分隔线
        Util.consoleDividingLine();

        //4.创建CompletableFuture，设置异步回调
        CompletableFuture<Void> async = CompletableFuture.supplyAsync(() -> {
            Util.sleepAndConsoleTime(100);
            return Util.DEFAULT_RESULT;
        }, Util.EXECUTOR).thenAcceptAsync((result) -> {
            Assert.isTrue(Util.DEFAULT_RESULT.equals(result));
            Util.sleepAndConsoleTime(100);
        }, Util.EXECUTOR);

        //5.阻塞式获取异步结果
        Util.consoleStartWaiting();
        timer = DateUtil.timer();
        Assert.isNull(async.get());
        Util.consoleEndWaitingAndBlockingTime(timer);

        //6.打印分隔线
        Util.consoleDividingLine();

        //7.创建CompletableFuture, 模拟发生异常
        CompletableFuture<Void> withException = CompletableFuture.supplyAsync(() -> {
            Util.sleepAndConsoleTime(100);
            throw new RuntimeException("mock");
        }, Util.EXECUTOR).thenAcceptAsync((result) -> {
            Assert.isTrue(Util.DEFAULT_RESULT.equals(result));
            Util.sleepAndConsoleTime(100);
        }, Util.EXECUTOR);

        //8.阻塞式获取异步结果
        Util.consoleStartWaiting();
        ExecutionException runtimeException = assertThrows(ExecutionException.class,
                () -> withException.get()
        );
        Assert.isTrue("java.lang.RuntimeException: mock".equals(runtimeException.getMessage()));
    }

    @Test
    @SneakyThrows(value = {ExecutionException.class, InterruptedException.class})
    void testThenApply() {

        //1.创建CompletableFuture，设置同步回调
        CompletableFuture<String> sync = CompletableFuture.supplyAsync(() -> {
            Util.sleepAndConsoleTime(100);
            return Util.DEFAULT_RESULT;
        }, Util.EXECUTOR).thenApply((result) -> {
            Assert.isTrue(Util.DEFAULT_RESULT.equals(result));
            Util.sleepAndConsoleTime(100);
            return Util.DEFAULT_RESULT;
        });

        //2.阻塞式获取同步结果
        Util.consoleStartWaiting();
        TimeInterval timer = DateUtil.timer();
        Assert.isTrue(Util.DEFAULT_RESULT.equals(sync.get()));
        Util.consoleEndWaitingAndBlockingTime(timer);

        //3.打印分隔线
        Util.consoleDividingLine();

        //4.创建CompletableFuture，设置异步回调
        CompletableFuture<String> async = CompletableFuture.supplyAsync(() -> {
            Util.sleepAndConsoleTime(100);
            return Util.DEFAULT_RESULT;
        }, Util.EXECUTOR).thenApplyAsync((result) -> {
            Assert.isTrue(Util.DEFAULT_RESULT.equals(result));
            Util.sleepAndConsoleTime(100);
            return Util.DEFAULT_RESULT;
        }, Util.EXECUTOR);

        //5.阻塞式获取异步结果
        Util.consoleStartWaiting();
        timer = DateUtil.timer();
        Assert.isTrue(Util.DEFAULT_RESULT.equals(async.get()));
        Util.consoleEndWaitingAndBlockingTime(timer);

        //6.打印分隔线
        Util.consoleDividingLine();

        //7.创建CompletableFuture, 模拟发生异常
        CompletableFuture<String> withException = CompletableFuture.supplyAsync(() -> {
            Util.sleepAndConsoleTime(100);
            throw new RuntimeException("mock");
        }, Util.EXECUTOR).thenApplyAsync((result) -> {
            Assert.isTrue(Util.DEFAULT_RESULT.equals(result));
            Util.sleepAndConsoleTime(100);
            return Util.DEFAULT_RESULT;
        }, Util.EXECUTOR);

        //8.阻塞式获取异步结果
        Util.consoleStartWaiting();
        ExecutionException runtimeException = assertThrows(ExecutionException.class,
                () -> withException.get()
        );
        Assert.isTrue("java.lang.RuntimeException: mock".equals(runtimeException.getMessage()));
    }
    
    @Test
    @SneakyThrows(value = {ExecutionException.class, InterruptedException.class})
    void testThenCompose() {

        //1.创建CompletableFuture，设置同步回调
        CompletableFuture<String> sync = CompletableFuture.supplyAsync(() -> {
            Util.sleepAndConsoleTime(100);
            return Util.DEFAULT_RESULT;
        }, Util.EXECUTOR).thenCompose((result) -> {
            Util.sleepAndConsoleTime(100);
            return CompletableFuture.supplyAsync(() -> {
                Assert.isTrue(Util.DEFAULT_RESULT.equals(result));
                return Util.DEFAULT_RESULT;
            });
        });

        //2.阻塞式获取同步结果
        Util.consoleStartWaiting();
        TimeInterval timer = DateUtil.timer();
        Assert.isTrue(Util.DEFAULT_RESULT.equals(sync.get()));
        Util.consoleEndWaitingAndBlockingTime(timer);

        //3.打印分隔线
        Util.consoleDividingLine();

        //4.创建CompletableFuture，设置异步回调
        CompletableFuture<String> async = CompletableFuture.supplyAsync(() -> {
            Util.sleepAndConsoleTime(100);
            return Util.DEFAULT_RESULT;
        }, Util.EXECUTOR).thenComposeAsync((result) -> {
            Util.sleepAndConsoleTime(100);
            return CompletableFuture.supplyAsync(() -> {
                Assert.isTrue(Util.DEFAULT_RESULT.equals(result));
                return Util.DEFAULT_RESULT;
            });
        }, Util.EXECUTOR);

        //5.阻塞式获取异步结果
        Util.consoleStartWaiting();
        timer = DateUtil.timer();
        Assert.isTrue(Util.DEFAULT_RESULT.equals(async.get()));
        Util.consoleEndWaitingAndBlockingTime(timer);

        //6.打印分隔线
        Util.consoleDividingLine();

        //7.创建CompletableFuture，设置异步回调
        CompletableFuture<String> withException = CompletableFuture.supplyAsync(() -> {
            Util.sleepAndConsoleTime(100);
            throw new RuntimeException("mock");
        }, Util.EXECUTOR).thenComposeAsync((result) -> {
            Util.sleepAndConsoleTime(100);
            return CompletableFuture.supplyAsync(() -> {
                Assert.isTrue(Util.DEFAULT_RESULT.equals(result));
                return Util.DEFAULT_RESULT;
            });
        }, Util.EXECUTOR);

        //8.阻塞式获取异步结果
        Util.consoleStartWaiting();
        ExecutionException runtimeException = assertThrows(ExecutionException.class,
                () -> withException.get()
        );
        Assert.isTrue("java.lang.RuntimeException: mock".equals(runtimeException.getMessage()));
    }

    @Test
    @SneakyThrows(value = {ExecutionException.class, InterruptedException.class})
    void testExceptionally() {

        //1.创建CompletableFuture, 设置异常回调(无异常)
        CompletableFuture<String> withOutException = CompletableFuture.supplyAsync(() -> {
            Util.sleepAndConsoleTime(100);
            return Util.DEFAULT_RESULT;
        }, Util.EXECUTOR).exceptionally((exception) -> {
            Util.sleepAndConsoleTime(100);
            return exception.getMessage();
        });

        //2.阻塞式获取同步结果
        Util.consoleStartWaiting();
        TimeInterval timer = DateUtil.timer();
        Assert.isTrue(Util.DEFAULT_RESULT.equals(withOutException.get()));
        Util.consoleEndWaitingAndBlockingTime(timer);

        //3.打印分隔线
        Util.consoleDividingLine();

        //4.创建CompletableFuture, 设置异常回调(有异常)
        CompletableFuture<Object> withException = CompletableFuture.supplyAsync(() -> {
            Util.sleepAndConsoleTime(100);
            throw new RuntimeException("mock");
        }, Util.EXECUTOR).exceptionally((exception) -> {
            Util.sleepAndConsoleTime(100);
            return exception.getMessage();
        });

        //5.阻塞式获取异步结果
        Util.consoleStartWaiting();
        timer = DateUtil.timer();
        Assert.isTrue("java.lang.RuntimeException: mock".equals(withException.get()));
        Util.consoleEndWaitingAndBlockingTime(timer);
    }

    @Test
    @SneakyThrows(value = {ExecutionException.class, InterruptedException.class})
    void testWhenComplete() {

        //1.创建CompletableFuture，设置同步回调
        CompletableFuture<String> sync = CompletableFuture.supplyAsync(() -> {
            Util.sleepAndConsoleTime(100);
            return Util.DEFAULT_RESULT;
        }, Util.EXECUTOR).whenComplete((result, throwable) -> {
            Assert.isTrue(Util.DEFAULT_RESULT.equals(result));
            Util.sleepAndConsoleTime(100);
        });

        //2.阻塞式获取同步结果
        Util.consoleStartWaiting();
        TimeInterval timer = DateUtil.timer();
        Assert.isTrue(Util.DEFAULT_RESULT.equals(sync.get()));
        Util.consoleEndWaitingAndBlockingTime(timer);

        //3.打印分隔线
        Util.consoleDividingLine();

        //4.创建CompletableFuture，设置异步回调
        CompletableFuture<String> async = CompletableFuture.supplyAsync(() -> {
            Util.sleepAndConsoleTime(100);
            return Util.DEFAULT_RESULT;
        }, Util.EXECUTOR).whenCompleteAsync((result, throwable) -> {
            Assert.isTrue(Util.DEFAULT_RESULT.equals(result));
            Util.sleepAndConsoleTime(100);
        }, Util.EXECUTOR);

        //5.阻塞式获取异步结果
        Util.consoleStartWaiting();
        timer = DateUtil.timer();
        Assert.isTrue(Util.DEFAULT_RESULT.equals(async.get()));
        Util.consoleEndWaitingAndBlockingTime(timer);

        //6.打印分隔线
        Util.consoleDividingLine();

        //7.创建CompletableFuture，模拟发生异常
        CompletableFuture<Object> withException = CompletableFuture.supplyAsync(() -> {
            Util.sleepAndConsoleTime(100);
            throw new RuntimeException("mock");
        }, Util.EXECUTOR).whenCompleteAsync((result, throwable) -> {
            Assert.isNull(result);
            Util.sleepAndConsoleTime(100);
        }, Util.EXECUTOR);

        //8.阻塞式获取异步结果
        Util.consoleStartWaiting();
        ExecutionException runtimeException = assertThrows(ExecutionException.class,
                () -> withException.get()
        );
        Assert.isTrue("java.lang.RuntimeException: mock".equals(runtimeException.getMessage()));
    }

    @Test
    @SneakyThrows(value = {ExecutionException.class, InterruptedException.class})
    void handler() {

        //1.创建CompletableFuture，设置同步回调
        CompletableFuture<String> sync = CompletableFuture.supplyAsync(() -> {
            Util.sleepAndConsoleTime(100);
            return Util.DEFAULT_RESULT;
        }, Util.EXECUTOR).handle((result, throwable) -> {
            Assert.isTrue(Util.DEFAULT_RESULT.equals(result));
            Assert.isTrue(ObjectUtil.isNull(throwable));
            Util.sleepAndConsoleTime(100);
            return Util.DEFAULT_RESULT;
        });

        //2.阻塞式获取同步结果
        Util.consoleStartWaiting();
        TimeInterval timer = DateUtil.timer();
        Assert.isTrue(Util.DEFAULT_RESULT.equals(sync.get()));
        Util.consoleEndWaitingAndBlockingTime(timer);

        //3.打印分隔线
        Util.consoleDividingLine();

        //4.创建CompletableFuture，设置异步回调
        CompletableFuture<String> async = CompletableFuture.supplyAsync(() -> {
            Util.sleepAndConsoleTime(100);
            return Util.DEFAULT_RESULT;
        }, Util.EXECUTOR).handleAsync((result, throwable) -> {
            Assert.isTrue(Util.DEFAULT_RESULT.equals(result));
            Assert.isTrue(ObjectUtil.isNull(throwable));
            Util.sleepAndConsoleTime(100);
            return Util.DEFAULT_RESULT;
        }, Util.EXECUTOR);

        //5.阻塞式获取异步结果
        Util.consoleStartWaiting();
        timer = DateUtil.timer();
        Assert.isTrue(Util.DEFAULT_RESULT.equals(async.get()));
        Util.consoleEndWaitingAndBlockingTime(timer);

        //6.打印分隔线
        Util.consoleDividingLine();

        //7.创建CompletableFuture，模拟发生异常
        CompletableFuture<Object> withException = CompletableFuture.supplyAsync(() -> {
            Util.sleepAndConsoleTime(100);
            throw new RuntimeException("mock");
        }, Util.EXECUTOR).handleAsync((result, throwable) -> {
            Assert.isNull(result);
            Assert.isTrue(ObjectUtil.isNotNull(throwable));
            Util.sleepAndConsoleTime(100);
            return Util.DEFAULT_RESULT;
        }, Util.EXECUTOR);

        //8.阻塞式获取异步结果
        Util.consoleStartWaiting();
        timer = DateUtil.timer();
        Assert.isTrue(Util.DEFAULT_RESULT.equals(withException.get()));
        Util.consoleEndWaitingAndBlockingTime(timer);
    }
}
