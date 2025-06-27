package singleton;
import factory.DirectoryFactory;
import factory.FileFactory;
import model.AbstractFile;
import strategy.NameStrategy;
import strategy.SizeStrategy;
import strategy.Strategy;
import strategy.TimeStrategy;
import tool.CalculateDirectorySize;
import tool.PrintTable;
import tool.WarnOut;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class CurrentDirectory {
    //设置单例
    private static CurrentDirectory currentDirectory=new CurrentDirectory();
    private CurrentDirectory(){}
    public static CurrentDirectory getInstance(){
        return currentDirectory;
    }

    private String currentPath="G:\\文件管理器TEST\\测试文件夹";//初始默认文件夹
    private String mainPath="D:\\IntelliJ IDEA 2022.2";//默认主文件夹
    private ArrayList<AbstractFile> currentFiles=new ArrayList<>();
    private Strategy strategy=new NameStrategy();// 文件排序策略

    //显示当前目录
    public void showDirectory()
    {
        strategy.sort(currentFiles);
        PrintTable table = PrintTable.create();
        table.setSbcMode(true);// 设置为全角模式
        table.addHeader("序号","文件名称", "修改日期", "类型","大小"); // 添加表头
        // 添加表体
        for(int i=0;i<currentFiles.size();i++)
        {
            String name,date,type,size;
            name=currentFiles.get(i).getName();
            date=convertDate(currentFiles.get(i).getChangeTime());
            type=currentFiles.get(i).getType();
            size=convertSize(currentFiles.get(i).getSize());
            table.addBody(Integer.toString(i+1),name,date,type,size); // 添加第一行
        }
        table.print(); // 打印表格
    }

    //更新目录下文件
    public void updateFiles()
    {
        currentFiles.clear();//清除之前的文件信息
        DirectoryFactory directoryFactory=new DirectoryFactory();
        FileFactory fileFactory=new FileFactory();
        Path directoryPath = Paths.get(currentPath);
        try {
            // 列出目录下的所有第一级文件和文件夹
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(directoryPath)) {
                for (Path entry : stream) {
                    // 获取文件或文件夹的信息
                    BasicFileAttributes attrs = Files.readAttributes(entry, BasicFileAttributes.class);
                    String name = entry.getFileName().toString();// 获取文件名
                    long size = attrs.size(); // 获取文件大小
                    long lastModifiedTime = attrs.lastModifiedTime().toMillis(); // 获取最后修改日期
                    // 加入到文件数组
                    if (attrs.isDirectory()) {
                        size= CalculateDirectorySize.calculate(entry);
                        currentFiles.add(directoryFactory.produce(currentPath+"\\"+name,name,size,lastModifiedTime));
                    }
                    else {
                        currentFiles.add(fileFactory.produce(currentPath+"\\"+name,name,size,lastModifiedTime));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    //修改文件排序策略
    public void changeStrategy(String strategy)
    {
        this.strategy=null;
        switch(strategy)
        {
            case "t":
                this.strategy=new TimeStrategy();
                System.out.println("文件已采用时间排序！");
                break;
            case "s":
                this.strategy=new SizeStrategy();
                System.out.println("文件已采用大小排序！");
                break;
            case "n":
                this.strategy=new NameStrategy();
                System.out.println("文件已采用名称排序！");
                break;
            default:
                this.strategy=new NameStrategy();
                break;
        }
    }



    //文件大小格式转换
    public static String convertSize(long sizeInBytes) {
        if (sizeInBytes < 0) {
            return "Invalid size"; // 处理负值情况
        }
        if (sizeInBytes >= 1024 * 1024 * 1024) {
            // 大于等于1GB
            double sizeInMB = sizeInBytes / (1024.0 * 1024.0 * 1024.0);
            return String.format("%.2f GB", sizeInMB); // 保留两位小数，单位为MB
        }else if (sizeInBytes >= 1024 * 1024) {
            // 大于等于1MB
            double sizeInMB = sizeInBytes / (1024.0 * 1024.0);
            return String.format("%.2f MB", sizeInMB); // 保留两位小数，单位为MB
        } else if (sizeInBytes >= 1024) {
            // 大于等于1KB
            double sizeInKB = sizeInBytes / 1024.0;
            return String.format("%.2f KB", sizeInKB); // 保留两位小数，单位为KB
        } else {
            // 小于1KB，直接返回字节数
            return sizeInBytes + "  B";
        }
    }

    //文件日期格式转换
    public static String convertDate(long millis) {
        // 使用系统默认时区将毫秒转换为 LocalDateTime
        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneId.systemDefault());
        // 定义日期格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // 格式化 LocalDateTime 为字符串
        return dateTime.format(formatter);
    }

    public void setCurrentPath(String currentPath) {
        File dir=new File(currentPath);
        if(dir.exists()&&dir.isDirectory())
        {
            this.currentPath = currentPath;
        }
        else
        {
            WarnOut.print("该目录不存在，切换失败");
        }
    }

    public String getCurrentPath() {
        return currentPath;
    }

    public ArrayList<AbstractFile> getCurrentFiles() {
        return currentFiles;
    }

    //返回主目录
    public void backMainPath() {
        this.currentPath = mainPath;
    }

}
