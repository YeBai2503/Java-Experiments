package model;

public class TaskInfo {
    private final String taskName;
    private volatile boolean isCompleted;

    public TaskInfo(String taskName) {
        this.taskName = taskName;
        this.isCompleted = false;
    }

    public String getTaskName() {
        return taskName;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}
