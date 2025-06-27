package factory;

import model.AbstractFile;
import model.Model;

public interface Factory {
    public AbstractFile produce(String path, String name, long size, long changeTime);
}
