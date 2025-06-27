package command;
import model.CommandDetail;
import tool.UnzipFile;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Paths;

//注意文件夹和文件的区别！比如存放的地址
public class UnzipFileCommand implements Command{
    private Object lock;
    @Override
    public void execute(CommandDetail details) {
        String zipFilePath = details.getSourcePath(); // 要解压缩的 zip 文件路径
        String destDirPath = details.getDestinationPath(); // 解压缩到的目标目录
        if(details.getBack()==1)
        {
            final Object lock=details;
            details.getTaskPool().submitTask("解压 "+details.getSourcePath(), () -> {
                synchronized (lock) { // 确保任务的线程安全
                    try {
                        // 在执行文件操作前获得文件锁
                        try (FileChannel channel = FileChannel.open(Paths.get(zipFilePath))) {
                            try (FileLock fileLock = channel.lock(0, Long.MAX_VALUE, true)) {
                                Thread.sleep(2000); // 模拟长时间运行的任务
                                UnzipFile.unzip(zipFilePath, destDirPath, 1);
                                fileLock.release();
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }
        else
        {
            try {
                UnzipFile.unzip(zipFilePath, destDirPath,0);
                System.out.println("解压缩完成，文件保存在: " + destDirPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
