import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Random;

@Path("/form")
public class FormResource {
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
     * Creates a new form for a particular school
     */
    @Path("{id}")
    @POST
    public void newForm(@PathParam("id") int school_id) throws SQLException {
        openConnection();
        int form_id = newFormID();
        String query = "INSERT INTO form (form_id, school_id) VALUES (?, ?)";
        PreparedStatement st = db.prepareStatement(query);
        st.setInt(1, form_id);
        st.setInt(2, school_id);
        st.executeQuery();
        closeConnection();
    }

    /**
     * Sets the student-grade range of which the form can be accessed
     */
    @Path("{form_id}/{max_grade}")
    @POST
    public void setFormGrade(@PathParam("form_id") int form_id, @PathParam("max_grade") int max_grade) throws SQLException, Exception {
        openConnection();
        String query = "UPDATE form SET grade = ? WHERE form_id = ?";
        PreparedStatement st = db.prepareStatement(query);
        st.setInt(1, form_id);
        st.setInt(2, max_grade);
        st.executeQuery();
        closeConnection();
    }

    /**
     * Generates an ID for a new form
     */
    private int newFormID() throws SQLException {
        openConnection();
        String query = "SELECT MAX(form_id) FROM form";
        PreparedStatement st = db.prepareStatement(query);
        ResultSet rs = st.executeQuery();
        int maxID = 0;
        while (rs.next()) {
            maxID = rs.getInt(1);
        }
        closeConnection();
        return maxID + 1;
    }

    /**
     * Creates a question field of an input-type, for a particular form ID
     */
    @Path("/field/{form_id}/{question}/{input_type}")
    @POST
    public void createField(@PathParam("form_id") int id, @PathParam("question") String question, @PathParam("input_type") String type) throws SQLException {
        openConnection();
        String query = "INSERT INTO fields (form_id, question, input_type) VALUES (?, ?, ?)";
        PreparedStatement st = db.prepareStatement(query);
        st.setInt(1, id);
        st.setString(2, question);
        st.setString(3, type);
        st.executeQuery();
        closeConnection();
    }

    /**
     * Uploads basic registration into database
     */
    @Path("/uploadBasicReqNoAccount/{childname}/{guardianname}/{telephone1}/{telephone2}/{bsn}/{birthdate}/{grade}/{schoolname}/{address}")
    @POST
    public void uploadRegistrationNoAccount(@PathParam("childname") String childName,
                                            @PathParam("guardianname") String guardianName,
                                            @PathParam("telephone1") String telephone1,
                                            @PathParam("telephone2") String telephone2,
                                            @PathParam("bsn") String bsn,
                                            @PathParam("birthdate") Date birth_date,
                                            @PathParam("grade") int grade,
                                            @PathParam("schoolname") String schoolName,
                                            @PathParam("address") String address) throws Exception {
        openConnection();
        int student_id = newStudentID();
        int registration_id = newRegistrationID();
        int school_id = getSchoolID(schoolName);
        String email = registration_id + "@studieportal.nl";

        if (!accountExists(email)) { //if an account doesn't exist, make a new account entry
            createAccount(guardianName, telephone1, telephone2, email, address);
        } else { //if an account exists, update depending on the phone numbers given
            updateAccount(telephone1, telephone2, email);
        }
        if (!checkGuardianExists(email)) {
            createGuardian(email);
        }
        if (!studentExists(bsn)) {
            createStudent(childName, email, bsn, birth_date, student_id);
        }
        createRegistration(grade, student_id, registration_id, school_id);

        closeConnection();
    }

    /**
     * Uploads basic registration into database
     */
    @Path("/uploadBasicReq/{childname}/{guardianname}/{email}/{telephone1}/{telephone2}/{bsn}/{birthdate}/{grade}/{schoolname}/{address}")
    @POST
    public void uploadRegistration(@PathParam("childname") String childName,
                                   @PathParam("guardianname") String guardianName,
                                   @PathParam("email") String email,
                                   @PathParam("telephone1") String telephone1,
                                   @PathParam("telephone2") String telephone2,
                                   @PathParam("bsn") String bsn,
                                   @PathParam("birthdate") Date birth_date,
                                   @PathParam("grade") int grade,
                                   @PathParam("schoolname") String schoolName,
                                   @PathParam("address") String address) throws Exception {
        openConnection();
        int student_id = newStudentID();
        int registration_id = newRegistrationID();
        int school_id = getSchoolID(schoolName);

        if (!accountExists(email)) { //if an account doesn't exist, make a new account entry
            createAccount(guardianName, telephone1, telephone2, email, address);
        } else { //if an account exists, update depending on the phone numbers given
            updateAccount(telephone1, telephone2, email);
        }
        if (!checkGuardianExists(email)) {
            createGuardian(email);
        }
        if (!studentExists(bsn)) {
            createStudent(childName, email, bsn, birth_date, student_id);
        }
        createRegistration(grade, student_id, registration_id, school_id);

        closeConnection();
    }

