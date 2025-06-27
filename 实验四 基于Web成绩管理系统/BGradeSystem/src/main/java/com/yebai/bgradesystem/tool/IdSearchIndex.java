package com.yebai.bgradesystem.tool;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class IdSearchIndex {
    public static <T> int searchIndex(ArrayList<T> list, int id) {
        for (int i = 0; i < list.size(); i++) {
            try {
                Method method = list.get(i).getClass().getMethod("getId");
                int itemId = (int) method.invoke(list.get(i));
                if (itemId == id) {
                    return i; // 找到 id，返回索引
                }
            } catch (Exception e) {
                // 处理异常，比如 NoSuchMethodException 或 IllegalAccessException
                e.printStackTrace();
            }
        }
        return -1; // 如果未找到，返回 -1
    }


}

