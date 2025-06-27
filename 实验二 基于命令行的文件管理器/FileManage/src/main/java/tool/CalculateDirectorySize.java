package tool;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class CalculateDirectorySize {
    public static long calculate(Path directory) throws IOException {
        long size = 0;
        // 使用 Files.walkFileTree 遍历目录树
        size = Files.walk(directory)
                .filter(Files::isRegularFile) // 仅计入常规文件
                .mapToLong(path -> {
                    try {
                        return Files.size(path); // 获取文件大小
                    } catch (IOException e) {
                        System.err.println("无法获取文件大小: " + e.getMessage());
                        return 0L; // 如果出错，返回0
                    }
                })
                .sum(); // 计算总和

        return size;
    }
}
