package singleton;
import command.*;
import filter.*;
import model.CommandDetail;
import model.TaskInfo;
import tool.ParseDate;
import tool.WarnOut;
import java.io.File;
import java.lang.String;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

//命令解析器（单例模式）
public class CommandParser {
    //设置单例
    private static CommandParser commandParser=new CommandParser();
    private CommandParser(){}
    public static CommandParser getInstance(){
        return commandParser;
    }

    // ANSI 颜色常量
    public static final String RESET = "\u001B[0m"; // 重置颜色
    public static final String RED = "\u001B[31m"; // 红色
    private String formerPath="";

    public void parse(String order,CurrentDirectory currentDirectory,TaskPool taskPool)
    {
        // 使用空格分隔指令
        String[] wordsArray = order.split("\\s+"); // 以空格为分隔符
        // 将指令数组转换为 ArrayList
        ArrayList<String> orderList = new ArrayList<>(Arrays.asList(wordsArray));
        int back=0;//后台运行
        if(orderList.get(orderList.size()-1).equals("-back"))
        {
            back=1;
            orderList.remove(orderList.size()-1);
        }
        CommandDetail commandDetail=new CommandDetail();
        commandDetail.setBack(back);
        commandDetail.setTaskPool(taskPool);
        switch (orderList.get(0))
        {
            case "cd":// 切换目录
                cd(orderList,currentDirectory);
                break;
            case "ls":// 按策略展示文件夹目录
                ls(orderList,currentDirectory);
                break;
            case "find":// 过滤文件
                find(orderList,currentDirectory);
                break;
            case "cat":// 查看txt文件
                cat(orderList,currentDirectory);
                break;
            case "touch":// 创建文件
            case "mkdir":// 创建文件夹
                create(orderList,currentDirectory);
                break;
            case "rm":// 删除文件或文件夹
                delete(orderList,currentDirectory);
                break;
            case "mv":// 重命名文件
                mv(orderList,currentDirectory);
                break;
            case "cp":// 拷贝
                CopyFileCommand copyFileCommand=new CopyFileCommand();
                commandDetail.setSourcePath(processPath(orderList.get(1),currentDirectory));
                commandDetail.setDestinationPath(processPath(orderList.get(2),currentDirectory));
                copyFileCommand.execute(commandDetail);
                break;
            case "gpg":// 加密
                EncryptFileCommand encryptFileCommand=new EncryptFileCommand();
                commandDetail.setPassword(orderList.get(1));
                commandDetail.setSourcePath(processPath(orderList.get(2),currentDirectory));
                if(orderList.size()==4)commandDetail.setDestinationPath(processPath(orderList.get(3),currentDirectory));
                else commandDetail.setDestinationPath(processPath(orderList.get(2),currentDirectory));
                encryptFileCommand.execute(commandDetail);
                break;
            case "ungpg":// 解密
                DecryptFileCommand decryptFileCommand=new DecryptFileCommand();
                commandDetail.setPassword(orderList.get(1));
                commandDetail.setSourcePath(processPath(orderList.get(2),currentDirectory));
                if(orderList.size()==4)commandDetail.setDestinationPath(processPath(orderList.get(3),currentDirectory));
                else commandDetail.setDestinationPath(processPath(orderList.get(2),currentDirectory));
                decryptFileCommand.execute(commandDetail);
                break;
            case "zip":// 压缩
                ZipFileCommand zipFileCommand=new ZipFileCommand();
                commandDetail.setSourcePath(processPath(orderList.get(1),currentDirectory));
                if(orderList.size()==3)commandDetail.setDestinationPath(processPath(orderList.get(2),currentDirectory));
                else commandDetail.setDestinationPath(processPath(orderList.get(1),currentDirectory));
                zipFileCommand.execute(commandDetail);
                break;
            case "unzip":// 解压
                UnzipFileCommand unzipFileCommand=new UnzipFileCommand();
                commandDetail.setSourcePath(processPath(orderList.get(1),currentDirectory));
                if(orderList.size()==3)commandDetail.setDestinationPath(processPath(orderList.get(2),currentDirectory));
                else commandDetail.setDestinationPath(processPath(orderList.get(1),currentDirectory));
                unzipFileCommand.execute(commandDetail);
                break;
            case "help":// 指令帮助文档
                HelpCommand helpCommand=new HelpCommand();
                helpCommand.execute(new CommandDetail());
                break;
            case "backstage":// 查看后台运行情况
                try {
                    Thread.sleep(1000); // 等待片刻以让一些任务开始执行
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                for (TaskInfo info : taskPool.getTaskInfoList()) {
                    System.out.println("后台任务: " + info.getTaskName() + " , 是否完成: " + info.isCompleted());
                }
                break;
            default:
                warning();
                break;
        }
    }

    public void cd(ArrayList<String> orderList,CurrentDirectory currentDirectory)
    {
        if(orderList.size()==1)return;
        if(orderList.size()>2)
        {
            warning();
            return;
        }
        String path=orderList.get(1);
        String former=currentDirectory.getCurrentPath();;//记录上次目录
        if(path.equals("-"))//返回上次目录
        {
            if(formerPath.equals(""))
            {
                WarnOut.print("您还未切换过目录");
                return;
            }
            currentDirectory.setCurrentPath(formerPath);
        }
        else if (path.equals("~")) //返回主目录
        {
            currentDirectory.backMainPath();
        }
        else
        {
            if(!path.startsWith("\\"))//相对路径
            {
                path=resolvePath(currentDirectory.getCurrentPath(),path);
            }
            else //绝对路径
            {
                path=path.substring(1);
            }
            File dir=new File(path);
            if(dir.exists()&&dir.isDirectory())
            {
                if(path.charAt(path.length()-2)==':')
                {
                    WarnOut.print("不可直接读取磁盘，切换失败");
                    return;
                }
                currentDirectory.setCurrentPath(path);
            }
            else
            {
                WarnOut.print("该目录不存在，切换失败");
                return;
            }
        }
        //更新上一次地址，显示新目录
        formerPath=former;
        currentDirectory.updateFiles();
        currentDirectory.showDirectory();
    }

    public void ls(ArrayList<String> orderList,CurrentDirectory currentDirectory)
    {
        if(orderList.size()==1)
        {
            currentDirectory.updateFiles();//更新当前目录
            currentDirectory.showDirectory();//显示当前目录
        }
        else if(orderList.size()==2)
        {
            switch (orderList.get(1))//修改策略
            {
                case "-t":
                    currentDirectory.changeStrategy("t");
                    break;
                case "-s":
                    currentDirectory.changeStrategy("s");
                    break;
                case "-n":
                    currentDirectory.changeStrategy("n");
                    break;
                default:
                    warning();
                    return;
            }
            currentDirectory.updateFiles();//更新当前目录
            currentDirectory.showDirectory();//显示当前目录
        }
        else {
            warning();
        }
    }

    public void find(ArrayList<String> orderList,CurrentDirectory currentDirectory)
    {
        if(orderList.size()==3&&!orderList.get(1).equals("-time")&&!orderList.get(1).equals("-size"))//名称或类型过滤
        {
            switch (orderList.get(1))
            {
                case "-name"://名称过滤
                    NameFilter namefilter=new NameFilter();
                    namefilter.sift(currentDirectory,orderList.get(2),0,0);
                    break;
                case "-type"://类型过滤
                    TypeFilter typefilter=new TypeFilter();
                    typefilter.sift(currentDirectory,orderList.get(2),0,0);
                    break;
                default:
                    warning();
                    break;
            }
        }
        else if(orderList.size()<5&&orderList.get(1).equals("-size"))//大小过滤
        {
            SizeFilter sizeFilter=new SizeFilter();
            String big="",small="";
            if(orderList.size()==4)
            {
                big=orderList.get(2).substring(1);
                small=orderList.get(3).substring(1);
            }
            else
            {
                if(orderList.get(2).startsWith("-"))
                {
                    small=orderList.get(2).substring(1);
                }
                else
                {
                    big=orderList.get(2).substring(1);
                }
            }
            long bigSize=big==""?0:parseSize(big);
            long smallSize=small==""?0:parseSize(small);
            sizeFilter.sift(currentDirectory,"",bigSize,smallSize);
        }
        else if(orderList.get(1).equals("-time")&&orderList.size()>2&&orderList.size()<7)//时间过滤
        {
            String smallDate="",bigDate="";
            if(orderList.get(2).startsWith("-"))//只看时间点之后的
            {
                smallDate=orderList.get(2).substring(1);
                if(orderList.size()==4)smallDate=smallDate+" "+orderList.get(3);
            }
            else if(orderList.get(2).startsWith("+"))
            {
                switch (orderList.size())
                {
                    case 3:
                        if(orderList.get(2).startsWith("-"))
                        {
                            smallDate=orderList.get(2).substring(1);
                        }
                        else {
                            bigDate=orderList.get(2).substring(1);
                        }
                        break;
                    case 4:
                        if(orderList.get(3).startsWith("-"))
                        {
                            bigDate=orderList.get(2).substring(1);
                            smallDate=orderList.get(3).substring(1);
                        }
                        else
                        {
                            bigDate=orderList.get(2).substring(1)+" "+orderList.get(3);
                        }
                        break;
                    case 5:
                        if(orderList.get(3).startsWith("-"))
                        {
                            bigDate=orderList.get(2).substring(1);
                            smallDate=orderList.get(3).substring(1)+" "+orderList.get(4);
                        }
                        else
                        {
                            bigDate=orderList.get(2).substring(1)+" "+orderList.get(3);
                            smallDate=orderList.get(4).substring(1);
                        }
                        break;
                    case 6:
                        bigDate=orderList.get(2).substring(1)+" "+orderList.get(3);
                        smallDate=orderList.get(4).substring(1)+" "+orderList.get(5);
                        break;
                    default:
                        warning();
                        break;
                }
            }
            else
            {
                warning();
                return;
            }
            DateFilter dateFilter=new DateFilter();
            long bigTime=bigDate==""?0:ParseDate.parseDateTimeToMillis(bigDate);
            long smallTime=smallDate==""?0:ParseDate.parseDateTimeToMillis(smallDate);
            dateFilter.sift(currentDirectory,"", bigTime,smallTime);
        }
        else {
            warning();
        }
    }
    public void mv(ArrayList<String> orderList,CurrentDirectory currentDirectory)
    {
        if(orderList.size()!=3)
        {
            warning();
        }
        else
        {
            CommandDetail commandDetail=new CommandDetail();
            RenameFileCommand command= new RenameFileCommand();
            commandDetail.setSourcePath(processPath(orderList.get(1),currentDirectory));
            commandDetail.setDestinationPath(processPath(orderList.get(2),currentDirectory));
            command.execute(commandDetail);
        }
    }
    public void cat(ArrayList<String> orderList,CurrentDirectory currentDirectory)
    {
        if(orderList.size()>3)
        {
            warning();
        }
        else
        {
            CommandDetail commandDetail=new CommandDetail();
            ReadFileCommand command= new ReadFileCommand();
            commandDetail.setSourcePath(processPath(orderList.get(1),currentDirectory));
            if(orderList.size()==3)//判断是否需要行号
            {
                if(orderList.get(2).equals("-n"))
                {
                    commandDetail.setNeedNum(1);
                }
                else {
                    warning();
                    return;
                }
            }
            command.execute(commandDetail);
        }
    }
    public void create(ArrayList<String> orderList,CurrentDirectory currentDirectory)
    {
        if(orderList.size()!=2)
        {
            warning();
        }
        else
        {
            CommandDetail commandDetail=new CommandDetail();
            CreateFileCommand command= new CreateFileCommand();
            commandDetail.setSourcePath(processPath(orderList.get(1),currentDirectory));
            command.execute(commandDetail);
        }
    }
    public void delete(ArrayList<String> orderList,CurrentDirectory currentDirectory)
    {
        if(orderList.size()!=2)
        {
            warning();
        }
        else
        {
            CommandDetail commandDetail=new CommandDetail();
            DeleteFileCommand command= new DeleteFileCommand();
            commandDetail.setSourcePath(processPath(orderList.get(1),currentDirectory));
            command.execute(commandDetail);
        }
    }

    public void spaceWarning()
    {
        System.out.println(RED+"**您输入了多余空格！指令每处仅需一个空格分割**"+RESET);
    }
    public void warning()
    {
        System.out.println(RED+"**指令错误！您可以输入help指令来获取指令帮助文档**"+RESET);
    }

    //处理相对路径
    public static String processPath(String inputPath,CurrentDirectory currentDirectory) {
        String processedPath;
        // 检查是否是绝对路径（以斜杠开头）
        if (inputPath.startsWith("\\")) {
            // 去掉第一个斜杠
            processedPath = inputPath.substring(1);
        } else {
            // 对于相对路径，直接使用输入的路径
            processedPath = currentDirectory.getCurrentPath()+"\\"+inputPath;
        }
        return processedPath;
    }
    //处理带..的相对路径
    public static String resolvePath(String currentPath, String relativePath) {
        // 创建当前路径的 Path 对象
        //因为不知道为什么resolveSibling方法会从上一级目录开始计算.. ,所以手动加一层无用的文件夹
        Path current = Paths.get(currentPath+"\\11");
        // 解析相对路径到当前路径
        Path resolvedPath = current.resolveSibling(relativePath);
        // 获取规范化的绝对路径
        return resolvedPath.toAbsolutePath().normalize().toString();
    }
    //处理输入的size
    public static long parseSize(String sizeStr)
    {
        // 获取数值部分和单位部分
        String unit;
        double sizeValue;

        // 判断单位并提取数值
        if (sizeStr.endsWith("GB")) {
            unit = "GB";
            sizeValue = Double.parseDouble(sizeStr.substring(0, sizeStr.length() - 2));
        } else if (sizeStr.endsWith("MB")) {
            unit = "MB";
            sizeValue = Double.parseDouble(sizeStr.substring(0, sizeStr.length() - 2));
        } else if (sizeStr.endsWith("KB")) {
            unit = "KB";
            sizeValue = Double.parseDouble(sizeStr.substring(0, sizeStr.length() - 2));
        } else if (sizeStr.endsWith("B")) {
            unit = "B";
            sizeValue = Double.parseDouble(sizeStr.substring(0, sizeStr.length() - 1));
        } else {//无单位时默认字节单位
            unit = "B";
            sizeValue = Double.parseDouble(sizeStr);
        }

        // 根据单位转换为字节数
        switch (unit) {
            case "GB":
                return (long) (sizeValue * 1024 * 1024 * 1024); // GB to Bytes
            case "MB":
                return (long) (sizeValue * 1024 * 1024); // MB to Bytes
            case "KB":
                return (long) (sizeValue * 1024); // KB to Bytes
            case "B":
                return (long) sizeValue; // Bytes
            default:
                throw new IllegalStateException("Unexpected value: " + unit);
        }
    }
}
