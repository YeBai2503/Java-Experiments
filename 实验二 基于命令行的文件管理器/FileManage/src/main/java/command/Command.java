package command;

import model.AbstractFile;
import model.CommandDetail;

public interface Command {
    public void execute(CommandDetail details);
}
