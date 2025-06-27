package singleton;
import model.TaskInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

//线程池
public class TaskPool {
    //设置单例
    private static TaskPool taskPool=new TaskPool(10);
    private TaskPool(int poolSize) {
        this.executorService = Executors.newFixedThreadPool(poolSize);
        this.taskInfoList = new ArrayList<>();
    }
    public static TaskPool getInstance(){
        return taskPool;
    }

    private final ExecutorService executorService;
    private final List<TaskInfo> taskInfoList;

    public void submitTask(String taskName, Runnable task) {
        TaskInfo taskInfo = new TaskInfo(taskName);
        taskInfoList.add(taskInfo);

        executorService.submit(() -> {
            try {
                task.run();
            } finally {
                taskInfo.setCompleted(true);
               // System.out.println("Task " + taskInfo.getTaskName() + " completed: " + taskInfo.isCompleted());
            }
        });
    }

    public void shutdown() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
    }

    public List<TaskInfo> getTaskInfoList() {
        return taskInfoList;
    }
}
