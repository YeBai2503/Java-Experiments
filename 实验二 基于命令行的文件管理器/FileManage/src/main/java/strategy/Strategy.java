package strategy;

import model.AbstractFile;
import singleton.CurrentDirectory;

import java.util.ArrayList;

public interface Strategy {
    public void sort(ArrayList<AbstractFile> currentFiles);
}
