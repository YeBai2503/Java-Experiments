package command;

import model.CommandDetail;
import tool.CopyFile;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Paths;

public class CopyFileCommand implements Command{
    private  Object lock;
    @Override
    public void execute(CommandDetail details) {
        if(details.getBack()==1)//后台
        {
            final Object lock=details;
            details.getTaskPool().submitTask("拷贝 "+details.getSourcePath(), () -> {
                synchronized (lock) { // 确保任务的线程安全
                    try {
                        Thread.sleep(2000); // 模拟长时间运行的任务
                        CopyFile.copy(details.getSourcePath(), details.getDestinationPath(), 1);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
            });
        }
        else CopyFile.copy(details.getSourcePath(),details.getDestinationPath(),0);
    }
}
