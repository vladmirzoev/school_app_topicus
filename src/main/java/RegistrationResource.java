import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Random;

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
            Class.forName("org.postgresql.Driver");
            db = DriverManager.getConnection(url, username, password);
        } catch (SQLException | ClassNotFoundException e) {
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
    @Produces(MediaType.TEXT_PLAIN)
    public String fetchGhostLogin(@PathParam("bsn") String bsn) throws SQLException, NoSuchAlgorithmException {
        String encodedBSN = hashBSN(bsn);
        String query = "SELECT registration.registration_id " +
                "FROM registration, student " +
                "WHERE student.student_id = registration.student_id " +
                "AND student.bsn LIKE ?";
        openConnection();
        PreparedStatement st = db.prepareStatement(query);
        st.setString(1, encodedBSN);
        ResultSet rs = st.executeQuery();
        closeConnection();
        int reg_id = -1;
        while (rs.next()) {
            reg_id = rs.getInt(1);
        }
        return reg_id + "@studieportal.nl" ;
    }

    /**
     * Returns student_id based on the bsn, returning a Registration JSON object instead
     */
    @Path("/fetchRegId/{bsn}")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Registration fetchRegIds(@PathParam("bsn") String bsn) throws SQLException, NoSuchAlgorithmException, InterruptedException {
        openConnection();
        String hashedBSN = hashBSN(bsn);
        Registration queriedStudent = new Registration();

        String query = "SELECT r.registration_id " +
                "FROM registration r, student s " +
                "WHERE r.student_id = s.student_id AND " +
                "s.bsn LIKE ?";
        PreparedStatement st = db.prepareStatement(query);
        st.setString(1, hashedBSN);
        ResultSet rs = st.executeQuery();

        while (rs.next()) {
            queriedStudent.setRegistration_id(rs.getInt(1));
        }
        closeConnection();
        return queriedStudent;
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

    @Path("editResponse/{reg_id}/{question_id}/{newResponse}")
    @POST
    public void editResponse(@PathParam("reg_id") int r_id,
                             @PathParam("question_id") int q_id,
                             @PathParam("newResponse") String newResponse) throws SQLException{
        openConnection();
        String query = "UPDATE responses SET response = ? WHERE registration_id = ? AND question_id = ?";
        PreparedStatement st = db.prepareStatement(query);
        st.setString(1, newResponse);
        st.setInt(2, r_id);
        st.setInt(3, q_id);
        st.executeQuery();

        closeConnection();
    }
}
