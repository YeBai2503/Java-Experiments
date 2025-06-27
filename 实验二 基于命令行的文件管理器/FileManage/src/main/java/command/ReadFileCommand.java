package command;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import model.CommandDetail;

//读取文本文件
public class ReadFileCommand implements Command{
    @Override
    public void execute(CommandDetail details) {
        String filePath = details.getSourcePath();
        int needNum=details.getNeedNum();//是否需要行号

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int num=1;//行号
            System.out.println();
            while ((line = br.readLine()) != null) {
                if(needNum==1)
                {
                    System.out.print(num+"| ");// 打印行号
                    System.out.println(line); // 输出每一行
                }
                else System.out.println(line); // 输出每一行
                num++;
            }
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace(); // 打印异常信息
        }
    }
}
