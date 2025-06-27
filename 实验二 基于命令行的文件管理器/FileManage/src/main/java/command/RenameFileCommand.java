package command;
import java.io.File;
import model.CommandDetail;
import tool.WarnOut;

//重命名文件，默认相对路径，绝对路径要是所属文件夹改变可能出错
public class RenameFileCommand implements Command{
    @Override
    public void execute(CommandDetail details) {
        String currentFilePath=details.getSourcePath();
        String newFilePath=details.getDestinationPath();
        File currentFile = new File(currentFilePath);
        File newFile = new File(newFilePath);

        // 检查当前文件是否存在
        if (!currentFile.exists()) {
            WarnOut.print("该文件不存在: " + currentFilePath);
            return;
        }

        // 检查新名称是否已经存在
        if (newFile.exists()) {
            WarnOut.print("已存在该名称: " + newFilePath);
            return;
        }

        // 尝试重命名文件
        if (currentFile.renameTo(newFile)) {
            System.out.println("重命名成功: " + currentFilePath + " to " + newFilePath);
        } else {
            WarnOut.print("重命名失败 " + currentFilePath + " to " + newFilePath);
        }
    }

}
