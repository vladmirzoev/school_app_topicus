import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;


import java.sql.*;
import java.util.ArrayList;


@Path("/parent")
public class ParentResource {

    String host = "bronto.ewi.utwente.nl";
    String dbName = "dab_di22232b_81";
    String url = "jdbc:postgresql://" + host + ":5432/" + dbName + "?currentSchema=TopicusDatabase";
    String username = "dab_di22232b_81";
    String password = "uZQ2Mqk82/Kx6s5l";
    Connection db = null;

    public void openConnection() {
        try {
            db = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void closeConnection() throws SQLException {
        db.close();
    }

    @Path("{gid}")
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Student getChildren(@PathParam("gid") String gid) throws Exception{
        openConnection();
        String query = "SELECT s.student_id, s.bsn, s.name, s.birth_date, s.guardian_id FROM STUDENT s, Guardian g WHERE g.guardian_id = s.guardian_id AND g.guardian_id = ?";
        PreparedStatement st = db.prepareStatement(query);
        st.setString(1, gid);
        ResultSet result = st.executeQuery();

        Student queriedStudent = new Student();
        while (result.next()) {
            queriedStudent.setId(result.getInt(1));
            queriedStudent.setBsn(result.getString(2));
            queriedStudent.setName(result.getString(3));
            queriedStudent.setBirthdate(String.valueOf(result.getDate(4)));
            queriedStudent.setGuardian_id(result.getString(5));
        }
        closeConnection();
        return queriedStudent;

    }

//    @Path("{messages}")
//    @GET
//    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
//    public Messages retrieveMessages() throws exception{
//
//    }
//
//    @Path("{messages}")
//    @POST
//    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
//    public Messages sendMessages() throws exception{
//
//    }




    /**
     * Gets all parents in the system
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<Parent> getGuardians() throws SQLException {
        ArrayList<Parent> guardianlist = new ArrayList<>();
        openConnection();

        String query = "SELECT account_id, name FROM account WHERE role = 'G'";
        PreparedStatement st = db.prepareStatement(query);
        ResultSet rs = st.executeQuery();

        while (rs.next()) {
            Parent queriedParent = new Parent();
            queriedParent.setName(rs.getString(2));
            queriedParent.setId(rs.getString(1));
            guardianlist.add(queriedParent);
        }
        closeConnection();
        return guardianlist;
    }

    /**
     * Gets specific parent depending on their account ID
     */
    @Path("{id}")
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Parent findParent(@PathParam("id") String id) throws Exception {
        openConnection();
        String query = "SELECT account_id, name, address, phone_number_1 FROM account WHERE account_id LIKE ?";
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
        closeConnection();
        return queriedParent;
    }
}
