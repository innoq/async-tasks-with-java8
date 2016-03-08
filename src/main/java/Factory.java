import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by torstenk on 07.03.16.
 */
public class Factory {
    private ConnectorMock conn;
    private ScheduledExecutorService scheduler;

    public Factory(ScheduledExecutorService scheduler) {
        this.scheduler = scheduler;
        this.conn = new ConnectorMock();
    }

    public MyTask<User> createUser(String userDataAsJsonString) {
        int taskUid = conn.doPostWithJson("/users", userDataAsJsonString);
        MyTask<User> myTask = new MyTask<>(conn, taskUid, task -> new User(conn.doGet(task.getResultUrl())) );
        TaskPoll taskPoll = new TaskPoll(myTask, scheduler);
        scheduler.submit(taskPoll);
        return myTask;
    }
}
