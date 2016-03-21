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

    public APITask<User> createUser(String userDataAsJsonString) {
        int taskUid = conn.doPostWithJson("/users", userDataAsJsonString);
        APITask<User> apiTask = new APITask<>(conn, taskUid, task -> new User(conn.doGet(task.getResultUrl())) );
        TaskPoll taskPoll = new TaskPoll(apiTask, scheduler);
        scheduler.submit(taskPoll);
        return apiTask;
    }
}
