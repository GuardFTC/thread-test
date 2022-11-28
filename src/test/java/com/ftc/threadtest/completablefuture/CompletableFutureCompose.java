package com.ftc.threadtest.completablefuture;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.lang.Assert;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author: 冯铁城 [17615007230@163.com]
 * @date: 2022-11-24 15:43:25
 * @describe: CompletableFuture单元测试-组合处理
 */
public class CompletableFutureCompose {

    @Test
    @SneakyThrows(value = {ExecutionException.class, InterruptedException.class})
    void testRunAfterBoth() {

        //1.创建2个CompletableFuture
        CompletableFuture<String> first = CompletableFuture.supplyAsync(() -> {
            Util.sleepAndConsoleTime(200);
            return Util.DEFAULT_RESULT;
        }, Util.EXECUTOR);
        CompletableFuture<String> second = CompletableFuture.supplyAsync(() -> {
            Util.sleepAndConsoleTime(100);
            return Util.DEFAULT_RESULT;
        }, Util.EXECUTOR);

        //2.串联两个CompletableFuture，同步回调
        CompletableFuture<Void> sync = second.runAfterBoth(first, () -> {
            Util.sleepAndConsoleTime(100);
        });

        //3.验证
        Util.consoleStartWaiting();
        TimeInterval timer = DateUtil.timer();
        Assert.isNull(sync.get());
        Util.consoleEndWaitingAndBlockingTime(timer);

        //4.打印分隔线
        Util.consoleDividingLine();

        //5.创建2个CompletableFuture
        first = CompletableFuture.supplyAsync(() -> {
            Util.sleepAndConsoleTime(100);
            return Util.DEFAULT_RESULT;
        }, Util.EXECUTOR);
        second = CompletableFuture.supplyAsync(() -> {
            Util.sleepAndConsoleTime(200);
            return Util.DEFAULT_RESULT;
        }, Util.EXECUTOR);

        //6.串联两个CompletableFuture，异步回调
        CompletableFuture<Void> async = second.runAfterBothAsync(first, () -> {
            Util.sleepAndConsoleTime(100);
        }, Util.EXECUTOR);

        //7.验证
        Util.consoleStartWaiting();
        timer = DateUtil.timer();
        Assert.isNull(async.get());
        Util.consoleEndWaitingAndBlockingTime(timer);

        //8.打印分隔线
        Util.consoleDividingLine();

        //9.创建2个CompletableFuture
        first = CompletableFuture.supplyAsync(() -> {
            Util.sleepAndConsoleTime(200);
            throw new RuntimeException("mock first");
        }, Util.EXECUTOR);
        second = CompletableFuture.supplyAsync(() -> {
            Util.sleepAndConsoleTime(100);
            throw new RuntimeException("mock second");
        }, Util.EXECUTOR);

        //10.串联两个CompletableFuture,抛出异常
        CompletableFuture<Void> withException = second.runAfterBothAsync(first, () -> {
            Util.sleepAndConsoleTime(100);
        }, Util.EXECUTOR);

        //11.验证
        Util.consoleStartWaiting();
        ExecutionException runtimeException = assertThrows(ExecutionException.class,
                () -> withException.get()
        );
        Assert.isTrue("java.lang.RuntimeException: mock second".equals(runtimeException.getMessage()));
    }

