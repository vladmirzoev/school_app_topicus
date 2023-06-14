import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.sql.*;
import java.util.ArrayList;

@Path("/registration")
public class RegistrationResource {

    String host = "bronto.ewi.utwente.nl";
    String dbName = "dab_di22232b_81";
    String url = "jdbc:postgresql://" + host + ":5432/" + dbName + "?currentSchema=TopicusDatabase";
    String username = "dab_di22232b_81";
    String password = "uZQ2Mqk82/Kx6s5l";
    Connection db = null;

    public void establishConnection() {
        try {
            db = DriverManager.getConnection(url, username, password);
        } catch (
                SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public ArrayList<Registration> findStudent() throws Exception {
        ArrayList<Registration> registrationlist = new ArrayList<>();
        establishConnection();

        String query = "SELECT a.*, b.name FROM registration a, student b WHERE a.student_id = b.student_id";
        PreparedStatement st = db.prepareStatement(query);
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            Registration registration = new Registration();
            registration.setId(rs.getInt(1));
            registration.setGrade(rs.getInt(2));
            registration.setRegistration_date(rs.getDate(3).toString());
            registration.setStudent_id(rs.getInt(4));
            registration.setSchool_id(rs.getInt(5));
            registration.setStatus(rs.getString(6));
            registration.setName(rs.getString(7));
            registrationlist.add(registration);
        }
        return registrationlist;
    }

    @Path("{id}")
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Registration findStudent(@PathParam("id") int id) throws Exception {
        establishConnection();

        String query = "SELECT a.registration_id, a.grade, a.registration_date, a.student_id, a.school_id, a.status, b.name " +
                "FROM registration a, student b WHERE a.student_id = b.student_id AND a.registration_id = ?";
        PreparedStatement st = db.prepareStatement(query);
        st.setInt(1, id);
        ResultSet rs = st.executeQuery();

        Registration registration = new Registration();
        while (rs.next()) {
            registration.setId(rs.getInt(1));
            registration.setGrade(rs.getInt(2));
            registration.setRegistration_date(rs.getDate(3).toString());
            registration.setStudent_id(rs.getInt(4));
            registration.setSchool_id(rs.getInt(5));
            registration.setStatus(rs.getString(6));
            registration.setName(rs.getString(7));
        }
        return registration;

    }
}
