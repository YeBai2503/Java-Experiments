package tool;

import entity.AbstractNameAndId;

import java.util.ArrayList;

//通过id寻找对应元素在数组下的下标
//传入泛型数组和id
public class IdSearchIndex {
    public static <T extends AbstractNameAndId> int searchIndex(ArrayList<T> list, int id) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId() == id) {
                return i; // 找到 id，返回索引
            }
        }
        return -1; // 如果未找到，返回 -1
    }
}
