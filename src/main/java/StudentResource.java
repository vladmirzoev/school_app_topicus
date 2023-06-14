import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.sql.*;
import java.util.ArrayList;

@Path("/student")
public class StudentResource {

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
    public ArrayList<Student> findStudent() throws Exception {
        ArrayList<Student> studentlist = new ArrayList<>();
        establishConnection();

        String query = "SELECT student_id, name FROM student ";
        PreparedStatement st = db.prepareStatement(query);
        ResultSet rs = st.executeQuery();

        while (rs.next()) {
            Student queriedStudent = new Student();
            queriedStudent.setId(rs.getInt(1));
            queriedStudent.setName(rs.getString(2));
            studentlist.add(queriedStudent);
        }
        return studentlist;
    }

    @Path("{id}")
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Student findStudent(@PathParam("id") int id) throws Exception {
        establishConnection();

        String query = "SELECT * FROM student WHERE student_id = ?";
        PreparedStatement st = db.prepareStatement(query);
        st.setInt(1, id);
        ResultSet rs = st.executeQuery();

        Student queriedStudent = new Student();
        while (rs.next()) {
            queriedStudent.setId(rs.getInt(1));
            queriedStudent.setBsn(rs.getString(2));
            queriedStudent.setName(rs.getString(3));
            queriedStudent.setBirthdate(String.valueOf(rs.getDate(4)));
            queriedStudent.setGuardian_id(rs.getString(5));
        }
        return queriedStudent;
//
//        int queriedId = -1;
//        String queriedBsn = null;
//        String queriedName = null;
//        String queriedBirthDate = null;
//        int queriedGuardian_id = -1;
//
//        while (rs.next()) {
//            queriedId = rs.getInt(1);
//            queriedBsn = rs.getString(2);
//            queriedName = rs.getString(3);
//            queriedBirthDate = String.valueOf(rs.getDate(4));
//            queriedGuardian_id = rs.getInt(5);
//        }

//        return new Student(queriedId, queriedBsn, queriedName, queriedBirthDate, queriedGuardian_id);
    }
}

