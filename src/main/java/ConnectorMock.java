import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Date;

/**
 * Created by torstenk on 07.03.16.
 */
public class ConnectorMock {
    private int taskUid;

    public JsonNode doPostWithJson(String path, String userData) {
        System.out.println(new Date() + " --> Sending HTTP Post Request to " + path + " with JSON data " + userData);

        String response = "200 OK";
        generateTaskUid();
        System.out.println(new Date() + " <-- Receiving \"" + response + "\" and taskUid " + this.taskUid);

        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readTree("{\"task\":\"" + this.taskUid + "\",\"self\":\"/tasks/" + this.taskUid + "\"}");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void generateTaskUid() {
        this.taskUid = (int) ((Math.random() + 1) * 100);
    }

    public JsonNode doGet(String path) {
        ObjectMapper mapper = new ObjectMapper();
        if ((Math.random() * 10 + 1) > 8) {
            try {
                return mapper.readTree("{\"status\":\"stopped\", \"result\":\"/users/" + Math.random()*1000 + "\"}");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            return mapper.readTree("{\"status\":\"running\"}");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
