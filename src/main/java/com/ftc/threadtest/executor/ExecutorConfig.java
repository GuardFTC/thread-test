package com.ftc.threadtest.executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author: 冯铁城 [17615007230@163.com]
 * @date: 2022-11-04 14:55:20
 * @describe: 线程池配置
 */
@EnableAsync
@Configuration
public class ExecutorConfig {

    @Bean("customExecutor")
    public Executor customExecutor() {

        //1.定义线程池
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        //2.定义核心线程数
        int cpuCount = Runtime.getRuntime().availableProcessors();
        executor.setCorePoolSize(cpuCount);

        //3.最大线程数
        executor.setMaxPoolSize(cpuCount << 1);

        //4.设置额外线程存活时间
        executor.setKeepAliveSeconds(60);

        //5.队列大小
        executor.setQueueCapacity(1024);

        //6.线程池中的线程名前缀
        executor.setThreadNamePrefix("custom-");

        //7.拒绝策略:异常抛出策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());

        //8.设置线程池shutdown时,等待当前被调度的任务完成,默认为false
        executor.setWaitForTasksToCompleteOnShutdown(true);

        //9.初始化线程池
        executor.initialize();

        //10.返回线程池
        return executor;
    }

    @Bean("customScheduledExecutor")
    public Executor customScheduledExecutor() {

        //1.定义线程池
        ThreadPoolTaskScheduler executor = new ThreadPoolTaskScheduler();

        //2.设置线程池大小
        int cpuCount = Runtime.getRuntime().availableProcessors();
        executor.setPoolSize(cpuCount);

        //3.线程池中的线程名前缀
        executor.setThreadNamePrefix("custom-scheduled-");

        //4.设置拒绝策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());

        //5.设置线程池shutdown时,等待当前被调度的任务完成,默认为false
        executor.setWaitForTasksToCompleteOnShutdown(true);

        //6.初始化线程池
        executor.initialize();

        //7.返回线程池
        return executor;
    }
}
