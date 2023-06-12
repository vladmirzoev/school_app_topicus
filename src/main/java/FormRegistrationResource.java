import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.sql.*;

@Path("/form")
public class FormRegistrationResource {
    String host = "bronto.ewi.utwente.nl";
    String dbName = "dab_di22232b_81";
    String url = "jdbc:postgresql://" + host + ":5432/" + dbName + "?currentSchema=TopicusDatabase";
    String username = "dab_di22232b_81";
    String password = "uZQ2Mqk82/Kx6s5l";
    Connection db = null;

//    /**
//     * Creates a form within the database, with its own form ID
//     */
//    @POST
//    @Consumes(MediaType.APPLICATION_JSON)
//    public void createForm(Form form) throws SQLException, Exception {
//        establishConnection();
//        int form_id = newFormID();
//        String query = "INSERT INTO form (form_id, grade, school_id) VALUES (?, ?, ?)";
//        PreparedStatement st = db.prepareStatement(query);
//        st.setInt(1, form_id);
//        st.setInt(2, ); //TODO get the grade as a parameter; done by the school admin while making the form
//        st.setInt(3, ); //TODO get the current school id from the admins current session
//        st.executeQuery();
//    }
//
//    /**
//     * Creates a field for a particular form ID
//     */
//    @POST
//    public void createField() throws SQLException {
//        establishConnection();
//        String query = "INSERT INTO fields (form_id, question, input_type) VALUES (?, ?, ?)";
//        PreparedStatement st = db.prepareStatement(query);
//        st.setInt(1, );
//        st.setString(2, ); //TODO get the question parameter from the form creation by school admin
//        st.setString(3, ); //TODO get the type parameter from the form creation by school admin
//    }

    /**
     * Parent registering their child to a school
     */
    @Path("/uploadBasicReq")
    @POST
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void uploadRegistration(@FormParam("childName") String childName,
                                   @FormParam("guardianName") String guardianName,
                                   @FormParam("telephone1") String telephone1,
                                   @FormParam("telephone2") String telephone2,
                                   @FormParam("email") String email,
                                   @FormParam("bsn") String bsn,
                                   @FormParam("birth_date") Date birth_date,
                                   @FormParam("grade") int grade,
                                   @FormParam("schoolName") String schoolName,
                                   @FormParam("address") String address) throws Exception {
        establishConnection();
        int student_id = newStudentID();
        int registration_id = newRegistrationID();
        int school_id = getSchoolID(schoolName);

        if (!accountExists(email)) { //if an account doesnt exist, make a new account entry
            String account = "INSERT INTO account (account_id, name, address, phone_number_1, phone_number_2) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement acc = db.prepareStatement(account);
            acc.setString(1, email);
            acc.setString(2, guardianName);
            acc.setString(3, address);
            acc.setString(4, telephone1);
            acc.setString(5, telephone2);
            acc.execute();
        } else { //if an account exists, update depending on the phone numbers given
            String account = "UPDATE account SET phone_number_1 = ?, phone_number_2 = ? WHERE account_id = ?";
            PreparedStatement acc = db.prepareStatement(account);
            acc.setString(1, telephone1);
            acc.setString(2, telephone2);
            acc.setString(3, email);
            acc.execute();
        }

        if (!checkGuardianExists(email)) {
            String guardian = "INSERT INTO guardian VALUES (?)";
            PreparedStatement guard = db.prepareStatement(guardian);
            guard.setString(1, email);
            guard.execute();
        }

        String student = "INSERT INTO student (student_id, bsn, name, birth_date, guardian_id) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement st = db.prepareStatement(student);
        st.setInt(1, student_id);
        st.setString(2, bsn);
        st.setString(3, childName);
        st.setDate(4, birth_date);
        st.setString(5, email);
        st.execute();

        String registration = "INSERT INTO registration (registration_id, grade, registration_date, student_id, school_id, status) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement reg = db.prepareStatement(registration);
        reg.setInt(1, registration_id);
        reg.setInt(2, grade);
        reg.setDate(3, Date.valueOf(java.time.LocalDate.now()));
        reg.setInt(4, student_id);
        reg.setInt(5, school_id);
        reg.setString(6, "Under review");
        reg.execute();

    }

    private boolean checkGuardianExists(String email) throws SQLException {
        String query = "SELECT * FROM guardian WHERE guardian_id = ?";
        PreparedStatement st = db.prepareStatement(query);
        st.setString(1, email);
        ResultSet rs = st.executeQuery();
        return rs.next();
    }

    public void establishConnection() {
        try {
            db = DriverManager.getConnection(url, username, password);
        } catch (
                SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean accountExists(String email) throws SQLException {
        String query = "SELECT * FROM account WHERE account_id = ?";
        PreparedStatement st = db.prepareStatement(query);
        st.setString(1, email);
        ResultSet rs = st.executeQuery();
        return rs.next();
    }

    /**
     * Create a form id by getting the max(form_id) + 1
     */
    public int newFormID() throws Exception {
        String query = "SELECT MAX(form_id) FROM form;";
        PreparedStatement st = db.prepareStatement(query);
        ResultSet rs = st.executeQuery();
        int newID = -1;

        // if there are no rows
        if (!rs.next()) {
            return 1;
        }
        return newID;
    }

    /**
     * Create a student id by getting the max(student_id) + 1
     */
    public int newStudentID() throws Exception {
        String query = "SELECT MAX(student_id) FROM student;";
        PreparedStatement st = db.prepareStatement(query);
        ResultSet rs = st.executeQuery();
        int newID = -1;

        // if there are no rows
        if (!rs.next()) {
            return 1;
        } else {
            newID = rs.getInt(1) + 1;
        }
        return newID;
    }

    /**
     * Create a registration id by getting the max(registration_id) + 1
     */
    public int newRegistrationID() throws Exception {
        String query = "SELECT MAX(registration_id) FROM registration";
        PreparedStatement st = db.prepareStatement(query);
        ResultSet rs = st.executeQuery();
        int newID = -1;

        // if there are no rows
        if (!rs.next()) {
            return 1;
        } else {
            newID = rs.getInt(1) + 1;
        }
        return newID;
    }

    /**
     * Gets the school id from a particular school name
     */
    public int getSchoolID(String schoolName) throws Exception {
        String query = "SELECT school_id FROM school WHERE school_name LIKE ?";
        PreparedStatement st = db.prepareStatement(query);
        st.setString(1, schoolName);
        ResultSet rs = st.executeQuery();

        int schoolID = -1;
        if (!rs.next()) {
            throw new Exception("No school found");
        } else {
            schoolID = rs.getInt(1);
        }
        return schoolID;
    }
}
