import com.fasterxml.jackson.databind.JsonNode;

/**
 * Created by torstenk on 07.03.16.
 */
public class User {

    public User(JsonNode json) {
        // parsing json and return new user object
    }

    public static MyTask<User> createAccount(ConnectorMock conn, String userData) {
        String path = "/users";
        int taskUid = conn.doPostWithJson(path, userData);

        return new MyTask<>(conn, taskUid, task -> new User(conn.doGet(task.getResultUrl())) );
    }

    // ...
}