    /**
     * Checks if a student already exists based on their bsn.
     * Used to reduce redundancy.
     */
    private boolean studentExists(String bsn) throws SQLException {
        openConnection();
        String query = "SELECT * FROM STUDENT WHERE bsn = ?";
        PreparedStatement st = db.prepareStatement(query);
        st.setString(1, bsn);
        ResultSet rs = st.executeQuery();
        closeConnection();
        if (rs.next()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Creates a child as a logged in parent
     */
    @Path("/createChild/{accountid}/{name}/{dob}/{bsn}")
    @POST
    @Produces(MediaType.TEXT_HTML)
    public void createChild(@PathParam("accountid") String accountid,
                            @PathParam("name") String childname,
                            @PathParam("dob") Date birth_date,
                            @PathParam("bsn") String bsn) throws Exception {
        openConnection();
        int student_id = newStudentID();
        createStudent(childname, accountid, bsn, birth_date, student_id);
        closeConnection();
    }


    /**
     * Creates new account table entry
     */
    private void createAccount(String guardianName, String telephone1, String telephone2, String email, String address) throws SQLException {
        openConnection();
        String account = "INSERT INTO account (account_id, name, address, phone_number_1, phone_number_2, role) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement acc = db.prepareStatement(account);
        acc.setString(1, email);
        acc.setString(2, guardianName);
        acc.setString(3, address);
        acc.setString(4, telephone1);
        acc.setString(5, telephone2);
        acc.setString(6, "G");
        acc.execute();
        closeConnection();
    }

    /**
     * Updates existing account table entry
     */
    private void updateAccount(String telephone1, String telephone2, String email) throws SQLException {
        openConnection();
        String account = "UPDATE account SET phone_number_1 = ?, phone_number_2 = ? WHERE account_id LIKE ?";
        PreparedStatement acc = db.prepareStatement(account);
        acc.setString(1, telephone1);
        acc.setString(2, telephone2);
        acc.setString(3, email);
        acc.execute();
        closeConnection();
    }

    /**
     * Creates new guardian table entry
     */
    private void createGuardian(String email) throws SQLException {
        openConnection();
        String guardian = "INSERT INTO guardian VALUES (?)";
        PreparedStatement guard = db.prepareStatement(guardian);
        guard.setString(1, email);
        guard.execute();
        closeConnection();
    }

    /**
     * Turns a string into a byte array for hashing
     */
    private static String stringToByteArray(byte[] hash) {
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

    /**
     * Hashes the BSN with SHA-256
     */
    private static String hashBSN(String bsn) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] byteBSN = digest.digest(bsn.getBytes(StandardCharsets.UTF_8));
        String hashedBSN = stringToByteArray(byteBSN);
        return hashedBSN;
    }

    /**
     * Creates new student entry
     */
    private void createStudent(String childName, String email, String bsn, Date birth_date, int student_id) throws SQLException, NoSuchAlgorithmException {
        openConnection();
        String student = "INSERT INTO student (student_id, bsn, name, birth_date, guardian_id) VALUES (?, ?, ?, ?, ?)";

        String hashedBSN = hashBSN(bsn);

        PreparedStatement st = db.prepareStatement(student);
        st.setInt(1, student_id);
        st.setString(2, hashedBSN);
        st.setString(3, childName);
        st.setDate(4, birth_date);
        st.setString(5, email);
        st.execute();
        closeConnection();
    }

    /**
     * Creates new registration table entry
     */
    private void createRegistration(int grade, int student_id, int registration_id, int school_id) throws SQLException {
        openConnection();
        String registration = "INSERT INTO registration (registration_id, grade, registration_date, student_id, school_id, status, allowedit) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement reg = db.prepareStatement(registration);
        reg.setInt(1, registration_id);
        reg.setInt(2, grade);
        reg.setDate(3, Date.valueOf(java.time.LocalDate.now()));
        reg.setInt(4, student_id);
        reg.setInt(5, school_id);
        reg.setString(6, "Under review");
        reg.setString(7, "N");
        reg.execute();
        closeConnection();
    }

    /**
     * Checks if a guardian already exists
     */
    private boolean checkGuardianExists(String email) throws SQLException {
        openConnection();
        String query = "SELECT * FROM guardian WHERE guardian_id = ?";
        PreparedStatement st = db.prepareStatement(query);
        st.setString(1, email);
        ResultSet rs = st.executeQuery();
        closeConnection();
        return rs.next();
    }

    /**
     * Checks if an account already exists
     */
    public boolean accountExists(String email) throws SQLException {
        openConnection();
        String query = "SELECT * FROM account WHERE account_id LIKE ?";
        PreparedStatement st = db.prepareStatement(query);
        st.setString(1, email);
        ResultSet rs = st.executeQuery();
        closeConnection();
        return rs.next();
    }

    /**
     * Creates new student ID
     */
    public int newStudentID() throws Exception {
        openConnection();
        String query = "SELECT MAX(student_id) FROM student;";
        PreparedStatement st = db.prepareStatement(query);
        ResultSet rs = st.executeQuery();
        int newID;

        // if there are no rows
        if (!rs.next()) {
            return 1;
        } else {
            newID = rs.getInt(1) + 1;
        }
        closeConnection();
        return newID;
    }

    /**
     * Creates new registration ID
     */
    public int newRegistrationID() throws Exception {
        openConnection();
        boolean foundID = false;
        ArrayList<Integer> takenIDs = new ArrayList<>();
        String query = "SELECT registration_id FROM registration";
        PreparedStatement st = db.prepareStatement(query);
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            takenIDs.add(rs.getInt(1));
        }
        int newID = 0;
        while (!foundID) {
            Random rnd = new Random();
            newID = rnd.nextInt(999999);
            if (!takenIDs.contains(newID)) {
                foundID = true;
            }
        }
        closeConnection();
        return newID;
    }

