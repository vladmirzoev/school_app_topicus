import jakarta.ws.rs.*;
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
     * Gets all registered students regardless of school
     */
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public ArrayList<Student> getStudents() throws Exception {
        ArrayList<Student> studentList = new ArrayList<>();
        openConnection();

        String query = "SELECT student_id, name FROM student ";
        PreparedStatement st = db.prepareStatement(query);
        ResultSet rs = st.executeQuery();

        while (rs.next()) {
            Student queriedStudent = new Student();
            queriedStudent.setId(rs.getInt(1));
            queriedStudent.setName(rs.getString(2));
            studentList.add(queriedStudent);
        }
        closeConnection();
        return studentList;
    }

    //TODO fetch all students under a particular school id

    /**
     * Fetches a student of a particular student id
     */
    @Path("{id}")
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Student findStudent(@PathParam("id") int id) throws Exception {
        openConnection();

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
        closeConnection();
        return queriedStudent;
    }

    @Path("editName/{id}/{name}")
    @POST
    public void editChildName(@PathParam("id") int s_id, @PathParam("name") String name) throws SQLException {
        openConnection();
        String query = "UPDATE student SET name = ? WHERE student_id = ?";
        PreparedStatement st = db.prepareStatement(query);
        st.setString(1,name);
        st.setInt(2, s_id);
        st.executeQuery();
        closeConnection();
    }

    @Path("editBirthDate/{id}/{birth_date}")
    @POST
    public void editBirthDate(@PathParam("id") int id, @PathParam("birth_date") Date birth_date) throws SQLException {
        openConnection();
        String birthdate = String.valueOf(birth_date);
        String query = "UPDATE TABLE student SET birth_date = ? WHERE student_id = ?";
        PreparedStatement st = db.prepareStatement(query);
        st.setString(1, birthdate);
        st.setInt(2, id);
        st.executeQuery();
        closeConnection();
    }

}

