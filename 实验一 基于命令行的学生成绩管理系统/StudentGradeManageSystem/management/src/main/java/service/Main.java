package service;

import command.*;
import tool.IdSearchIndex;
import tool.RankingStudent;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Manage manager = Manage.getInstance();
        int mainCommand = -1;
        int dataExist = 0; // 记录数据是否已经生成
        Scanner scanner = new Scanner(System.in); // 添加 Scanner 读取用户输入

        welcomeWords();//欢迎词
        do {
            mainMenu();//展示主功能菜单
            System.out.print("请选择功能: ");
            mainCommand = scanner.nextInt(); // 读取用户输入的命令

            switch (mainCommand) {
                case 1: // 生成数据
                    dataExist = generateFunction(dataExist,manager);//生成数据，并返回1
                    break;
                case 2: // 查看数据
                    watchMenu(dataExist,manager);//查看功能菜单模块
                    break;
                case 3: // 管理数据
                    changeMenu(dataExist,manager);//管理功能菜单模块
                    break;
                case 4: // 清除数据
                    dataExist = clearFunction(dataExist,manager);//清除数据，并返回0
                    break;
                case 5: // 退出
                    systemMessage("退出成功！欢迎您下次使用！");
                    break;
                default:
                    exceptionMessage("该命令无效！请您重新输入");
                    break;
            }

        } while (mainCommand != 5);

        scanner.close(); // 关闭 Scanner
    }


    // 打印欢迎词
    public static void welcomeWords() {
        System.out.println("**************************************************");
        System.out.println("*********欢迎使用<北港大学>学生成绩管理系统！*********");
        System.out.println("**************************************************");
    }

    // 打印主功能菜单
    public static void mainMenu() {
        System.out.println("\n<<主功能菜单>>");
        System.out.println("1.生成数据");
        System.out.println("2.查看数据");
        System.out.println("3.管理数据");
        System.out.println("4.清除数据");
        System.out.println("5.退出");
    }

    // 数据生成功能
    public static int generateFunction(int dataExist,Manage manager)
    {
        //判断当前是否已有数据
        if(dataExist==1)
        {
            exceptionMessage("抱歉，当前已存在系统数据！请您先清除数据再进行该操作");
            return 1;
        }
        GenerateCommand GC=new GenerateCommand();
        GC.execute(manager,0,0,0,"");
        systemMessage("数据已生成！");
        return 1;
    }

    //数据清除功能
    public static int clearFunction(int dataExist,Manage manager)
    {
        Scanner scanner =new Scanner(System.in);
        //判断当前是否已有数据
        if(dataExist==0)
        {
            exceptionMessage("当前未生成数据！无需进行该操作");
            return 0;
        }
        System.out.println("\n您确定清除数据吗？（输入1为确认，其他数字为取消）");
        int yes=scanner.nextInt();
        if(yes!=1)//取消数据清除
        {
            exceptionMessage("操作已取消！");
            return 1;
        }
        ClearCommand CC=new ClearCommand();
        CC.execute(manager,0,0,0,"");
        systemMessage("数据已清除！");
        return 0;
    }

    // 数据查看功能清单
    public static void watchMenu(int dataExist,Manage manager) {
        //判断当前是否已有数据
        if(dataExist==0)
        {
            exceptionMessage("抱歉，当前还未生成数据！请您先生成数据再进行该操作");
            return;
        }
        systemMessage("欢迎进入数据查看模块！");
        Scanner scan = new Scanner(System.in); // 添加 Scanner 读取用户输入
        int watchCommand;
        do {
            System.out.println("\n<数据查看功能菜单>");
            System.out.println("1.按类查看基本信息");
            System.out.println("2.查看指定教学班成绩");
            System.out.println("3.查看指定学生成绩单");
            System.out.println("4.查看学生总排名");
            System.out.println("5.查看成绩分数段分布");
            System.out.println("6.退出");
            System.out.print("请选择功能: ");
            watchCommand = scan.nextInt(); // 读取用户输入的命令
            int secondCommand=0,thirdCommand=0;//二,三级指令
            String nameSearch="";//按姓名找学生用

            switch (watchCommand) {
                case 1: // 按类查看基本信息
                    //菜单
                    System.out.println("\n<按类查看基本信息>");
                    System.out.println("1.学生类");
                    System.out.println("2.老师类");
                    System.out.println("3.课程类");
                    System.out.println("4.教学班类");
                    System.out.print("请输入你要查看的类：");
                    secondCommand=scan.nextInt();
                    //判断错误输入
                    if(secondCommand<1||secondCommand>4)
                    {
                        exceptionMessage("输入不合法！请重试");
                        continue;
                    }
                    SearchBaseCommand searchBaseCommand=new SearchBaseCommand();
                    searchBaseCommand.execute(manager,secondCommand,0,0,"");
                    break;
                case 2: // 查看指定教学班成绩
                    System.out.print("\n请输入你要查看的班级编号：");
                    secondCommand=scan.nextInt();
                    //判断班级是否存在
                    if(IdSearchIndex.searchIndex(manager.getClassrooms(),secondCommand)==-1)
                    {
                        exceptionMessage("该班级不存在！请重试");
                        continue;
                    }
                    System.out.print("请输入你要查看的排序方式（1为学号排序，2为课程成绩排序）：");
                    thirdCommand=scan.nextInt();
                    //判断指令合法
                    if(thirdCommand!=1&&thirdCommand!=2)
                    {
                        exceptionMessage("该指令不合法！请重试");
                        continue;
                    }
                    SearchClassCommand searchClassCommand=new SearchClassCommand();
                    searchClassCommand.execute(manager,thirdCommand,secondCommand,0,"");
                    break;
                case 3: // 查看指定学生成绩单
                    System.out.print("\n请输入你的搜索方式（1为学号查找，2为姓名查找）：");
                    thirdCommand=scan.nextInt();
                    //判断指令合法
                    if(thirdCommand==1)
                    {
                        System.out.print("\n请输入你要查找的学生ID：");
                        secondCommand=scan.nextInt();
                        //判断学生id是否存在
                        if(IdSearchIndex.searchIndex(manager.getStudents(),secondCommand)==-1)
                        {
                            exceptionMessage("该学生不存在！请重试");
                            continue;
                        }
                    }
                    else if (thirdCommand==2)
                    {
                        System.out.print("\n请输入你要查找的学生姓名：");
                        nameSearch=scan.next();
                    }
                    else
                    {
                        exceptionMessage("该指令不合法！请重试");
                        continue;
                    }
                    SearchStudentCommand searchStudentCommand=new SearchStudentCommand();
                    int live=searchStudentCommand.execute(manager,thirdCommand,secondCommand,0,nameSearch);
                    if(live==0) exceptionMessage("该学生不存在！请重试");//不存在该姓名的学生
                    break;
                case 4: // 查看学生总排名
                    System.out.print("\n请输入你要查看的排序方式（1为学号排序，2为课程成绩排序则，3为总成绩排序）：");
                    secondCommand=scan.nextInt();
                    if(secondCommand==2)
                    {
                        System.out.print("请输入你要指定的课程编号：");
                        thirdCommand=scan.nextInt();
                    }
                    RankingCommand rankingCommand=new RankingCommand();
                    rankingCommand.execute(manager,secondCommand,thirdCommand,0,"");
                    break;
                case 5: // 查看成绩分数段分布
                    System.out.print("\n请输入你要查看的分数段（1为全体学生总成绩及课程成绩的统计，2为指定教学班的成绩）:");
                    secondCommand=scan.nextInt();
                    if(secondCommand==1)
                    {
                        StatisticsCommand statisticsCommand=new StatisticsCommand();
                        statisticsCommand.execute(manager,1,0,0,"");
                    }
                    else if(secondCommand==2)
                    {
                        System.out.print("请输入你要查看的教学班编号:");
                        thirdCommand=scan.nextInt();
                        //判断班级编号是否存在
                        if (IdSearchIndex.searchIndex(manager.getClassrooms(),thirdCommand)==-1)
                        {
                            exceptionMessage("该班级不存在！请重试");
                            continue;
                        }
                        StatisticsCommand statisticsCommand=new StatisticsCommand();
                        statisticsCommand.execute(manager,2,thirdCommand,0,"");
                    }
                    else
                    {
                        exceptionMessage("该指令不合法！请重试");
                        continue;
                    }
                    break;
                case 6: // 退出
                    break;
                default:
                    exceptionMessage("该命令无效！请您重新输入");
                    break;
            }

        } while (watchCommand != 6);
        systemMessage("已退出数据查看！");
    }

    // 数据修改功能清单
    public static void changeMenu(int dataExist,Manage manager) {
        //判断当前是否已有数据
        if(dataExist==0)
        {
            exceptionMessage("抱歉，当前还未生成数据！请您先生成数据再进行该操作");
            return;
        }
        System.out.println("\n————提示：该功能为教师修改所负责教学班的成绩使用，需验证教师身份");
        Scanner scanner=new Scanner(System.in);
        System.out.print("请输入您的教师ID: ");
        int TID=scanner.nextInt();
        //验证教师身份
        CheckCommand checkCommand=new CheckCommand();
        int check = checkCommand.execute(manager,TID,0,0,"");
        if(check==0)
        {
            exceptionMessage("教师身份验证未通过！");
            return;
        }
        systemMessage("欢迎进入数据管理模块！");
        System.out.println("\n这是您的教学班信息：");
        //教学班信息
        ClassBaseCommand classBaseCommand=new ClassBaseCommand();
        int classroomId=classBaseCommand.execute(manager,TID,0,0,"");
        //提供所教学生信息
        System.out.println("\n————是否需要提供班内学生信息？（输入1为需要，其他数字为拒绝）");
        System.out.print("您的选择：");
        int provide=scanner.nextInt();
        System.out.println("感谢您的选择！");
        if(provide==1)
        {
            SearchClassCommand searchClassCommand =new SearchClassCommand();
            searchClassCommand.execute(manager,1,classroomId,0,"");
        }
        //修改成绩
        int conChange=1;//是否继续修改
        do {
            System.out.print("\n————请输入你要修改成绩的学生的ID(如不想修改，可输入0退出数据管理)：");
            int SID=scanner.nextInt();
            if(SID==0)//用户不想修改，退出系统
            {
                systemMessage("已退出数据管理！");
                return;
            }
            if(IdSearchIndex.searchIndex(manager.getStudents(),SID)==-1)//查询是否存在该学生
            {
                exceptionMessage("该用户不存在！请重试");
                continue;
            }
            //判断该学生是否在此教学班
            else if (!manager.getClassrooms().get(IdSearchIndex.searchIndex(manager.getClassrooms(),classroomId)).getStudentIDs().contains(SID))
            {
                exceptionMessage("无权限！该学生不在您所教学的班级");
                continue;
            }
            //输入要修改的成绩
            System.out.println("\n————请输入你要修改的成绩（1为平时成绩，2为期中成绩，3为实验成绩，4为期末成绩）");
            System.out.print("您的选择是：");
            int gradeSelect=scanner.nextInt();
            if(gradeSelect>5||gradeSelect<1)//是否合法输入
            {
                exceptionMessage("不合法的输入！请重试");
                continue;
            }
            System.out.print("\n————请输入你要修改的分数：");
            int score=scanner.nextInt();
            if(score<0||score>100)
            {
                exceptionMessage("分数不合规！修改失败");
            }
            else
            {
                ChangeGradeCommand changeGradeCommand=new ChangeGradeCommand();
                changeGradeCommand.execute(manager,SID,score,classroomId,String.valueOf(gradeSelect));
                systemMessage("修改成功！");
            }
            System.out.println("\n————是否需要继续修改？（输入1为需要，其他数字为拒绝）");
            System.out.print("您的选择：");
            conChange=scanner.nextInt();
        }while (conChange==1);
        systemMessage("已退出数据管理！");
    }

    //打印系统通知
    public static void systemMessage(String message) {
        System.out.println("\n*********"+message+"*********");
    }

    //打印异常通知
    public static void exceptionMessage(String message) {
        System.out.println("\n===="+message+"====");
    }


}