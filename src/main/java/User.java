import com.fasterxml.jackson.databind.JsonNode;

/**
 * Created by torstenk on 07.03.16.
 */
public class User {

    public User(JsonNode json) {
        // parsing json and return new user object
    }

    public static User fromServiceResponse(JsonNode json) {
        return new User(json);
    }

    // ...
}
