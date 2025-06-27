package command;

import service.Manage;

public class ClearCommand implements Command{

    public int execute(Manage manager,int one,int two,int three,String word) {
        manager.clearData();
        return 0;
    }
}
