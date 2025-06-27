package command;
import model.CommandDetail;
import tool.EncryptFile;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

//加密文件
//注意默认目标地址的问题
public class EncryptFileCommand implements Command{
    private  Object lock;
    @Override
    public void execute(CommandDetail details) {
        String sourcePath=details.getSourcePath();
        String destinationPath=details.getDestinationPath();
        if(details.getBack()==1)
        {
            final Object lock=details;
            details.getTaskPool().submitTask("加密 "+details.getSourcePath(), () -> {
                synchronized (lock) { // 确保任务的线程安全
                    try {
                        // 在执行文件操作前获得文件锁
                        try (FileChannel channel = FileChannel.open(Paths.get(sourcePath))) {
                            try (FileLock fileLock = channel.lock(0, Long.MAX_VALUE, true)) {
                                Thread.sleep(2000); // 模拟长时间运行的任务
                                EncryptFile.encryptFile(details.getPassword(), details.getSourcePath(), details.getDestinationPath(), 1);
                            } catch (InterruptedException | NoSuchAlgorithmException | NoSuchPaddingException |
                                     InvalidKeyException | IOException e) {
                                Thread.currentThread().interrupt();
                            }

                        }
                    }
                    catch (RuntimeException | IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }
        else {
            try {
                EncryptFile.encryptFile(details.getPassword(),details.getSourcePath(),details.getDestinationPath(),0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
