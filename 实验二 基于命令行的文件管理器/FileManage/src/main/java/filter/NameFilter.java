package filter;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import model.AbstractFile;
import singleton.CurrentDirectory;
import tool.PrintTable;

public class NameFilter implements Filter {
    @Override
    public void sift(CurrentDirectory currentDirectory, String name, long big, long small) {
        List<AbstractFile> siftFiles = new ArrayList<>();

        // 遍历当前目录中的所有文件
        for (AbstractFile file : currentDirectory.getCurrentFiles()) {
            String fileName = file.getName();

            // 检查是否包含 name 中所有字符
            if (isMatching(fileName, name)) {
                siftFiles.add(file);
            }
        }

        // 排序 siftFiles 按照相关性
        Collections.sort(siftFiles, new Comparator<AbstractFile>() {
            @Override
            public int compare(AbstractFile f1, AbstractFile f2) {
                return calculateRelevance(f1.getName(), name) - calculateRelevance(f2.getName(), name);
            }
        });

        // 输出结果
        print(siftFiles);
    }

    // 检查 fileName 是否包含 name 中所有字符
    private boolean isMatching(String fileName, String name) {
        int lastIndex = -1;
        for (char ch : name.toCharArray()) {
            lastIndex = fileName.indexOf(ch, lastIndex + 1);
            if (lastIndex == -1) {
                return false; // 如果某个字符没找到，返回 false
            }
        }
        return true; // 所有字符都在文件名中出现
    }

    // 计算相关性的简单方法，越小越相关
    private int calculateRelevance(String fileName, String name) {
        int gapSum = 0; // 用来计算字符之间的间隔
        int lastIndex = -1;

        for (char ch : name.toCharArray()) {
            int index = fileName.indexOf(ch, lastIndex + 1);
            if (index == -1) {
                return Integer.MAX_VALUE; // 字符没找到，相关性很低
            }
            if (lastIndex != -1) {
                gapSum += index - lastIndex - 1; // 计算上一个字符和当前字符之间的间隔
            }
            lastIndex = index;
        }

        return gapSum; // 返回字符间隔和，间隔越小，相关性越高
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