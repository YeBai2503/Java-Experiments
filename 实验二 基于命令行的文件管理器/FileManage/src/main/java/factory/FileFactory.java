package factory;

import model.AbstractFile;
import model.FileModel;
import model.Model;
import tool.GetFileType;

public class FileFactory implements Factory{
    @Override
    public AbstractFile produce(String path, String name, long size, long changeTime) {
        FileModel fileModel=new FileModel();
        fileModel.setPath(path);
        fileModel.setName(name);
        fileModel.setSize(size);
        fileModel.setChangeTime(changeTime);
        fileModel.setType(GetFileType.getFileType(fileModel));
        return fileModel;
    }
}
