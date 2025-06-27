package command;
import model.CommandDetail;
import tool.WarnOut;
import java.io.File;
import java.io.IOException;

//创建文件夹或文件，目标地址的文件夹不存在时会尝试创建文件夹
public class CreateFileCommand implements Command {
    @Override
    public void execute(CommandDetail details) {
        String path = details.getSourcePath(); // 替换为要创建的文件路径
        File fileOrDir = new File(path);

        try {
            if (fileOrDir.exists()) {
                System.out.println("The path already exists: " + path);
                return;
            }

            // 判断路径是否以文件名结尾并创建文件或文件夹
            if (isFile(path)) {
                // 创建文件
                fileOrDir.getParentFile().mkdirs(); // 确保目录存在
                if (fileOrDir.createNewFile()) {
                    System.out.println("文件创建成功: " + path);
                } else {
                    WarnOut.print("文件创建失败: " + path);
                }
            } else {
                // 创建目录
                if (fileOrDir.mkdirs()) {
                    System.out.println("文件夹创建成功: " + path);
                } else {
                    WarnOut.print("文件夹创建失败: " + path);
                }
            }
        } catch (IOException e) {
            WarnOut.print("创建失败");
            e.printStackTrace();
        }
    }

    // 帮助方法，判断路径是否代表一个文件
    private static boolean isFile(String path) {
        return path.lastIndexOf(".") > path.lastIndexOf("/") && path.lastIndexOf(".") >= 0;
    }
}