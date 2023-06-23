import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.sql.*;
import java.util.ArrayList;

@Path("/school")
public class SchoolResource {
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

    /**
     * Gets all schools in the database
     */
    @Path("/getschools")
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public ArrayList<School> getSchools() throws SQLException {
        ArrayList<School> schoolList = new ArrayList<>();
        openConnection();

        String query = "SELECT school_id, school_name FROM school";
        PreparedStatement st = db.prepareStatement(query);
        ResultSet rs = st.executeQuery();

        while (rs.next()) {
            School queriedSchool = new School();
            queriedSchool.setSchool_id(rs.getInt(1));
            queriedSchool.setSchool_name(rs.getString(2));
            schoolList.add(queriedSchool);
        }
        closeConnection();
        return schoolList;
    }

    /**
     * Gets a specific school depending on their school ID
     */
    @Path("{id}")
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public School findSchool(@PathParam("id") String id) throws Exception {
        openConnection();
        String query = "SELECT school_id, school_name, address, tuition, contact_number FROM school WHERE school_id = ?";
        PreparedStatement st = db.prepareStatement(query);
        st.setString(1, id);
        ResultSet rs = st.executeQuery();

        School queriedSchool = new School();
        while (rs.next()) {
            queriedSchool.setSchool_id(rs.getInt(1));
            queriedSchool.setSchool_name(rs.getString(2));
            queriedSchool.setAddress(rs.getString(3));
            queriedSchool.setTuition(rs.getInt(4));
            queriedSchool.setContact_number(rs.getString(5));
        }
        closeConnection();
        return queriedSchool;
    }
}
