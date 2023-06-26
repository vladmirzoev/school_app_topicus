import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
     * Gets all registrations regardless of school
     */
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public ArrayList<Registration> getRegistrations() throws Exception {
        ArrayList<Registration> registrationlist = new ArrayList<>();
        openConnection();

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
        closeConnection();
        return registrationlist;
    }

    //TODO fetch all registrations of a specific school

    /**
     * Gets a specific registration depending on their student_id
     */
    @Path("{id}")
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Registration findRegistration(@PathParam("id") int id) throws Exception {
        openConnection();

        String query = "SELECT a.registration_id, a.grade, a.registration_date, a.student_id, a.school_id, a.status, b.name FROM registration a, student b WHERE a.student_id = b.student_id AND a.registration_id = ?";
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
        closeConnection();
        return registration;
    }

    /**
     * Gets all registrations relevant to the guardian
     */
    @Path("/fetchregistrations/{email}")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public ArrayList<Registration> fetchRegistrations(@PathParam("email") String email) throws SQLException {
        ArrayList<Registration> queriedRegistrations = new ArrayList<>();
        openConnection();
        String query = "SELECT r.registration_id, r.grade, r.registration_date, r.student_id, s.name, r.school_id, r.status, r.allowedit " +
                "FROM registration r, student s" +
                "WHERE s.guardian_id = ?";
        PreparedStatement st = db.prepareStatement(query);
        st.setString(1, email);
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            Registration current = new Registration();
            current.setId(rs.getInt(1));
            current.setGrade(rs.getInt(2));
            current.setRegistration_date(String.valueOf(rs.getDate(3)));
            current.setStudent_id(rs.getInt(4));
            current.setName(rs.getString(5));
            current.setSchool_id(rs.getInt(6));
            current.setStatus(rs.getString(7));
            current.setAllowedit(rs.getString(8));
            queriedRegistrations.add(current);
        }

        closeConnection();
        return queriedRegistrations;
    }

    /**
     * Returns registration_id of specific registration for a ghost account
     */
    @Path("/fetchregistrationsghost/{bsn}")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public int fetchRegistrationID(@PathParam("bsn") String bsn) throws SQLException, NoSuchAlgorithmException {
        String encodedBSN = hashBSN(bsn);
        String query = "SELECT r.registration_id" +
                "FROM registration r, student s" +
                "WHERE s.student_id = r.student_id AND s.bsn = ?";
        PreparedStatement st = db.prepareStatement(query);
        st.setString(1, encodedBSN);
        ResultSet rs = st.executeQuery();
        int reg_id = -1;
        while (rs.next()) {
            reg_id = rs.getInt(1);
        }
        return reg_id;
    }

    private static String hashBSN(String bsn) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] byteBSN = digest.digest(bsn.getBytes(StandardCharsets.UTF_8));
        String hashedBSN = hashLoginPass(byteBSN);
        return hashedBSN;
    }

    private static String hashLoginPass(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
