package strategy;

import model.AbstractFile;
import model.DirectoryModel;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

public class NameStrategy implements Strategy{
    @Override
    public void sort(ArrayList<AbstractFile> currentFiles) {
        // 创建一个 Collator 实例
        Collator collator = Collator.getInstance(Locale.CHINESE);

        // 排序
        Collections.sort(currentFiles, new Comparator<AbstractFile>() {
            @Override
            public int compare(AbstractFile f1, AbstractFile f2) {
                boolean isF1Directory = f1 instanceof DirectoryModel;
                boolean isF2Directory = f2 instanceof DirectoryModel;

                if (isF1Directory && !isF2Directory) {
                    return -1; // f1 是 DirectoryModel，排在前面
                } else if (!isF1Directory && isF2Directory) {
                    return 1; // f2 是 DirectoryModel，排在前面
                } else {
                    // 使用 Collator 按名称比较
                    return collator.compare(f1.getName(), f2.getName());
                }
            }
        });
    }
}
