package command;

import model.CommandDetail;
import tool.PrintTable;
import tool.PrintTable2;

public class HelpCommand implements Command{
    @Override
    public void execute(CommandDetail details) {
        PrintTable2 table = PrintTable2.create();
        table.setSbcMode(true);// 设置为全角模式
        table.addHeader("序号", "命令","功能", "样例","说明"); // 添加表头
        table.addBody(Double.toString(1.1),"cd \\绝对路径","切换绝对路径","cd \\G:\\文件管理器TEST","绝对路径记得加\\");
        table.addBody(Double.toString(1.2),"cd \\相对路径","切换相对路径","cd 11文件夹","");
        table.addBody(Double.toString(1.3),"cd ..","切换上一级路径","cd ..","");
        table.addBody(Double.toString(1.4),"cd ..\\路径","切换含..的路径","cd ..\\11文件夹","..可以融入路径中，自动识别上一级目录");
        table.addBody(Double.toString(1.5),"cd ~","切换到主目录","cd ~","");
        table.addBody(Double.toString(1.6),"cd -","切换到上次访问的目录","cd -","");
        table.addBody(Double.toString(2.1),"ls","查看目录","ls","默认名称排序");
        table.addBody(Double.toString(2.2),"ls -t","文件按时间排序","ls -t","");
        table.addBody(Double.toString(2.3),"ls -s","文件按大小排序","ls -s","");
        table.addBody(Double.toString(2.4),"ls -n","文件按名称排序","ls -n","");
        table.addBody(Double.toString(3.1),"find -name 名称","过滤出含该名称的文件","find -name 11","过滤出的文件按名称相关性排序");
        table.addBody(Double.toString(3.2),"find -size +S1 -S2","过滤出大于S1和小于S2的文件","find -size +10MB -5KB","可任取大于小于中一个");
        table.addBody(Double.toString(3.3),"find -time +T1 -T2","过滤出早于T1和晚于T2的文件","find -time +2022-11-20 15:20:10 -2024-12-20 15:20:10","可任取大于小于中一个,时分秒可不填");
        table.addBody(Double.toString(3.4),"find -type 类型","过滤该类型的文件","find -type txt","类型写后缀名");
        table.addBody(Integer.toString(4),"cat 路径","查看txt","cat 11.txt","路径可相对可绝对，加-n添加行号");
        table.addBody(Integer.toString(5),"touch 文件名","创建文件","touch 11.txt","");
        table.addBody(Integer.toString(6),"mkdir 文件夹名","创建文件夹","mkdir 1122","");
        table.addBody(Integer.toString(7),"mv 旧名 新名","重命名","mv 11.txt 22.txt","");
        table.addBody(Integer.toString(8),"rm 文件名","删除文件或文件夹","rm 11.txt","");
        table.addBody(Integer.toString(9),"cp 原文件路径 拷贝路径","拷贝文件或文件夹","cp 11.txt 66\\77.txt","可加-back后台运行，路径可相对可绝对");
        table.addBody(Integer.toString(10),"zip 原文件路径 目标路径","压缩文件或文件夹","zip 11 66.zip","可加-back后台运行，路径可相对可绝对");
        table.addBody(Integer.toString(11),"unzip 原文件路径 目标路径","解压文件或文件夹","unzip 11.zip 66","可加-back后台，运行路径可相对可绝对");
        table.addBody(Integer.toString(12),"gpg 密码 原文件路径 目标路径","加密文件或文件夹","gpg 6699 11.txt 11.txtGpg","可加-back后台运行，路径可相对可绝对");
        table.addBody(Integer.toString(13),"ungpg 密码 原文件路径 目标路径","解密文件或文件夹","ungpg 6699 11.txtGpg 11.txt","可加-back后台运行，路径可相对可绝对");
        table.addBody(Integer.toString(14),"backstage","查看后台线程情况","backstage","");
        table.addBody(Integer.toString(15),"help","查看帮助文档","help","");
        table.print(); // 打印表格
    }
}
