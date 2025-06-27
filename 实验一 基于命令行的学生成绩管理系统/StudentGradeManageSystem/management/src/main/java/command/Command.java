package command;

import service.Manage;

public interface Command {
    public int execute(Manage manager,int one,int two,int three,String word);
}
