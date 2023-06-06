import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.*;

@Path("/login.html/{id}")
public class StudentResource {

    String host = "bronto.ewi.utwente.nl";
    String dbName = "dab_di22232b_81";
    String url = "jdbc:postgresql://" + host + ":5432/" +
            dbName + "?currentSchema=TopicusDatabase";
    String username = "dab_di22232b_81";
    String password = "uZQ2Mqk82/Kx6s5l";
    Connection db = null;

    public void establishConnection() {
        try {
            db = DriverManager.getConnection(url, username,
                    password);
        } catch (
                SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public JSONArray findStudent(@PathParam("id") int id) throws Exception {
        establishConnection();
        String query = "SELECT * FROM student WHERE student_id = ?";
        PreparedStatement st = db.prepareStatement(query);
        st.setString(1, String.valueOf(id));
        ResultSet resultSet = st.executeQuery();
        return convert(resultSet);
    }

    public static JSONArray convert(ResultSet resultSet) throws Exception {

        JSONArray jsonArray = new JSONArray();

        while (resultSet.next()) {

            int columns = resultSet.getMetaData().getColumnCount();
            JSONObject obj = new JSONObject();

            for (int i = 0; i < columns; i++)
                obj.put(resultSet.getMetaData().getColumnLabel(i + 1).toLowerCase(), resultSet.getObject(i + 1));

            jsonArray.put(obj);
        }
        return jsonArray;
    }
}

