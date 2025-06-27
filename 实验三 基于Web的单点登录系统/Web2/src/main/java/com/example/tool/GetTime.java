package com.example.tool;

import java.util.Calendar;

public class GetTime {
    public static String get() {
        // 获取当前日历实例
        Calendar calendar = Calendar.getInstance();
        // 获取各个字段
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1; // 月份从0开始
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);

        System.out.printf("获取当前时间: %d-%02d-%02d %02d:%02d:%02d%n", year, month, day, hour, minute, second);
        return String.format("%d-%02d-%02d %02d:%02d:%02d", year, month, day, hour, minute, second);
    }
}