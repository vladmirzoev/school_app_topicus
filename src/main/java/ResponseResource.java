import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;

@Path("/response")
public class ResponseResource {
    String host = "bronto.ewi.utwente.nl";
    String dbName = "dab_di22232b_81";
    String url = "jdbc:postgresql://" + host + ":5432/" + dbName + "?currentSchema=TopicusDatabase";
    String username = "dab_di22232b_81";
    String password = "uZQ2Mqk82/Kx6s5l";
    Connection db = null;

    public void openConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            db = DriverManager.getConnection(url, username, password);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void closeConnection() throws SQLException {
        db.close();
    }

    @Path("/sendresponse/{registration_id}/{question_id}/{response}")
    @POST
    public void sendResponse(@PathParam("registration_id") int registration_id,
                             @PathParam("question_id") int question_id,
                             @PathParam("response") String response) throws SQLException {
        String cmd = "INSERT INTO responses (response, registration_id, question_id) VALUES (?, ?, ?)";
        openConnection();
        PreparedStatement st = db.prepareStatement(cmd);
        st.setString(1, response);
        st.setInt(2, registration_id);
        st.setInt(3, question_id);
        st.execute();
        closeConnection();
    }
}
