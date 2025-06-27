package strategy;
import model.AbstractFile;
import java.util.ArrayList;

public class TimeStrategy implements Strategy{
    @Override
    public void sort(ArrayList<AbstractFile> currentFiles) {
        currentFiles.sort((file1, file2) -> Long.compare(file2.getChangeTime(), file1.getChangeTime()));
    }
}
