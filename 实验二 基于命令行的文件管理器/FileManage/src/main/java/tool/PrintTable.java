package tool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//控制台打印表格工具类 支持全角和半角两种模式
//使用案例：
//PrintTable table = PrintTable.create();
//table.setSbcMode(true);// 设置为全角模式
//table.addHeader("班级编号", "课程名称", "开课学期","班级总人数"); // 添加表头
//table.addBody(Integer.toString(classroomId), manager.getClassrooms().get(i).getName(), Integer.toString( manager.getClassrooms().get(i).getTerm()),Integer.toString( manager.getClassrooms().get(i).getNumStudent())); // 添加第一行
//table.print(); // 打印表格

public class PrintTable {
    private static final char HALF_ROW_LINE = '-';
    private static final char FULL_ROW_LINE = '－';
    private static final char COLUMN_LINE = '|';
    private static final char CORNER = '+';
    // 半角模式空格的unicode码
    private static final char HALF_SPACE = '\u0020';
    // 全角模式空格的unicode码
    private static final char FULL_SPACE = '\u3000';
    private static final char LF = '\n';
    private static final char SPACE = ' ';
    private static final String EMPTY = "";

    // 全角模式
    private boolean sbcMode = true;

    public static PrintTable create() {
        return new PrintTable();
    }

    // 表格头信息
    private final List<List<String>> headerList = new ArrayList<List<String>>();

    // 表格体信息
    private final List<List<String>> bodyList = new ArrayList<List<String>>();

    // 每列最大字符个数
    private List<Integer> columnCharCount;

    //设置是否使用全角模式
    public PrintTable setSbcMode(boolean sbcMode) {
        this.sbcMode = sbcMode;
        return this;
    }

    // 添加表头
    public PrintTable addHeader(String... titles) {
        if (columnCharCount == null) {
            columnCharCount = new ArrayList<Integer>(Collections.nCopies(titles.length, 0));
        }
        List<String> headers = new ArrayList<String>();
        fillColumns(headers, titles);
        headerList.add(headers);
        return this;
    }

// 添加表体

    public PrintTable addBody(String... values) {
        List<String> bodies = new ArrayList<String>();
        bodyList.add(bodies);
        fillColumns(bodies, values);
        return this;
    }

    // 填充表头或者表体
    private void fillColumns(List<String> columns, String[] values) {
        for (int i = 0; i < values.length; i++) {
            String column = values[i];
            if (sbcMode) {
                column = toSbc(column);
            }
            columns.add(column);
            int width = column.length();
            if (!sbcMode) {
                int sbcCount = nonSbcCount(column);
                width = (width - sbcCount) * 2 + sbcCount;
            }
            if (width > columnCharCount.get(i)) {
                columnCharCount.set(i, width);
            }
        }
    }

    // 获取表格字符串

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        fillBorder(sb);
        fillRows(sb, headerList,0);
        fillBorder(sb);
        fillRows(sb, bodyList,1);
        fillBorder(sb);
        return sb.toString();
    }

    // 填充表头或者表体信息（多行）
    private void fillRows(StringBuilder sb, List<List<String>> list,int k) {

        for (List<String> row : list) {
            sb.append(COLUMN_LINE);
            fillRow(sb, row,k);
            sb.append(LF);
        }
    }

    // 填充一行数据

    private void fillRow(StringBuilder sb, List<String> row,int first) {
        final int size = row.size();
        String value;
        for (int i = 0; i < size; i++) {
            value = row.get(i);
            if(i!=size-1||(i==size-1&&first==0))//判断是否是表头，表体中最后一列要右对齐
            {
                sb.append(sbcMode ? FULL_SPACE : HALF_SPACE);
                sb.append(value);
            }
            final int length = value.length();
            final int sbcCount = nonSbcCount(value);
            if (sbcMode && sbcCount % 2 == 1) {
                sb.append(SPACE);
            }
            sb.append(sbcMode ? FULL_SPACE : HALF_SPACE);

            int maxLength = columnCharCount.get(i);
            int doubleNum = 2;
            if (sbcMode) {
                for (int j = 0; j < (maxLength - length + (sbcCount / doubleNum)); j++) {
                    sb.append(FULL_SPACE);
                }
            } else {
                for (int j = 0; j < (maxLength - ((length - sbcCount) * doubleNum + sbcCount)); j++) {
                    sb.append(HALF_SPACE);
                }
            }
            if(i==size-1&&first!=0)//判断是否是表头，表体中最后一列要右对齐
            {
                sb.append(value);
                sb.append(sbcMode ? FULL_SPACE : HALF_SPACE);
            }
            sb.append(COLUMN_LINE);
        }
    }

    // 填充边框

    private void fillBorder(StringBuilder sb) {
        sb.append(CORNER);
        for (Integer width : columnCharCount) {
            sb.append(sbcMode ? repeat(FULL_ROW_LINE, width + 2) : repeat(HALF_ROW_LINE, width + 2));
            sb.append(CORNER);
        }
        sb.append(LF);
    }

    // 打印到控制台
    public void print() {
        System.out.print(this);
    }

    //半角字符数量<br/>
    private int nonSbcCount(String value) {
        int count = 0;
        for (int i = 0; i < value.length(); i++) {
            if (value.charAt(i) < '\177') {
                count++;
            }
        }
        return count;
    }

    // 重复字符

    public static String repeat(char c, int count) {
        if (count <= 0) {
            return EMPTY;
        }

        char[] result = new char[count];
        for (int i = 0; i < count; i++) {
            result[i] = c;
        }
        return new String(result);
    }

    //转成全角字符
    public static String toSbc(String input) {
        final char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == ' ') {
                c[i] = '\u3000';
            } else if (c[i] < '\177') {
                c[i] = (char) (c[i] + 65248);
            }
        }
        return new String(c);
    }
}
