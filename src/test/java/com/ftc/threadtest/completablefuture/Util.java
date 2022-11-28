package com.ftc.threadtest.completablefuture;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.util.StrUtil;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * @author: 冯铁城 [17615007230@163.com]
 * @date: 2022-11-28 09:52:17
 * @describe: CompletableFuture测试相关工具类
 */
public class Util {

    /**
     * 定义线程池
     */
    private final static ThreadFactory THREAD_FACTORY = new ThreadFactoryBuilder().setNameFormat("custom-%d").build();
    public final static ExecutorService EXECUTOR = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(), THREAD_FACTORY);

    /**
     * 各种输出模板
     */
    private final static String START_TIME_TEMPLATE = "[{}] start running time:{}";
    private final static String END_TIME_TEMPLATE = "[{}] end running time:{}";
    private final static String START_BLOCKING_TEMPLATE = "[{}] start blocking time:{}";
    private final static String END_BLOCKING_TEMPLATE = "[{}] end blocking time:{}";
    private final static String BLOCKING_TEMPLATE = "[{}] blocking time is:{}";

    /**
     * 默认返回结果
     */
    public final static String DEFAULT_RESULT = "ending";

    /**
     * 最终结果集
     */
    public static List<Integer> RESULT_LIST = CollUtil.newArrayList();

    /**
     * 睡{time}ms,并控制台打印相关信息
     *
     * @param time 睡眠时间/ms
     */
    public static void sleepAndConsoleTime(int time) {

        //1.打印开始时间
        System.out.println(StrUtil.format(
                START_TIME_TEMPLATE, Thread.currentThread().getName(), DateUtil.format(new Date(), "HH:mm:ss:SSS")
        ));

        //2.睡一会
        try {
            TimeUnit.MILLISECONDS.sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        //3.打印开始时间
        System.out.println(StrUtil.format(
                END_TIME_TEMPLATE, Thread.currentThread().getName(), DateUtil.format(new Date(), "HH:mm:ss:SSS")
        ));
    }

    /**
     * 控制台打印开始阻塞信息
     */
    public static void consoleStartWaiting() {
        System.out.println(StrUtil.format(
                START_BLOCKING_TEMPLATE, Thread.currentThread().getName(), DateUtil.format(new Date(), "HH:mm:ss:SSS")
        ));
    }

    /**
     * 控制台打印结束阻塞信息
     *
     * @param timer 计时器
     */
    public static void consoleEndWaitingAndBlockingTime(TimeInterval timer) {
        System.out.println(StrUtil.format(
                END_BLOCKING_TEMPLATE, Thread.currentThread().getName(), DateUtil.format(new Date(), "HH:mm:ss:SSS")
        ));
        System.out.println(StrUtil.format(
                BLOCKING_TEMPLATE, Thread.currentThread().getName(), timer.interval()
        ));
    }

    /**
     * 打印分割线
     */
    public static void consoleDividingLine() {
        System.out.println("----------------------------------------------------------------------");
    }

    /**
     * 获取集合
     *
     * @return 集合-item个数100
     */
    public static List<Integer> getList() {
        List<Integer> list = CollUtil.newArrayList();
        for (int i = 0; i < 100; i++) {
            list.add(i);
        }
        return list;
    }

    /**
     * 存入结果集合
     *
     * @param list 集合-item个数100
     */
    public synchronized static void addList(List<Integer> list) {
        RESULT_LIST.addAll(list);
    }
}
