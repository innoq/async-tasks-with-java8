import com.fasterxml.jackson.databind.JsonNode;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

/**
 * Created by torstenk on 07.03.16.
 */
public class APITask<T> extends CompletableFuture<T> {
    private ConnectorMock conn;
    private int taskUid;
    private String selfUri;
    private Function<APITask<T>, T> retriever;

    private String status;
    private String resultUrl;

    public APITask(ConnectorMock conn, JsonNode json, Function<APITask<T>, T> retriever) {
        this.conn = conn;
        this.taskUid = json.findPath("task").asInt();
        this.selfUri = json.findPath("self").asText();
        this.retriever = retriever;
        this.status = "running";
    }

    public String fetchStatus() {
        if (status.equals("stopped")) {
            return status;
        }

        JsonNode json = conn.doGet(this.selfUri);

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