    @Test
    @SneakyThrows(value = {ExecutionException.class, InterruptedException.class})
    void testThenAcceptBoth() {

        //1.创建2个CompletableFuture
        CompletableFuture<String> first = CompletableFuture.supplyAsync(() -> {
            Util.sleepAndConsoleTime(200);
            return Util.DEFAULT_RESULT;
        }, Util.EXECUTOR);
        CompletableFuture<String> second = CompletableFuture.supplyAsync(() -> {
            Util.sleepAndConsoleTime(100);
            return Util.DEFAULT_RESULT;
        }, Util.EXECUTOR);

        //2.串联两个CompletableFuture，同步回调
        CompletableFuture<Void> sync = second.thenAcceptBoth(first, (firstResult, secondResult) -> {
            Assert.isTrue(Util.DEFAULT_RESULT.equals(firstResult));
            Assert.isTrue(Util.DEFAULT_RESULT.equals(secondResult));
            Util.sleepAndConsoleTime(100);
        });

        //3.验证
        Util.consoleStartWaiting();
        TimeInterval timer = DateUtil.timer();
        Assert.isNull(sync.get());
        Util.consoleEndWaitingAndBlockingTime(timer);

        //4.打印分隔线
        Util.consoleDividingLine();

        //5.创建2个CompletableFuture
        first = CompletableFuture.supplyAsync(() -> {
            Util.sleepAndConsoleTime(200);
            return Util.DEFAULT_RESULT;
        }, Util.EXECUTOR);
        second = CompletableFuture.supplyAsync(() -> {
            Util.sleepAndConsoleTime(100);
            return Util.DEFAULT_RESULT;
        }, Util.EXECUTOR);

        //6.串联两个CompletableFuture，异步回调
        CompletableFuture<Void> async = second.thenAcceptBothAsync(first, (firstResult, secondResult) -> {
            Assert.isTrue(Util.DEFAULT_RESULT.equals(firstResult));
            Assert.isTrue(Util.DEFAULT_RESULT.equals(secondResult));
            Util.sleepAndConsoleTime(100);
        }, Util.EXECUTOR);

        //7.验证
        Util.consoleStartWaiting();
        timer = DateUtil.timer();
        Assert.isNull(async.get());
        Util.consoleEndWaitingAndBlockingTime(timer);

        //8.打印分隔线
        Util.consoleDividingLine();

        //9.创建2个CompletableFuture
        first = CompletableFuture.supplyAsync(() -> {
            Util.sleepAndConsoleTime(200);
            throw new RuntimeException("mock first");
        }, Util.EXECUTOR);
        second = CompletableFuture.supplyAsync(() -> {
            Util.sleepAndConsoleTime(100);
            throw new RuntimeException("mock second");
        }, Util.EXECUTOR);

        //10.串联两个CompletableFuture,抛出异常
        CompletableFuture<Void> withException = second.thenAcceptBothAsync(first, (firstResult, secondResult) -> {
            Assert.isTrue(Util.DEFAULT_RESULT.equals(firstResult));
            Assert.isTrue(Util.DEFAULT_RESULT.equals(secondResult));
            Util.sleepAndConsoleTime(100);
        }, Util.EXECUTOR);

        //11.验证
        Util.consoleStartWaiting();
        ExecutionException runtimeException = assertThrows(ExecutionException.class,
                () -> withException.get()
        );
        Assert.isTrue("java.lang.RuntimeException: mock second".equals(runtimeException.getMessage()));
    }

    @Test
    @SneakyThrows(value = {ExecutionException.class, InterruptedException.class})
    void testThenCombine() {

        //1.创建2个CompletableFuture
        CompletableFuture<String> first = CompletableFuture.supplyAsync(() -> {
            Util.sleepAndConsoleTime(200);
            return Util.DEFAULT_RESULT;
        }, Util.EXECUTOR);
        CompletableFuture<String> second = CompletableFuture.supplyAsync(() -> {
            Util.sleepAndConsoleTime(100);
            return Util.DEFAULT_RESULT;
        }, Util.EXECUTOR);

        //2.串联两个CompletableFuture，同步回调
        CompletableFuture<String> sync = second.thenCombine(first, (firstResult, secondResult) -> {
            Assert.isTrue(Util.DEFAULT_RESULT.equals(firstResult));
            Assert.isTrue(Util.DEFAULT_RESULT.equals(secondResult));
            Util.sleepAndConsoleTime(100);
            return Util.DEFAULT_RESULT;
        });

        //3.验证
        Util.consoleStartWaiting();
        TimeInterval timer = DateUtil.timer();
        Assert.isTrue(Util.DEFAULT_RESULT.equals(sync.get()));
        Util.consoleEndWaitingAndBlockingTime(timer);

        //4.打印分隔线
        Util.consoleDividingLine();

        //5.创建2个CompletableFuture
        first = CompletableFuture.supplyAsync(() -> {
            Util.sleepAndConsoleTime(200);
            return Util.DEFAULT_RESULT;
        }, Util.EXECUTOR);
        second = CompletableFuture.supplyAsync(() -> {
            Util.sleepAndConsoleTime(100);
            return Util.DEFAULT_RESULT;
        }, Util.EXECUTOR);

        //6.串联两个CompletableFuture，异步回调
        CompletableFuture<String> async = second.thenCombineAsync(first, (firstResult, secondResult) -> {
            Assert.isTrue(Util.DEFAULT_RESULT.equals(firstResult));
            Assert.isTrue(Util.DEFAULT_RESULT.equals(secondResult));
            Util.sleepAndConsoleTime(100);
            return Util.DEFAULT_RESULT;
        }, Util.EXECUTOR);

        //7.验证
        Util.consoleStartWaiting();
        timer = DateUtil.timer();
        Assert.isTrue(Util.DEFAULT_RESULT.equals(async.get()));
        Util.consoleEndWaitingAndBlockingTime(timer);

        //8.打印分隔线
        Util.consoleDividingLine();

        //9.创建2个CompletableFuture
        first = CompletableFuture.supplyAsync(() -> {
            Util.sleepAndConsoleTime(200);
            throw new RuntimeException("mock first");
        }, Util.EXECUTOR);
        second = CompletableFuture.supplyAsync(() -> {
            Util.sleepAndConsoleTime(100);
            throw new RuntimeException("mock second");
        }, Util.EXECUTOR);

        //10.串联两个CompletableFuture,抛出异常
        CompletableFuture<Object> withException = second.thenCombineAsync(first, (firstResult, secondResult) -> {
            Assert.isTrue(Util.DEFAULT_RESULT.equals(firstResult));
            Assert.isTrue(Util.DEFAULT_RESULT.equals(secondResult));
            Util.sleepAndConsoleTime(100);
            return Util.DEFAULT_RESULT;
        }, Util.EXECUTOR);

        //11.验证
        Util.consoleStartWaiting();
        ExecutionException runtimeException = assertThrows(ExecutionException.class,
                () -> withException.get()
        );
        Assert.isTrue("java.lang.RuntimeException: mock second".equals(runtimeException.getMessage()));
    }

