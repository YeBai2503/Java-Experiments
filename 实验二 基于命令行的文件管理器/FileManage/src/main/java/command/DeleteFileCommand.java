package command;
import model.CommandDetail;
import tool.WarnOut;
import java.io.File;

// 删除指定路径的文件或文件夹的全部内容
public class DeleteFileCommand implements Command{
    @Override
    public void execute(CommandDetail details) {
        String path=details.getSourcePath();
        File fileOrDir = new File(path);

        if (!fileOrDir.exists()) {
            WarnOut.print("该路径不存在: " + path);
            return;
        }

        if (fileOrDir.isFile()) {
            // 如果是文件，直接删除
            if (fileOrDir.delete()) {
                System.out.println("文件删除成功: " + path);
            } else {
                WarnOut.print("文件删除失败: " + path);
            }
        } else if (fileOrDir.isDirectory()) {
            // 如果是文件夹，递归删除其内容
            deleteDirectoryContents(fileOrDir);
            if (fileOrDir.delete()) {
                System.out.println("文件夹删除成功: " + path);
            } else {
                WarnOut.print("文件夹删除失败: " + path);
            }
        } else {
            WarnOut.print("该路径不是文件或文件夹: " + path);
        }
    }

    // 递归删除目录中的所有文件和子目录
    private static void deleteDirectoryContents(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    // 递归调用
                    deleteDirectoryContents(file);
                }
                // 删除文件或空目录
                if (file.delete()) {
                    System.out.println("删除: " + file.getAbsolutePath());
                } else {
                    WarnOut.print("该文件删除失败: " + file.getAbsolutePath());
                }
            }
        }
    }
}
