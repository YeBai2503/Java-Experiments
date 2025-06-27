package filter;

import singleton.CurrentDirectory;

public interface Filter {
    public void sift(CurrentDirectory currentDirectory,String name,long big,long small);
}
