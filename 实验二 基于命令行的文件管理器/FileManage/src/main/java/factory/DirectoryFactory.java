package factory;

import model.AbstractFile;
import model.DirectoryModel;
import model.Model;

public class DirectoryFactory implements Factory{
    @Override
    public AbstractFile produce(String path, String name, long size, long changeTime) {
        DirectoryModel directoryModel=new DirectoryModel();
        directoryModel.setPath(path);
        directoryModel.setName(name);
        directoryModel.setSize(size);
        directoryModel.setChangeTime(changeTime);
        directoryModel.setType("文件夹");
        return directoryModel;
    }
}
