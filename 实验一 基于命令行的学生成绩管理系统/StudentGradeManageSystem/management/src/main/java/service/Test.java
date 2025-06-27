package service;

import tool.GenerateBarChart;
import tool.GenerateGaussian;
import tool.PrintTable;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;



//测试用程序
public class Test {
    public static void main(String[] args) {
//        Manage manager = Manage.getInstance();
//        manager.dataGenerate();
//        manager.classSelect();
//        manager.gradeGet();


        PrintTable table = PrintTable.create();
        table.setSbcMode(true);// 设置为全角模式
        //table.addHeader("编号", "姓名", "成绩"); // 添加表头

        // 动态生成表头
        List<String> headers = new ArrayList<String>();
        headers.add("排序编号");
        headers.add("学号");
        headers.add("姓名");

        // 使用 toArray() 方法将 List 转换为 String 数组
        String[] headerArray = new String[headers.size()];
        headers.toArray(headerArray);  // 将 List 转换为数组
        table.addHeader(headerArray);

        List<String> bodys = new ArrayList<String>();
        bodys.add("2");
        bodys.add("fad");
        bodys.add("5555");

        // 使用 toArray() 方法将 List 转换为 String 数组
        String[] bodyArray = new String[bodys.size()];
        bodys.toArray(bodyArray);  // 将 List 转换为数组
        table.addBody(bodyArray);

//        table.addBody(Integer.toString(124), "张三积分", "95"); // 添加第一行
//        table.addBody("2", "李四到", "89") ;// 添加第二行
//        table.addBody("3", "王五", "76"); // 添加第三行

        table.print(); // 打印表格

        System.out.println("\n++++++++<全体学生总成绩的分数段统计>++++++++\n");
        List<String> labels = Arrays.asList("100~90", "90~80", "80~70", "70~60", "60~50","50~40","40~0","44");
        List<Integer> values = Arrays.asList(41, 181, 71, 61,51,41,131,55);
        GenerateBarChart.printBarChart(labels,values);



    }
    
}
