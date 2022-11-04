package com.ftc.threadtest.executor;

import cn.hutool.log.StaticLog;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;

/**
 * @author: 冯铁城 [17615007230@163.com]
 * @date: 2022-11-04 14:55:20
 * @describe: 线程池任务运行异常配置
 */
@Configuration
public class ExecutorErrorHandler implements AsyncConfigurer {

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (throwable, method, params) ->
                StaticLog.error(
                        "[Async method:[{}] run with error:[{}]",
                        method.getName(), throwable.getMessage()
                );
    }
}
