package command;

import model.CommandDetail;
import tool.CopyFile;
import tool.ZipFile;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Paths;

//压缩文件
//注意默认目标地址的问题
public class ZipFileCommand implements Command{
    private Object lock;
    @Override
    public void execute(CommandDetail details) {
        String sourcePath = details.getSourcePath(); // 要压缩的文件或文件夹
        String zipFilePath = details.getDestinationPath(); // 压缩后存放的路径
        if(details.getBack()==1)
        {
            final Object lock=details;
            details.getTaskPool().submitTask("压缩 "+details.getSourcePath(), () -> {
                synchronized (lock) { // 确保任务的线程安全
                    try {
                        Thread.sleep(2000); // 模拟长时间运行的任务
                        ZipFile.zipPath(sourcePath, zipFilePath, true, 1);
                    } catch (IOException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }
        else
        {
            // 调用压缩方法
            try {
                ZipFile.zipPath(sourcePath, zipFilePath, true,0);
                System.out.println("\n压缩完成，保存位置: " + zipFilePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
