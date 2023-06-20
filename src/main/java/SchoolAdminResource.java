import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.sql.*;


@Path("/schooladmin")
public class SchoolAdminResource {

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

    @Path("{id}")
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Student getStudent(@PathParam("id") String studentID) throws Exception{
        openConnection();
        String query = "SELECT s.student_id, s.bsn, s.name, s.birth_date, s.guardian_id FROM student s, registration r, schooladmin sa WHERE s.student_id = r.student_id AND r.school_id = sa.school_id AND r.status = 'accepted' AND s.student_id = ?";
        PreparedStatement st = db.prepareStatement(query);
        st.setString(1, studentID);
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

    @Path("{school_id}")
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Student getAllStudents(@PathParam("school_id") String schoolID) throws Exception{
        openConnection();
        String query = "SELECT s.student_id, s.bsn, s.name, s.birth_date, s.guardian_id FROM student s, registration r, schooladmin sa WHERE s.student_id = r.student_id AND r.school_id = sa.school_id AND r.status = 'accepted' AND sa.school_id = ?";
        PreparedStatement st = db.prepareStatement(query);
        st.setString(1, schoolID);
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



}
