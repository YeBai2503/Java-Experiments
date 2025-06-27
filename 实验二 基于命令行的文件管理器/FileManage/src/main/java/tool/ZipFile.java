package tool;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

//传入参数1.源地址2.目的地址3.是否允许重写
public class ZipFile {
    // 记录压缩任务状态
    private static long totalSize = 0; // 记录要压缩的文件总大小
    private static long compressedSize = 0; // 记录已压缩的大小

    public static void zipPath(String sourcePath, String zipFilePath, boolean overwrite,int back) throws IOException {
        File sourceFile = new File(sourcePath);

        // 检查目标文件是否存在，且不允许覆盖
        if (!overwrite && new File(zipFilePath).exists()) {
            throw new IOException("目标文件已存在，且不允许覆盖：" + zipFilePath);
        }

        // 获取源文件的总大小
        totalSize = getDirectorySize(sourceFile);
        compressedSize = 0; // 初始化已压缩大小

        try (FileOutputStream fos = new FileOutputStream(zipFilePath);
             ZipOutputStream zos = new ZipOutputStream(fos)) {
            zipFileOrDirectory(sourceFile, zos, sourceFile.getName(),back);
        }
    }

    // 递归压缩文件或目录
    private static void zipFileOrDirectory(File fileOrDir, ZipOutputStream zos, String zipEntryName,int back) throws IOException {
        if (fileOrDir.isDirectory()) {
            File[] files = fileOrDir.listFiles();
            if (files != null) {
                for (File file : files) {
                    zipFileOrDirectory(file, zos, zipEntryName + "/" + file.getName(),back);
                }
            }
            // 添加目录条目
            zos.putNextEntry(new ZipEntry(zipEntryName + "/"));
            zos.closeEntry();
        } else {
            // 处理文件压缩
            try (FileInputStream fis = new FileInputStream(fileOrDir)) {
                ZipEntry zipEntry = new ZipEntry(zipEntryName);
                zos.putNextEntry(zipEntry);

                byte[] buffer = new byte[1024];
                int length;
                while ((length = fis.read(buffer)) >= 0) {
                    zos.write(buffer, 0, length);
                    compressedSize += length; // 更新已压缩大小
                    if(back==0)printProgress(); // 打印进度
                }
                zos.closeEntry();
            }
        }
    }

    // 打印压缩进度与已花费时间
    private static void printProgress() {
        int progressPercentage = (int) ((compressedSize * 100) / totalSize);
        long seconds = (System.currentTimeMillis() - startTime) / 1000;
        // 将耗时转换为秒和毫秒
        long milliseconds = (System.currentTimeMillis() - startTime) % 1000;

        // 打印进度信息
        System.out.printf("\r压缩进度: %d%%, 已压缩大小: %d 字节, 已花费时间: %d.%03d 秒",
                progressPercentage, compressedSize, seconds,milliseconds);
    }

    // 获取目录的总大小
    private static long getDirectorySize(File dir) {
        long size = 0;
        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (files != null) {
                for (File file : files) {
                    size += getDirectorySize(file);
                }
            }
        } else {
            size = dir.length();
        }
        return size;
    }

    private static long startTime = System.currentTimeMillis(); // 记录开始时间
}