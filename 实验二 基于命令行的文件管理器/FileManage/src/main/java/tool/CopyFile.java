package tool;
import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

//拷贝文件，传入源地址和目的地址
public class CopyFile {
    public static void copy(String sourcePath,String destinationPath,int back) {

        try {
            long startTime = System.currentTimeMillis(); // 记录开始时间

            Path source = Paths.get(sourcePath);
            Path destination = Paths.get(destinationPath);

            if (Files.isDirectory(source)) {
                // 如果是目录，则拷贝整个文件夹
                long totalBytes = calculateTotalBytes(source); // 计算总字节数
                long[] copiedBytes = {0}; // 使用数组来存储已拷贝的字节数
                copyDirectory(source, destination, copiedBytes, totalBytes, startTime,back);
                if(back==0)System.out.println("\n拷贝完成！总字节数：" + copiedBytes[0]);
            } else if (Files.isRegularFile(source)) {
                // 如果是文件，则直接拷贝单个文件
                long totalBytes = Files.size(source);
                long[] copiedBytes = {copyFile(source, destination, totalBytes, startTime,back)};
                if(back==0)System.out.println("\n拷贝完成！总字节数：" + copiedBytes[0]);
            } else {
                WarnOut.print("源路径既不是文件也不是目录。");
            }

            long endTime = System.currentTimeMillis(); // 记录结束时间
            if(back==0)System.out.println("总耗时: " + (endTime - startTime) + " 毫秒");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 计算源目录的总字节数
    private static long calculateTotalBytes(Path sourceDir) throws IOException {
        TotalBytesVisitor visitor = new TotalBytesVisitor();
        Files.walkFileTree(sourceDir, visitor);
        return visitor.getTotalBytes();
    }

    // 拷贝整个目录
    private static void copyDirectory(Path source, Path destination, long[] copiedBytes, long totalBytes, long startTime,int back) throws IOException {
        Files.walkFileTree(source, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                // 拷贝文件
                Path destFilePath = destination.resolve(source.relativize(file));
                Files.createDirectories(destFilePath.getParent()); // 创建目标文件的父目录

                // 使用字节流逐块拷贝文件
                try (InputStream in = Files.newInputStream(file);
                     OutputStream out = Files.newOutputStream(destFilePath)) {

                    byte[] buffer = new byte[4096]; // 定义缓冲区大小
                    int bytesRead;
                    while ((bytesRead = in.read(buffer)) != -1) {
                        out.write(buffer, 0, bytesRead);
                        copiedBytes[0] += bytesRead; // 更新已拷贝字节数

                        // 更新进度显示
                        updateProgress(copiedBytes[0], totalBytes, startTime,back);
                    }
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                // 创建目标目录
                Path newDir = destination.resolve(source.relativize(dir));
                Files.createDirectories(newDir);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    // 拷贝单个文件
    private static long copyFile(Path sourceFile, Path destinationFile, long totalBytes, long startTime,int back) throws IOException {
        long copiedBytes = 0;

        // 拷贝文件
        try (InputStream in = Files.newInputStream(sourceFile);
             OutputStream out = Files.newOutputStream(destinationFile)) {

            byte[] buffer = new byte[4096]; // 定义缓冲区大小
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
                copiedBytes += bytesRead;

                // 更新进度显示
                updateProgress(copiedBytes, totalBytes, startTime,back);
            }
        }
        return copiedBytes;
    }

    // 更新进度显示
    private static void updateProgress(long copiedBytes, long totalBytes, long startTime,int back) {
        double progress = (double) copiedBytes / totalBytes * 100; // 进度百分比

        // 计算已用时间
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - startTime; // 计算经过的时间（毫秒）

        // 将耗时转换为秒和毫秒
        long seconds = elapsedTime / 1000;
        long milliseconds = elapsedTime % 1000;

        // 更新进度显示
        if(back==0)System.out.printf("\r已拷贝：%d/%d 字节 (%.2f%%)，耗时：%02d:%02d.%03d",
                copiedBytes, totalBytes, progress, seconds / 60, seconds % 60, milliseconds);
    }

    // 自定义访问器以计算总字节数
    private static class TotalBytesVisitor extends SimpleFileVisitor<Path> {
        private long totalBytes = 0;

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            totalBytes += attrs.size();
            return FileVisitResult.CONTINUE;
        }

        public long getTotalBytes() {
            return totalBytes;
        }
    }
}