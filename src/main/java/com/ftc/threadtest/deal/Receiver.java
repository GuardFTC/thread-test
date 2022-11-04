package com.ftc.threadtest.deal;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

/**
 * @author: 冯铁城 [17615007230@163.com]
 * @date: 2022-11-02 18:40:28
 * @describe: 接收器类
 */
@AllArgsConstructor
public class Receiver implements Runnable {

    /**
     * People
     */
    private final People people;

    /**
     * 指令
     */
    private String instruction;

    /**
     * 指令集
     */
    public static final String PLAY_PHONE = "玩手机";
    public static final String BRUSH_TEETH = "刷牙";
    public static final String SLEEP = "睡觉";

    @Override
    @SneakyThrows
    public void run() {
        if (PLAY_PHONE.equals(instruction)) {
            people.playPhone();
        } else if (BRUSH_TEETH.equals(instruction)) {
            people.brushTeeth();
        } else if (SLEEP.equals(instruction)) {
            people.sleep();
        }
    }
}
