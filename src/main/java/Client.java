import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * Created by torstenk on 07.03.16.
 */
public class Client {
    public static void main(String[] args) throws InterruptedException {
        ScheduledExecutorService scheduler = new ScheduledThreadPoolExecutor(10);
        Factory factory = new Factory(scheduler);

        APITask<User> user1Future = factory.createUser("user1");
        APITask<User> user2Future = factory.createUser("user2");
        APITask<User> user3Future = factory.createUser("user3");
        APITask<User> user4Future = factory.createUser("user4");

        User user1 = getUserFromFuture(user1Future);
        User user2 = getUserFromFuture(user2Future);
        User user3 = getUserFromFuture(user3Future);
        User user4 = getUserFromFuture(user4Future);

        scheduler.shutdown();
    }

    private static User getUserFromFuture(APITask<User> task) {
        try {
            return task.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            System.out.println(e.getCause().getMessage());
        }
        return null;
    }
}
