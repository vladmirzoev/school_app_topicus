import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.print.attribute.standard.Media;
import java.sql.*;

@Path("/student")
public class StudentResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String test() {
        return "Hello! This is a test";
    }

    String host = "bronto.ewi.utwente.nl";
    String dbName = "dab_di22232b_81";
    String url = "jdbc:postgresql://" + host + ":5432/" +
            dbName + "?currentSchema=topicusdatabase";
    String username = "dab_di22232b_81";
    String password = "uZQ2Mqk82/Kx6s5l";
    Connection db;

    public void establishConnection() {
        try {
            db = DriverManager.getConnection(url, username,
                    password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Student findStudent(@PathParam("id") int id) throws Exception {
        establishConnection();
        String query = "SELECT * FROM student WHERE student_id = ?";
        PreparedStatement st = db.prepareStatement(query);
        st.setInt(1, id);
        ResultSet resultSet = st.executeQuery();

        int queriedId = resultSet.getInt(1);
        String queriedBsn = resultSet.getString(2);
        String queriedName = resultSet.getString(3);
        String queriedBirthDate = resultSet.getString(4);
        int queriedGuardian_id = resultSet.getInt(5);

        Student queriedStudent = new Student(queriedId, queriedBsn, queriedName, queriedBirthDate, queriedGuardian_id);
        return queriedStudent;
    }
}

