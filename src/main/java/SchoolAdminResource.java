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

    /**
     * Gets all registrations concerning the school
     */
    @Path("/getschoolregistrations/{id}")
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Registration> getSchoolRegistrations(@PathParam("id") String adminID) throws Exception {
        openConnection();
        List<Registration> regs = new ArrayList<>();

        String query = "SELECT DISTINCT r.registration_id, r.grade, r.registration_date, r.student_id, s.name, r.school_id, r.status, r.allowedit " +
                "FROM registration r, schooladmin sa, student s " +
                "WHERE r.student_id = s.student_id AND sa.school_id = r.school_id AND sa.account_id = ? ORDER BY s.name";
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

    /**
     * Gets all registrations concerning the school ordered
     */
    @Path("/getschoolregistrations/{id}/{column}/{asc_dsc}")
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Registration> getSchoolRegistrationsOrdered(@PathParam("id") String adminID, @PathParam("column") String column, @PathParam("asc_dsc") String asc_dsc) throws Exception {
        String query = "SELECT r.registration_id, r.grade, r.registration_date, r.student_id, s.name, r.school_id, r.status, r.allowedit " +
                "FROM registration r, schooladmin sa, student s " +
                "WHERE r.student_id = s.student_id AND sa.school_id = r.school_id AND sa.account_id = ? ";
        openConnection();
        List<Registration> regs = new ArrayList<>();
        query+= " ORDER BY " + column + " " + asc_dsc.toUpperCase();
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

    /**
     * Gets all registrations of a specific parent id
     */
    @Path("/getparentstudents/{id}")
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Registration> getParentStudents(@PathParam("id") String parentid) throws Exception {
        openConnection();
        List<Registration> regs = new ArrayList<>();
        String query = "SELECT DISTINCT r.registration_id, r.grade, r.registration_date, r.student_id, s.name, r.school_id, r.status, r.allowedit" +
                " FROM registration r, guardian g, student s " +
                "WHERE r.student_id = s.student_id AND g.guardian_id = s.guardian_id AND g.guardian_id = ?";
        PreparedStatement st = db.prepareStatement(query);
        st.setString(1, parentid);
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

    /**
     * updates the registration status depending on student id
     */
    @Path("/updatestatus/{id}/{status}")
    @POST
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void updateStatus(@PathParam("id") int studentID, @PathParam("status") String status) throws Exception {
        openConnection();
        String query = "UPDATE registration SET status = ? WHERE student_id = CAST(? AS int)";
        PreparedStatement st = db.prepareStatement(query);
        st.setString(1, status);
        st.setInt(2, studentID);
        st.executeUpdate();
        closeConnection();
    }

    /**
     * updates allowedit privilege based on student id
     */
    @Path("/updateedit/{id}/{edit}")
    @POST
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void updateEdit(@PathParam("id") int studentID, @PathParam("edit") String edit) throws Exception {
        openConnection();
        String query = "UPDATE registration SET allowedit = ? WHERE student_id = CAST(? AS int)";
        PreparedStatement st = db.prepareStatement(query);
        st.setString(1, edit);
        st.setInt(2, studentID);
        st.executeUpdate();
        closeConnection();
    }

    /**
     * deletes a registration based on student id
     */
    @Path("/deleteregistration/{id}")
    @DELETE
    public void deleteRegistration(@PathParam("id") int studentID) throws Exception {
        openConnection();
        String query = "DELETE FROM registration WHERE student_id = ?";
        PreparedStatement st = db.prepareStatement(query);
        st.setInt(1, studentID);
        st.execute();
        closeConnection();
    }

}