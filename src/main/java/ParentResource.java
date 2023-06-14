import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.*;
import java.util.ArrayList;


@Path("/parent")
public class ParentResource {

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
    public ArrayList<Parent> getGuardians() throws SQLException {
        ArrayList<Parent> guardianlist = new ArrayList<>();
        establishConnection();

        String query = "SELECT account_id, name FROM account WHERE role = 'G'";
        PreparedStatement st = db.prepareStatement(query);
        ResultSet rs = st.executeQuery();

        while (rs.next()) {
            Parent queriedParent = new Parent();
            queriedParent.setName(rs.getString(2));
            queriedParent.setId(rs.getString(1));
            guardianlist.add(queriedParent);
        }
        return guardianlist;
    }

    @Path("{id}")
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Parent findParent(@PathParam("id") String id) throws Exception {
        establishConnection();
        String query = "SELECT account_id, name, address, phone_number_1 FROM account WHERE account_id = ?";
        PreparedStatement st = db.prepareStatement(query);
        st.setString(1, id);
        ResultSet rs = st.executeQuery();

        Parent queriedParent = new Parent();
        while (rs.next()) {
            queriedParent.setId(rs.getString(1));
            queriedParent.setName(rs.getString(2));
            queriedParent.setAddress(rs.getString(3));
            queriedParent.setPhone_1(rs.getString(4));


        }
        return queriedParent;
    }
}
