package factory;

import model.AbstractFile;
import model.CommandDetail;
import model.Model;

import java.util.ArrayList;

public class CommandDetailFactory implements Factory{
        public static CommandDetail produce(ArrayList<String> orderList,String sourcePath,String destinationPath,String currentPath,int back)
        {
            CommandDetail commandDetail =new CommandDetail();
            commandDetail.setOrderList(orderList);
            commandDetail.setSourcePath(sourcePath);
            commandDetail.setDestinationPath(destinationPath);
            commandDetail.setCurrentPath(currentPath);
            commandDetail.setBack(back);

            return commandDetail;
        }

    @Override
    public AbstractFile produce(String path, String name, long size, long changeTime) {
        return null;
    }
}