    /**
     * Deletes a form based on its form ID
     */
    @Path("{id}")
    @DELETE
    public void deleteForm(@PathParam("id") int form_id) throws SQLException {
        openConnection();
        String query = "DELETE FROM form WHERE form_id = ?";
        PreparedStatement st = db.prepareStatement(query);
        st.setInt(1, form_id);
        st.executeQuery();
        closeConnection();
    }

    /**
     * Deletes a field based on its form ID and the question of the field
     */
    @Path("{id}/{question}")
    @DELETE
    public void deleteField(@PathParam("id") int formID, @PathParam("question") String question) throws SQLException {
        openConnection();
        String query = "DELETE FROM fields WHERE form_id = ? AND question = ?";
        PreparedStatement st = db.prepareStatement(query);
        st.setInt(1, formID);
        st.setString(2, question);
        st.executeQuery();
        closeConnection();
    }

    /**
     * Gets school-specific form by taking in the school name and the grade
     */
    @Path("{schoolname}/{grade}")
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Form getForm(@PathParam("schoolname") String schoolname, @PathParam("grade") int grade) throws Exception {
        int id = getSchoolID(schoolname);
        Form form = new Form();

        String query = "SELECT grade FROM form WHERE school_id = ?";
        openConnection();
        PreparedStatement st = db.prepareStatement(query);
        st.setInt(1, id);
        ArrayList<Integer> formGrades = new ArrayList<>();
        ResultSet rs = st.executeQuery();
        closeConnection();
        while (rs.next()) {
            formGrades.add(rs.getInt(1));
        }

        int finalgrade = getFinalGrade(grade, formGrades);

        String query2 = "SELECT b.question, b.input_type, b.question_id FROM form a, fields b WHERE a.form_id = b.form_id AND a.school_id = ? AND a.grade = ?";
        openConnection();
        PreparedStatement st2 = db.prepareStatement(query2);
        st2.setInt(1, id);
        st2.setInt(2, finalgrade);
        ResultSet rs2 = st2.executeQuery();
        closeConnection();
        while (rs2.next()) {
            Form.Field question = new Form.Field();
            question.setQuestion(rs2.getString(1));
            question.setInput_type(rs2.getString(2));
            question.setQuestion_id(rs2.getInt(3));
            form.appendField(question);
        }

        String query3 = "SELECT a.form_id, a.school_id, b.school_name FROM form a, school b WHERE a.school_id = b.school_id AND a.school_id = ? AND a.grade = ?";
        openConnection();
        PreparedStatement st3 = db.prepareStatement(query3);
        st3.setInt(1, id);
        st3.setInt(2, finalgrade);
        ResultSet rs3 = st3.executeQuery();
        closeConnection();
        while (rs3.next()) {
            form.setForm_id(rs3.getInt(1));
            form.setGrade(grade);
            form.setSchool_id(rs3.getInt(2));
            form.setSchool_name(rs3.getString(3));
        }
        return form;
    }

    /**
     * For the range of grades applicable for a form
     */
    private static int getFinalGrade(int grade, ArrayList<Integer> gradeSet) {
        boolean check = false;
        while (!check) {
            for (int comparedGrade : gradeSet) {
                if (comparedGrade >= grade) {
                    check = true;
                    return comparedGrade;
                } else if (comparedGrade == 12) {
                    return 12;
                }
            }
        }
        return 12;
    }

    /**
     * Gets the school ID depending on the school name
     */
    public int getSchoolID(String schoolName) throws Exception {
        openConnection();
        String query = "SELECT school_id FROM school WHERE school_name LIKE ?";
        PreparedStatement st = db.prepareStatement(query);
        st.setString(1, schoolName);
        ResultSet rs = st.executeQuery();

        int schoolID;
        if (!rs.next()) {
            throw new Exception("No school found");
        } else {
            schoolID = rs.getInt(1);
        }
        closeConnection();
        return schoolID;
    }
}
