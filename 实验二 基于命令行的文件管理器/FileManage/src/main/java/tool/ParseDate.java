package tool;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ParseDate {
    // 将输入字符串转换成毫秒
    public static long parseDateTimeToMillis(String input) {
        LocalDateTime dateTime = parseDateTime(input);
        return dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
    // 定义支持的日期时间格式
    private static final DateTimeFormatter[] SUPPORTED_FORMATS = {
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"), // 包含秒
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"),    // 仅到分钟
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH"),           // 仅小时
            DateTimeFormatter.ofPattern("yyyy-MM-dd"),           // 仅日期
    };

    // 方法：判断输入的日期时间字符串并返回 LocalDateTime
    public static LocalDateTime parseDateTime(String input) {
        for (DateTimeFormatter formatter : SUPPORTED_FORMATS) {
            try {
                if(!input.contains(" "))input=input+" 00";
                return LocalDateTime.parse(input, formatter);
            } catch (DateTimeParseException e) {
                // 捕获解析异常，继续下一个格式
            }
        }
        // 如果没有匹配的格式，抛出异常
        throw new IllegalArgumentException("Input does not match any supported date format.");
    }
}
