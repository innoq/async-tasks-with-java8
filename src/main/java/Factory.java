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

    public MyTask<User> createUser(String userData) {
        MyTask<User> task = User.createAccount(this.conn, userData);
        TaskPoll taskPoll = new TaskPoll(task, scheduler);
        scheduler.submit(taskPoll);
        return task;
    }
}
