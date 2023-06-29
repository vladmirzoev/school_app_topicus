import jakarta.ws.rs.Path;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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

//    @Path("/sendresponse/{registration_id}/{question_id}/{response}")
}
