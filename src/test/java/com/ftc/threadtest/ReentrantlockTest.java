package com.ftc.threadtest;

import com.ftc.threadtest.lock.ReentrantlockTestEntity;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author: 冯铁城 [17615007230@163.com]
 * @date: 2022-11-10 16:46:37
 * @describe: Reentrantlock单元测试
 */
public class ReentrantlockTest {

    @Test
    @SneakyThrows(InterruptedException.class)
    void testLockAndUnlock() {
        ReentrantlockTestEntity reentrantlockTestEntity = new ReentrantlockTestEntity();

        Thread thread1 = new Thread(reentrantlockTestEntity::consoleInfo);
        Thread thread2 = new Thread(reentrantlockTestEntity::consoleInfo);

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
    }

    @Test
    @SneakyThrows(InterruptedException.class)
    void testTryLock() {
        ReentrantlockTestEntity reentrantlockTestEntity = new ReentrantlockTestEntity();

        Thread thread1 = new Thread(reentrantlockTestEntity::consoleInfo);
        Thread thread2 = new Thread(reentrantlockTestEntity::consoleIsLock);
        Thread thread3 = new Thread(reentrantlockTestEntity::consoleIsLockTimeLimit);

        thread1.start();
        thread2.start();
        thread3.start();

        thread1.join();
        thread2.join();
        thread3.join();
    }

    @Test
    @SneakyThrows(InterruptedException.class)
    void testLockInterrupt() {
        ReentrantlockTestEntity reentrantlockTestEntity = new ReentrantlockTestEntity();

        Thread thread1 = new Thread(reentrantlockTestEntity::consoleInfoInterrupt);
        Thread thread2 = new Thread(reentrantlockTestEntity::consoleInfoInterrupt);

        thread1.start();
        thread2.start();

        thread2.interrupt();
        TimeUnit.SECONDS.sleep(3);
    }

    @Test
    @SneakyThrows(InterruptedException.class)
    void testGetHoldCountAndGetQueueLength() {
        ReentrantlockTestEntity reentrantlockTestEntity = new ReentrantlockTestEntity();

        Thread thread1 = new Thread(reentrantlockTestEntity::consoleInfoV2);
        Thread thread2 = new Thread(reentrantlockTestEntity::consoleInfoV2);
        Thread thread3 = new Thread(reentrantlockTestEntity::consoleInfoV2);

        thread1.start();
        thread2.start();
        thread3.start();

        thread1.join();
        thread2.join();
        thread3.join();
    }

    @Test
    @SneakyThrows(InterruptedException.class)
    void testHasQueuedThreads() {
        ReentrantlockTestEntity reentrantlockTestEntity = new ReentrantlockTestEntity();

        Thread thread1 = new Thread(reentrantlockTestEntity::consoleInfoV4);
        Thread thread2 = new Thread(reentrantlockTestEntity::consoleInfoV4);

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
    }

    @Test
    void testIsFair() {
        System.out.println(ReentrantlockTestEntity.CLASS_LOCK.isFair());
    }
}
