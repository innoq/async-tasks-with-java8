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
        APITask<User> apiTask = new APITask<>(conn,
                conn.doPostWithJson("/users", userDataAsJsonString),
                task -> User.fromServiceResponse(conn.doGet(task.getResultUri())) );
        TaskPoller taskPoller = new TaskPoller(apiTask, scheduler);
        scheduler.submit(taskPoller);
        return apiTask;
    }
}
