package com.ftc.threadtest.completablefuture;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.RandomUtil;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
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
        CompletableFuture<Void> sync = second.thenAcceptBoth(first, (secondResult, firstResult) -> {
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
        CompletableFuture<Void> async = second.thenAcceptBothAsync(first, (secondResult, firstResult) -> {
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
        CompletableFuture<Void> withException = second.thenAcceptBothAsync(first, (secondResult, firstResult) -> {
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
        CompletableFuture<String> sync = second.thenCombine(first, (secondResult, firstResult) -> {
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
        CompletableFuture<String> async = second.thenCombineAsync(first, (secondResult, firstResult) -> {
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
        CompletableFuture<Object> withException = second.thenCombineAsync(first, (secondResult, firstResult) -> {
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
            Util.sleepAndConsoleTime(200);
            return Util.DEFAULT_RESULT;
        }, Util.EXECUTOR);
        second = CompletableFuture.supplyAsync(() -> {
            Util.sleepAndConsoleTime(100);
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
            Util.sleepAndConsoleTime(200);
            return Util.DEFAULT_RESULT;
        }, Util.EXECUTOR);
        second = CompletableFuture.supplyAsync(() -> {
            Util.sleepAndConsoleTime(100);
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
            Util.sleepAndConsoleTime(200);
            return Util.DEFAULT_RESULT;
        }, Util.EXECUTOR);
        second = CompletableFuture.supplyAsync(() -> {
            Util.sleepAndConsoleTime(100);
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

        //2.循环创建10个异步任务
        for (int i = 0; i < 10; i++) {
            completableFutures.add(CompletableFuture.runAsync(() -> {

                //3.拉取数据
                List<Integer> list = Util.getList();

                //4.存入总结果集合
                Util.addList(list);
            }));
        }

        //5.使用allOf管理所有异步任务
        CompletableFuture<Void> objectCompletableFuture = CompletableFuture
                .allOf(completableFutures.toArray(new CompletableFuture[0]));

        //6.阻塞所有CompletableFuture
        objectCompletableFuture.join();

        //7.校验阻塞后的总结果集合大小
        Assert.isTrue(1000 == Util.RESULT_LIST.size());
    }

    @Test
    @SneakyThrows({InterruptedException.class, ExecutionException.class})
    void testAllOfV2() {

        //1.创建CompletableFuture集合
        List<CompletableFuture<List<Integer>>> completableFutures = CollUtil.newArrayList();

        //2.循环创建10个异步任务,
        for (int i = 0; i < 10; i++) {
            completableFutures.add(CompletableFuture.supplyAsync(Util::getList));
        }

        //3.使用allOf管理所有异步任务
        CompletableFuture<List<Integer>> objectCompletableFuture = CompletableFuture
                .allOf(completableFutures.toArray(new CompletableFuture[0]))
                .thenApplyAsync((value) -> {

                    //4.调用所有异步任务的get方法，获取数据
                    List<List<Integer>> collect = completableFutures.stream()
                            .map(CompletableFuture::join)
                            .collect(Collectors.toList());

                    //5.统一存入总结果集合
                    for (List<Integer> list : collect) {
                        Util.addList(list);
                    }

                    //6.返回
                    return Util.RESULT_LIST;
                }, Util.EXECUTOR);

        //7.阻塞获取总结果集合
        List<Integer> result = objectCompletableFuture.get();

        //8.校验阻塞后的总结果集合大小
        Assert.isTrue(1000 == result.size());
    }

    /**
     * 判定是否有精子进入卵细胞
     */
    boolean join = false;

    @Test
    void testAnyOf() {

        //1.创建CompletableFuture集合
        List<CompletableFuture<Void>> completableFutures = CollUtil.newArrayList();

        //2.循环创建16个异步任务，即16个精子
        for (int i = 0; i < 16; i++) {

            //3.创建异步任务-无返回值
            CompletableFuture<Void> runAsync = CompletableFuture.runAsync(() -> {

                //4.随机速度，只有速度超过一定值，才打破循环
                for (; ; ) {
                    int speed = RandomUtil.randomInt(1, 100);
                    if (speed > 95) {
                        break;
                    }
                }

                //5.进入卵细胞
                synchronized (this) {
                    if (!join) {
                        System.out.println(Thread.currentThread().getName() + " 进入卵细胞！！！");
                        join = true;
                    } else {
                        System.out.println(Thread.currentThread().getName() + " 妈的来晚了！");
                    }
                }
            }, Util.EXECUTOR);

            //6.存入集合
            completableFutures.add(runAsync);
        }

        //7.anyOf管理所有异步任务
        CompletableFuture<Void> anyOf = CompletableFuture
                .anyOf(completableFutures.toArray(new CompletableFuture[0]))
                .thenRun(() -> System.out.println(Thread.currentThread().getName() + " 受精卵已生成!"));

        //8.anyOf阻塞等待受精成功
        anyOf.join();
    }

    @Test
    @SneakyThrows
    void testAnyOfPlay() {

        //1.创建循环栅栏阻塞
        CyclicBarrier cyclicBarrier = new CyclicBarrier(10, () -> {
            System.out.println("比赛开始");
        });

        //2.创建10个异步任务，代表十个人
        List<CompletableFuture<Void>> peoples = CollUtil.newArrayList();
        for (int i = 0; i < 10; i++) {

            //3.创建异步任务，一个人要做什么
            CompletableFuture<Void> people = CompletableFuture.runAsync(() -> {

                //4.走到起跑线
                try {
                    System.out.println(Thread.currentThread().getName() + " 已到达比赛场地");
                    cyclicBarrier.await();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                //5.随机睡觉
                int sleepTime = RandomUtil.randomInt(0, 10);
                System.out.println(Thread.currentThread().getName() + " sleep " + sleepTime + " seconds");
                try {
                    TimeUnit.SECONDS.sleep(sleepTime);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(Thread.currentThread().getName() + "睡醒了！");
            }, Util.EXECUTOR);

            //6.异步任务存入集合
            peoples.add(people);
        }

        //7.阻塞10个人，谁先睡醒谁拿到奖
        CompletableFuture<Void> anyOf = CompletableFuture.anyOf(peoples.toArray(new CompletableFuture[10])).thenRun(() -> {
            System.out.println(Thread.currentThread().getName() + " is win!!!");
        });

        //8.阻塞，看看谁能赢
        anyOf.join();
    }

    /**
     * 最终前几名可以获得胜利
     */
    int winPeopleNum = 3;

    @Test
    @SneakyThrows
    void testAnyOfPlayV2() {

        //1.创建循环栅栏阻塞
        CyclicBarrier cyclicBarrier = new CyclicBarrier(10, () -> {
            System.out.println("比赛开始");
        });

        //2.创建10个异步任务，代表十个人
        List<CompletableFuture<Void>> peoples = CollUtil.newArrayList();
        for (int i = 0; i < 10; i++) {

            //3.创建异步任务，一个人要做什么
            CompletableFuture<Void> people = CompletableFuture.runAsync(() -> {

                //4.走到起跑线
                try {
                    cyclicBarrier.await();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }, Util.EXECUTOR);

            //5.比赛开始
            people = people.thenRun(() -> {
                synchronized (this) {
                    if (winPeopleNum > 0) {
                        System.out.println(Thread.currentThread().getName() + " is win.Position is " + (4 - winPeopleNum));
                        winPeopleNum--;
                    } else {
                        System.out.println(Thread.currentThread().getName() + " is lose");
                    }
                }
            });

            //6.存入集合
            peoples.add(people);
        }

        //7.统一管理10个人
        CompletableFuture<Void> allOf = CompletableFuture.allOf(peoples.toArray(new CompletableFuture[10])).thenRun(() -> {
            System.out.println("比赛结束!");
        });

        //8.阻塞，看看谁能赢
        allOf.join();
    }
}
