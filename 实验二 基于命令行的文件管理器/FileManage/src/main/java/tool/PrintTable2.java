package tool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PrintTable2 {
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

    public static PrintTable2 create() {
        return new PrintTable2();
    }

    // 表格头信息
    private final List<List<String>> headerList = new ArrayList<List<String>>();

    // 表格体信息
    private final List<List<String>> bodyList = new ArrayList<List<String>>();

    // 每列最大字符个数
    private List<Integer> columnCharCount;

    //设置是否使用全角模式
    public PrintTable2 setSbcMode(boolean sbcMode) {
        this.sbcMode = sbcMode;
        return this;
    }

    // 添加表头
    public PrintTable2 addHeader(String... titles) {
        if (columnCharCount == null) {
            columnCharCount = new ArrayList<Integer>(Collections.nCopies(titles.length, 0));
        }
        List<String> headers = new ArrayList<String>();
        fillColumns(headers, titles);
        headerList.add(headers);
        return this;
    }

// 添加表体

    public PrintTable2 addBody(String... values) {
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
        fillRows(sb, headerList);
        fillBorder(sb);
        fillRows(sb, bodyList);
        fillBorder(sb);
        return sb.toString();
    }

    // 填充表头或者表体信息（多行）
    private void fillRows(StringBuilder sb, List<List<String>> list) {
        for (List<String> row : list) {
            sb.append(COLUMN_LINE);
            fillRow(sb, row);
            sb.append(LF);
        }
    }

    // 填充一行数据

    private void fillRow(StringBuilder sb, List<String> row) {
        final int size = row.size();
        String value;
        for (int i = 0; i < size; i++) {
            value = row.get(i);
            sb.append(sbcMode ? FULL_SPACE : HALF_SPACE);
            sb.append(value);
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
