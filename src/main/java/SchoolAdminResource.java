import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


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
            Class.forName("org.postgresql.Driver");
            db = DriverManager.getConnection(url, username, password);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void closeConnection() throws SQLException {
        db.close();
    }

//    @Path("/getStudent/{id}")
//    @GET
//    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
//    public Student getStudent(@PathParam("id") String studentID) throws Exception{
//        openConnection();
//        String query = "SELECT r.registration_id, r.grade, r.registration_date, r.student_id, r.school_id, r.status FROM student s, registration r, schooladmin sa WHERE s.student_id = r.student_id AND r.school_id = sa.school_id AND r.status = 'accepted' AND s.student_id = ?";
//        PreparedStatement st = db.prepareStatement(query);
//        st.setString(1, studentID);
//        ResultSet result = st.executeQuery();
//
//        Student queriedStudent = new Student();
//        while (result.next()) {
//            queriedStudent.setId(result.getInt(1));
//            queriedStudent.setBsn(result.getString(2));
//            queriedStudent.setName(result.getString(3));
//            queriedStudent.setBirthdate(String.valueOf(result.getDate(4)));
//            queriedStudent.setGuardian_id(result.getString(5));
//        }
//        closeConnection();
//        return queriedStudent;
//    }

    @Path("/getAllStudents/{id}")
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Registration> getAllStudents(@PathParam("id") String adminID) throws Exception {
        openConnection();
        List<Registration> regs = new ArrayList<>();
        String query = "SELECT r.registration_id, r.grade, r.registration_date, r.student_id, s.name, sa.school_id, r.status, r.allowedit FROM registration r, schooladmin sa, student s WHERE s.student_id = r.student_id AND (SELECT sa.school_id FROM schooladmin sa WHERE sa.account_id LIKE ? ) = r.school_id";
        PreparedStatement st = db.prepareStatement(query);
        st.setString(1, adminID);
        ResultSet result = st.executeQuery();

        while (result.next()) {
            Registration queriedReg = new Registration();
            queriedReg.setId(result.getInt(1));
            queriedReg.setGrade(result.getInt(2));
            queriedReg.setRegistration_date(String.valueOf(result.getDate(3)));
            queriedReg.setStudent_id(result.getInt(4));
            queriedReg.setName(result.getString(5));
            queriedReg.setSchool_id(result.getInt(6));
            queriedReg.setStatus(result.getString(7));
            queriedReg.setAllowedit(result.getString(8));
            regs.add(queriedReg);
        }
        closeConnection();
        return regs;
    }

    @Path("/updatestatus/{id}/{status}")
    @POST
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void updateStatus(@PathParam("id") int studentID, @PathParam("status") String status) throws Exception {
        openConnection();
        String query = "UPDATE registration SET status = ? WHERE student_id = CAST(? AS int)";
        PreparedStatement st = db.prepareStatement(query);
        st.setString(1, status);
        st.setInt(2, studentID);
        st.executeQuery();
        closeConnection();
    }

    @Path("/updateedit/{id}/{edit}")
    @POST
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void updateEdit(@PathParam("id") int studentID, @PathParam("edit") String edit) throws Exception {
        openConnection();
        String query = "UPDATE registration SET allowedit = ? WHERE student_id = CAST(? AS int)";
        PreparedStatement st = db.prepareStatement(query);
        st.setString(1, edit);
        st.setInt(2, studentID);
        st.executeQuery();
        closeConnection();
    }
    
    @Path("/deleteregistration/{id}")
    @DELETE
    public void deleteRegistration(@PathParam("id") int studentID) throws Exception{
        openConnection();
        String query = "DELETE FROM registration WHERE student_id = CAST(? AS int)";
        PreparedStatement st = db.prepareStatement(query);
        st.setInt(1,studentID);
        st.executeQuery();
        closeConnection();
    }

}