    @Test
    @SneakyThrows(value = {ExecutionException.class, InterruptedException.class})
    void testRunAfterEither() {

        //1.创建2个CompletableFuture
        CompletableFuture<String> first = CompletableFuture.supplyAsync(() -> {
            Util.sleepAndConsoleTime(200);
            return Util.DEFAULT_RESULT;
        }, Util.EXECUTOR);
        CompletableFuture<String> second = CompletableFuture.supplyAsync(() -> {
            Util.sleepAndConsoleTime(100);
            return Util.DEFAULT_RESULT;
        }, Util.EXECUTOR);

        //2.串联两个CompletableFuture，同步回调
        CompletableFuture<Void> sync = second.runAfterEither(first, () -> {
            Util.sleepAndConsoleTime(100);
        });

        //3.验证
        Util.consoleStartWaiting();
        TimeInterval timer = DateUtil.timer();
        Assert.isNull(sync.get());
        Util.consoleEndWaitingAndBlockingTime(timer);

        //4.打印分隔线
        Util.consoleDividingLine();

        //5.创建2个CompletableFuture
        first = CompletableFuture.supplyAsync(() -> {
            Util.sleepAndConsoleTime(100);
            return Util.DEFAULT_RESULT;
        }, Util.EXECUTOR);
        second = CompletableFuture.supplyAsync(() -> {
            Util.sleepAndConsoleTime(200);
            return Util.DEFAULT_RESULT;
        }, Util.EXECUTOR);

        //6.串联两个CompletableFuture，异步回调
        CompletableFuture<Void> async = second.runAfterEitherAsync(first, () -> {
            Util.sleepAndConsoleTime(100);
        }, Util.EXECUTOR);

        //7.验证
        Util.consoleStartWaiting();
        timer = DateUtil.timer();
        Assert.isNull(async.get());
        Util.consoleEndWaitingAndBlockingTime(timer);

        //8.打印分隔线
        Util.consoleDividingLine();

        //9.创建2个CompletableFuture
        first = CompletableFuture.supplyAsync(() -> {
            Util.sleepAndConsoleTime(200);
            throw new RuntimeException("mock first");
        }, Util.EXECUTOR);
        second = CompletableFuture.supplyAsync(() -> {
            Util.sleepAndConsoleTime(100);
            throw new RuntimeException("mock second");
        }, Util.EXECUTOR);

        //10.串联两个CompletableFuture,抛出异常
        CompletableFuture<Void> withException = second.runAfterEitherAsync(first, () -> {
            Util.sleepAndConsoleTime(100);
        }, Util.EXECUTOR);

        //11.验证
        Util.consoleStartWaiting();
        ExecutionException runtimeException = assertThrows(ExecutionException.class,
                () -> withException.get()
        );
        Assert.isTrue("java.lang.RuntimeException: mock second".equals(runtimeException.getMessage()));
    }

