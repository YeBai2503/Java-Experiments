package model;

import singleton.TaskPool;

import java.util.ArrayList;

//传给命令类的信息
public class CommandDetail implements Model{
    private ArrayList<String> orderList;
    private String sourcePath;
    private String destinationPath;
    private String currentPath;
    private String password;
    private int back;
    private int needNum=0;
    private TaskPool taskPool;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public TaskPool getTaskPool() {
        return taskPool;
    }

    public void setTaskPool(TaskPool taskPool) {
        this.taskPool = taskPool;
    }

    public int getNeedNum() {
        return needNum;
    }

    public void setNeedNum(int needNum) {
        this.needNum = needNum;
    }

    public ArrayList<String> getOrderList() {
        return orderList;
    }

    public String getSourcePath() {
        return sourcePath;
    }

    public String getDestinationPath() {
        return destinationPath;
    }

    public String getCurrentPath() {
        return currentPath;
    }

    public int getBack() {
        return back;
    }

    public void setOrderList(ArrayList<String> orderList) {
        this.orderList = orderList;
    }

    public void setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
    }

    public void setDestinationPath(String destinationPath) {
        this.destinationPath = destinationPath;
    }

    public void setCurrentPath(String currentPath) {
        this.currentPath = currentPath;
    }

    public void setBack(int back) {
        this.back = back;
    }
}
