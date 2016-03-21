import java.util.Date;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by torstenk on 07.03.16.
 */
public class TaskPoll implements Runnable {
    private APITask task;
    private ScheduledExecutorService ses;
    private int count;

    public TaskPoll(APITask task, ScheduledExecutorService ses) {
        this(task, ses, 0);
    }

    public TaskPoll(APITask task, ScheduledExecutorService ses, int count) {
        this.task = task;
        this.ses = ses;
        this.count = count;
    }

    @Override
    public void run() {
        System.out.println(new Date() + " " + Thread.currentThread().getName() + ": " + task.getTaskUid() + " -> " + (count+1) + ". Mal");

        if (task.fetchStatus().equals("stopped")) {
            System.out.println(new Date() + " " + Thread.currentThread().getName() + ": Task " + task.getTaskUid() + " erfolgreich beendet.");
            task.complete(task.getResult());
        } else if (count >= 10) {
            task.completeExceptionally(new TimeoutException("Task " + task.getTaskUid() + " beendet sich nicht."));
        } else {
            ses.schedule(new TaskPoll(task, ses, count+1), 1, TimeUnit.SECONDS);
        }
    }
}