    @Test
    @SneakyThrows(value = {ExecutionException.class, InterruptedException.class})
    void testAcceptEither() {

        //1.创建2个CompletableFuture
        CompletableFuture<String> first = CompletableFuture.supplyAsync(() -> {
            Util.sleepAndConsoleTime(200);
            return Util.DEFAULT_RESULT;
        }, Util.EXECUTOR);
        CompletableFuture<String> second = CompletableFuture.supplyAsync(() -> {
            Util.sleepAndConsoleTime(100);
            return Util.DEFAULT_RESULT;
        }, Util.EXECUTOR);

        //2.串联两个CompletableFuture，同步回调
        CompletableFuture<Void> sync = second.acceptEither(first, (result) -> {
            Assert.isTrue(Util.DEFAULT_RESULT.equals(result));
            Util.sleepAndConsoleTime(100);
        });

        //3.验证
        Util.consoleStartWaiting();
        TimeInterval timer = DateUtil.timer();
        Assert.isNull(sync.get());
        Util.consoleEndWaitingAndBlockingTime(timer);

        //4.打印分隔线
        Util.consoleDividingLine();

        //5.创建2个CompletableFuture
        first = CompletableFuture.supplyAsync(() -> {
            Util.sleepAndConsoleTime(100);
            return Util.DEFAULT_RESULT;
        }, Util.EXECUTOR);
        second = CompletableFuture.supplyAsync(() -> {
            Util.sleepAndConsoleTime(200);
            return Util.DEFAULT_RESULT;
        }, Util.EXECUTOR);

        //6.串联两个CompletableFuture，异步回调
        CompletableFuture<Void> async = second.acceptEitherAsync(first, (result) -> {
            Assert.isTrue(Util.DEFAULT_RESULT.equals(result));
            Util.sleepAndConsoleTime(100);
        }, Util.EXECUTOR);

        //7.验证
        Util.consoleStartWaiting();
        timer = DateUtil.timer();
        Assert.isNull(async.get());
        Util.consoleEndWaitingAndBlockingTime(timer);

        //8.打印分隔线
        Util.consoleDividingLine();

        //9.创建2个CompletableFuture
        first = CompletableFuture.supplyAsync(() -> {
            Util.sleepAndConsoleTime(200);
            throw new RuntimeException("mock first");
        }, Util.EXECUTOR);
        second = CompletableFuture.supplyAsync(() -> {
            Util.sleepAndConsoleTime(100);
            throw new RuntimeException("mock second");
        }, Util.EXECUTOR);

        //10.串联两个CompletableFuture,抛出异常
        CompletableFuture<Void> withException = second.acceptEitherAsync(first, (result) -> {
            Assert.isTrue(Util.DEFAULT_RESULT.equals(result));
            Util.sleepAndConsoleTime(100);
        }, Util.EXECUTOR);

        //11.验证
        Util.consoleStartWaiting();
        ExecutionException runtimeException = assertThrows(ExecutionException.class,
                () -> withException.get()
        );
        Assert.isTrue("java.lang.RuntimeException: mock second".equals(runtimeException.getMessage()));
    }

    @Test
    @SneakyThrows(value = {ExecutionException.class, InterruptedException.class})
    void testApplyToEither() {

        //1.创建2个CompletableFuture
        CompletableFuture<String> first = CompletableFuture.supplyAsync(() -> {
            Util.sleepAndConsoleTime(200);
            return Util.DEFAULT_RESULT;
        }, Util.EXECUTOR);
        CompletableFuture<String> second = CompletableFuture.supplyAsync(() -> {
            Util.sleepAndConsoleTime(100);
            return Util.DEFAULT_RESULT;
        }, Util.EXECUTOR);

        //2.串联两个CompletableFuture，同步回调
        CompletableFuture<String> sync = second.applyToEither(first, (result) -> {
            Assert.isTrue(Util.DEFAULT_RESULT.equals(result));
            Util.sleepAndConsoleTime(100);
            return Util.DEFAULT_RESULT;
        });

        //3.验证
        Util.consoleStartWaiting();
        TimeInterval timer = DateUtil.timer();
        Assert.isTrue(Util.DEFAULT_RESULT.equals(sync.get()));
        Util.consoleEndWaitingAndBlockingTime(timer);

        //4.打印分隔线
        Util.consoleDividingLine();

        //5.创建2个CompletableFuture
        first = CompletableFuture.supplyAsync(() -> {
            Util.sleepAndConsoleTime(100);
            return Util.DEFAULT_RESULT;
        }, Util.EXECUTOR);
        second = CompletableFuture.supplyAsync(() -> {
            Util.sleepAndConsoleTime(200);
            return Util.DEFAULT_RESULT;
        }, Util.EXECUTOR);

        //6.串联两个CompletableFuture，异步回调
        CompletableFuture<String> async = second.applyToEitherAsync(first, (result) -> {
            Assert.isTrue(Util.DEFAULT_RESULT.equals(result));
            Util.sleepAndConsoleTime(100);
            return Util.DEFAULT_RESULT;
        }, Util.EXECUTOR);

        //7.验证
        Util.consoleStartWaiting();
        timer = DateUtil.timer();
        Assert.isTrue(Util.DEFAULT_RESULT.equals(async.get()));
        Util.consoleEndWaitingAndBlockingTime(timer);

        //8.打印分隔线
        Util.consoleDividingLine();

        //9.创建2个CompletableFuture
        first = CompletableFuture.supplyAsync(() -> {
            Util.sleepAndConsoleTime(200);
            throw new RuntimeException("mock first");
        }, Util.EXECUTOR);
        second = CompletableFuture.supplyAsync(() -> {
            Util.sleepAndConsoleTime(100);
            throw new RuntimeException("mock second");
        }, Util.EXECUTOR);

        //10.串联两个CompletableFuture,抛出异常
        CompletableFuture<String> withException = second.applyToEitherAsync(first, (result) -> {
            Assert.isTrue(Util.DEFAULT_RESULT.equals(result));
            Util.sleepAndConsoleTime(100);
            return Util.DEFAULT_RESULT;
        }, Util.EXECUTOR);

        //11.验证
        Util.consoleStartWaiting();
        ExecutionException runtimeException = assertThrows(ExecutionException.class,
                () -> withException.get()
        );
        Assert.isTrue("java.lang.RuntimeException: mock second".equals(runtimeException.getMessage()));
    }

