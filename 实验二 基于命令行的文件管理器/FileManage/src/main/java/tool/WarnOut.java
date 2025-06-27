package tool;

public class WarnOut {
    // ANSI 颜色常量
    public static final String RESET = "\u001B[0m"; // 重置颜色
    public static final String RED = "\u001B[31m"; // 红色
    public static void print(String warning)
    {
        System.out.println(RED+"** "+warning+" **"+RESET);
    }
}
