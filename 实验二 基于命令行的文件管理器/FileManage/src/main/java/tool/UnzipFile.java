package tool;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class UnzipFile {
    /**
     * 解压缩 zip 文件
     *
     * @param zipFilePath zip 文件路径
     * @param destDirPath 解压缩目标目录
     * @throws IOException 如果发生 I/O 异常
     */
    private static long totalSize = 0; // 总大小
    private static long decompressedSize = 0; // 已解压缩大小
    private static long startTime; // 开始时间

    public static void unzip(String zipFilePath, String destDirPath, int back) throws IOException {
        // 创建目标目录
        File destDir = new File(destDirPath);
        if (!destDir.exists()) {
            destDir.mkdirs(); // 如果目标目录不存在，则创建
        }

        // 初始化压缩文件的总大小
        totalSize = getZipFileSize(zipFilePath);
        decompressedSize = 0; // 初始化已解压缩大小
        startTime = System.currentTimeMillis(); // 记录开始时间

        // 创建 zip 输入流
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFilePath))) {
            ZipEntry zipEntry;

            // 循环处理 zip 文件中的每个条目
            while ((zipEntry = zis.getNextEntry()) != null) {
                // 直接获取文件名和路径
                String fileName = zipEntry.getName();
                File newFile = new File(destDir, fileName);

                // 处理目录结构
                if (zipEntry.isDirectory()) {
                    // 如果是目录，则直接创建
                    newFile.mkdirs();
                } else {
                    // 确保父目录存在
                    new File(newFile.getParent()).mkdirs();
                    // 将文件内容写入目标文件
                    try (FileOutputStream fos = new FileOutputStream(newFile)) {
                        byte[] buffer = new byte[1024];
                        int length;
                        while ((length = zis.read(buffer)) > 0) {
                            fos.write(buffer, 0, length);
                            decompressedSize += length; // 更新已解压缩大小

                            // 只有当 back 为 0 的时候才打印进度
                            if (back == 0) {
                                printProgress(); // 打印进度
                            }
                        }

                    }
                }
                zis.closeEntry(); // 关闭当前条目
            }
        }
        if (back == 0) {
            decompressedSize = totalSize;
            printProgress(); // 打印100%的进度
        }
        if (back == 0)System.out.println(); // 完成后换行
    }

    // 打印解压缩进度与已花费时间
    private static void printProgress() {
        int progressPercentage = (int) ((decompressedSize * 100) / totalSize);
        long elapsedTime = System.currentTimeMillis() - startTime;
        long seconds = elapsedTime / 1000;
        long milliseconds = elapsedTime % 1000;

        // 使用 '\r' 替换 '\n'，以覆盖当前行
        System.out.printf("\r解压进度: %d%%, 已解压缩大小: %d 字节, 已花费时间: %d.%03d 秒",
                progressPercentage, decompressedSize, seconds,milliseconds);
    }

    // 计算zip文件的大小
    private static long getZipFileSize(String zipFilePath) throws IOException {
        File zipFile = new File(zipFilePath);
        return zipFile.length(); // 返回压缩文件的字节大小
    }
}