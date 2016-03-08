import com.fasterxml.jackson.databind.JsonNode;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

/**
 * Created by torstenk on 07.03.16.
 */
public class MyTask<T> extends CompletableFuture<T> {
    private ConnectorMock conn;
    private int taskUid;
    private Function<MyTask<T>, T> retriever;

    private String status;
    private String resultUrl;

    public MyTask(ConnectorMock conn, int taskUid, Function<MyTask<T>, T> retriever) {
        this.conn = conn;
        this.taskUid = taskUid;
        this.retriever = retriever;

        this.status = "running";
    }

    public String fetchStatus() {
        if (status.equals("stopped")) {
            return status;
        }

        // status should be loaded from external service
        JsonNode json = conn.doGet("/task/" + taskUid);

        this.status = json.findPath("status").asText();
        if (status.equals("stopped")) {
            this.resultUrl = json.findPath("result").asText();
        }
        return status;
    }

    public int getTaskUid() {
        return this.taskUid;
    }

    public String getResultUrl() {
        return this.resultUrl;
    }

    public T getResult() {
        return retriever.apply(this);
    }
}