    @Test
    void testAllOfV1() {

        //1.创建CompletableFuture集合
        List<CompletableFuture<Void>> completableFutures = CollUtil.newArrayList();

        //2.循环
        for (int i = 0; i < 10; i++) {
            completableFutures.add(CompletableFuture.runAsync(() -> {
                List<Integer> list = Util.getList();
                Util.addList(list);
            }));
        }

        //3.allOff所有CompletableFuture
        CompletableFuture<Void> objectCompletableFuture = CompletableFuture
                .allOf(completableFutures.toArray(new CompletableFuture[completableFutures.size()]));

        //4.阻塞所有CompletableFuture
        objectCompletableFuture.join();

        //5.校验阻塞后的集合大小
        Assert.isTrue(1000 == Util.RESULT_LIST.size());
    }

    @Test
    void testAllOfV2() {

        //1.创建CompletableFuture集合
        List<CompletableFuture<List<Integer>>> completableFutures = CollUtil.newArrayList();

        //2.循环
        for (int i = 0; i < 10; i++) {
            completableFutures.add(CompletableFuture.supplyAsync(Util::getList));
        }

        //3.allOff所有CompletableFuture,并在
        CompletableFuture<List<Integer>> objectCompletableFuture = CompletableFuture
                .allOf(completableFutures.toArray(new CompletableFuture[completableFutures.size()]))
                .thenApplyAsync((value) -> {

                    //4.循环阻塞取值
                    List<List<Integer>> collect = completableFutures.stream()
                            .map(CompletableFuture::join)
                            .collect(Collectors.toList());

                    //5.存入ResultList
                    for (List<Integer> list : collect) {
                        Util.addList(list);
                    }

                    //6.返回
                    return Util.RESULT_LIST;
                }, Util.EXECUTOR);

        //7.阻塞所有CompletableFuture
        List<Integer> result = objectCompletableFuture.join();

        //8.校验阻塞后的集合大小
        Assert.isTrue(1000 == result.size());
    }

    @Test
    void testAnyOf() {

        //1.创建3个CompletableFuture
        CompletableFuture<Integer> first = CompletableFuture.supplyAsync(() -> {
            Util.sleepAndConsoleTime(100);
            return 1;
        }, Util.EXECUTOR);
        CompletableFuture<Integer> second = CompletableFuture.supplyAsync(() -> {
            Util.sleepAndConsoleTime(200);
            return 2;
        }, Util.EXECUTOR);
        CompletableFuture<Integer> third = CompletableFuture.supplyAsync(() -> {
            Util.sleepAndConsoleTime(300);
            return 3;
        }, Util.EXECUTOR);

        //2.anyOff所有CompletableFuture
        CompletableFuture<Object> anyOf = CompletableFuture.anyOf(first, second, third);

        //3.阻塞所有CompletableFuture
        Object join = anyOf.join();

        //4.校验阻塞后的集合大小
        Assert.isTrue(1 == Convert.toInt(join));
    }
}
