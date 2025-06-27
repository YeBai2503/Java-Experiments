package tool;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GenerateBarChart extends JPanel {
    // ANSI escape codes for background colors
    private static final String RESET = "\u001B[0m";//无色
    private static final String BLUE_BG = "\u001B[44m"; // 蓝色背景
    private static final String LIGHT_BLUE_BG = "\u001B[46m"; // 浅蓝色背景
    private static final String PURPLE_BG = "\u001B[45m"; // 紫色背景
    private static final String YELLOW_BG = "\u001B[43m"; // 黄色背景
    private static final String GREEN_BG = "\u001B[42m"; // 绿色背景
    private static final String ORANGE_BG = "\u001B[48;2;255;165;0m"; // 橙色背景
    private static final String RED_BG = "\u001B[41m"; // 红色背景
    private static final List<String> colors=Arrays.asList(RED_BG,ORANGE_BG,YELLOW_BG,LIGHT_BLUE_BG,PURPLE_BG,GREEN_BG,BLUE_BG);
    private static final int maxLength=100;// 条形最大长度

    // 方法：打印条形图
    public static void printBarChart(List<String> labels, List<Integer> values) {
        for (int i = 0; i < values.size(); i++) {
            String label = labels.get(i);
            int value = values.get(i);
            String color;
            // 选择背景颜色
            color=colors.get(i%colors.size());
//            if (value > 90) {
//                color = RED_BG;
//            } else if (value > 80) {
//                color = ORANGE_BG;
//            } else if (value > 70) {
//                color = YELLOW_BG;
//            } else if (value > 60) {
//                color = LIGHT_BLUE_BG;
//            } else if (value>50) {
//                color=PURPLE_BG;
//            } else if (value>40) {
//                color=GREEN_BG;
//            } else if (value==0) {
//                color = RESET;
//            } else {
//                color = BLUE_BG;
//            }
            // 打印标签
            System.out.print(String.format("%8s", label + ": "));
            // 打印条形图（使用带有背景颜色的空格表示每个单位）
            for (int j = 0; j < value&&j<maxLength; j++) {
                System.out.print(color + " " + RESET); // 显示带背景的空格
            }
            System.out.println(" "+value); // 换行
        }
    }
}