package client;
import singleton.CommandParser;
import singleton.CurrentDirectory;
import singleton.TaskPool;

import java.util.Scanner;

//前端服务类 主函数
public class Main {
    public static void main(String[] args) {
        CurrentDirectory currentDirectory=CurrentDirectory.getInstance();//当前目录
        CommandParser commandParser=CommandParser.getInstance();//命令解析器
        TaskPool taskPool=TaskPool.getInstance();//线程池help
        String order="";//指令
        Scanner scanner = new Scanner(System.in);
        currentDirectory.updateFiles();//更新当前目录
        currentDirectory.showDirectory();//显示当前目录
        while(true)
        {
            currentDirectory.updateFiles();//更新当前目录
            System.out.print(currentDirectory.getCurrentPath()+">> ");
            order=scanner.nextLine().trim();//读入指令并去除两端空格
            if (order.equals("exit")) {
                break; // 退出循环
            }
            else
            {
                //指令解析器
                commandParser.parse(order,currentDirectory,taskPool);
            }
        }
        scanner.close();
        taskPool.shutdown();
    }

}
