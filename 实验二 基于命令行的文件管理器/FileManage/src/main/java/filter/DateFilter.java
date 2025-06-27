package filter;

import model.AbstractFile;
import singleton.CurrentDirectory;
import strategy.TimeStrategy;
import tool.PrintTable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DateFilter implements Filter{
    @Override
    public void sift(CurrentDirectory currentDirectory, String name, long big,long small) {
        ArrayList<AbstractFile> siftFiles = new ArrayList<>();
        // 遍历当前目录中的所有文件
        for (AbstractFile file : currentDirectory.getCurrentFiles()) {
            long time=file.getChangeTime();
            if ((big==0||big!=0&&time<=big)&&(small==0||small!=0&&time>=small)) {
                siftFiles.add(file);
            }
        }
        TimeStrategy timeStrategy=new TimeStrategy();
        timeStrategy.sort(siftFiles);
        print(siftFiles);
    }
    private void print(List<AbstractFile> currentFiles)
    {
        if(currentFiles.size()==0)
        {
            System.out.println("** 暂无满足过滤条件的文件 **");
            return;
        }
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
            table.addBody(Integer.toString(i+1),name,date,type, size); // 添加第一行
        }
        table.print(); // 打印表格
    }
    public static String convertDate(long millis) {
        // 使用系统默认时区将毫秒转换为 LocalDateTime
        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneId.systemDefault());
        // 定义日期格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // 格式化 LocalDateTime 为字符串
        return dateTime.format(formatter);
    }
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
}

