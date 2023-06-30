import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;

@Path("/sysAdmin")
public class TopicusAdminResource {
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
     * Fetches all admin account IDs and their associated schools within the system
     */
    @Path("/getAdmins")
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public ArrayList<SchoolAdmin> allAdmins() throws SQLException {
        openConnection();
        ArrayList<SchoolAdmin> adminList = new ArrayList<>();

        String query = "SELECT * FROM schooladmin;";
        PreparedStatement st = db.prepareStatement(query);
        ResultSet rs = st.executeQuery();

        while (rs.next()) {
            SchoolAdmin queriedAdmin = new SchoolAdmin();
            queriedAdmin.setSchool_id(rs.getInt(1));
            queriedAdmin.setAccount_id(rs.getString(2));
            adminList.add(queriedAdmin);
        }

        closeConnection();
        return adminList;
    }

    /**
     * Fetches all details of a particular admin
     */
    @Path("/fetchAdmin/{id}")
    @GET
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Account getAdmin(@PathParam("id") String accountID) throws SQLException {
        openConnection();
        String query = "SELECT * FROM account WHERE account_id LIKE ? AND role = 'A';";
        PreparedStatement st = db.prepareStatement(query);
        st.setString(1, accountID);
        ResultSet rs = st.executeQuery();

        Account queriedAdmin = new Account();
        while (rs.next()) {
            queriedAdmin.setAccount_id(rs.getString(1));
            queriedAdmin.setName(rs.getString(2));
            queriedAdmin.setAddress(rs.getString(3));
            queriedAdmin.setPhone_number_1(rs.getString(4));
            queriedAdmin.setPassword(rs.getString(6)); //TODO this method must also get the admin's unhashed password
        }

        return queriedAdmin;
    }

    /**
     * Adds a school into the system through form fields
     */
    @Path("/addSchool/{schoolName}/{address}/{tuition}/{contactNumber}")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void addSchool(@PathParam("schoolName") String schoolName,
                          @PathParam("address") String address,
                          @PathParam("tuition") int tuition,
                          @PathParam("contactNumber") String contactNumber) throws SQLException {
        openConnection();
        int schoolID = generateSchoolID();

        String cmd = "INSERT INTO school (school_id, school_name, address, tuition, contact_number) VALUES (?, ?, ?, ?, ?);";
        PreparedStatement st = db.prepareStatement(cmd);
        st.setInt(1, schoolID);
        st.setString(2, schoolName);
        st.setString(3, address);
        st.setInt(4, tuition);
        st.setString(5, contactNumber);
        st.execute();
        closeConnection();

        //TODO some response saying 'School Successfully Added!" and then redirect back to the main SysAdmin page
    }



    /**
     * Adds an admin under a school
     */
    @Path("/addAdmin/{id}/{email}/{name}/{address}/{phoneNumber}/{password}")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void addAdmin(@PathParam("id") int id,
                         @PathParam("email") String accountID,
                         @PathParam("name") String name,
                         @PathParam("address") String address,
                         @PathParam("phoneNumber") String phoneNumber,
                         @PathParam("password") String password) throws SQLException, NoSuchAlgorithmException {
        openConnection();
        String accountCMD = "INSERT INTO account (account_id, name, address, phone_number_1, password, role) VALUES (?, ?, ?, ?, ?, ?);";
        PreparedStatement st1 = db.prepareStatement(accountCMD);
        st1.setString(1, accountID);
        st1.setString(2, name);
        st1.setString(3, address);
        st1.setString(4, phoneNumber);
        st1.setString(6, "A");

        //make a random password, hash it and keep track of the original password
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] bytePass = digest.digest(password.getBytes(StandardCharsets.UTF_8));
        String hashedPass = hashLoginPass(bytePass);
        st1.setString(5, hashedPass);

        st1.execute();

        String schoolAdminCMD = "INSERT INTO schooladmin (school_id, account_id) VALUES (?, ?);";
        PreparedStatement st2 = db.prepareStatement(schoolAdminCMD);
        st2.setInt(1, id);
        st2.setString(2, accountID);
        st2.execute();

        //TODO some response saying 'Successfully added admin!'

        closeConnection();
    }

    /**
     * Updates a school's details
     */
    @Path("/updateSchool/{id}/{schoolName}/{address}/{tuition}/{contactNumber}")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void updateSchool(@PathParam("id") int id,
                             @PathParam("schoolName") String schoolName,
                             @PathParam("address") String address,
                             @PathParam("tuition") int tuition,
                             @PathParam("contactNumber") String contactNumber) throws SQLException {
        openConnection();
        String cmd = "UPDATE school SET school_name = ?, address = ?, tuition = ?, contact_number = ? WHERE school_id = ?;";
        PreparedStatement st = db.prepareStatement(cmd);
        st.setString(1, schoolName);
        st.setString(2, address);
        st.setInt(3, tuition);
        st.setString(4, contactNumber);
        st.setInt(5, id);
        st.execute();

        //TODO either restrict inputs to avoid NULLs or have a response/check for empty fields

        closeConnection();
    }

    /**
     * Updates a school admin's details
     */
    @Path("/updateSchoolAdmin")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void updateSchoolAdmin(@FormParam("account_id") String accountID,
                                  @FormParam("name") String name,
                                  @FormParam("address") String address,
                                  @FormParam("phoneNumber") String phoneNumber,
                                  @FormParam("pass") String pass) throws SQLException {
        openConnection();
        String cmd = "UPDATE account SET name = ?, address = ?, phone_number_1 = ?, password = ? WHERE account_id LIKE ?;";
        PreparedStatement st = db.prepareStatement(cmd);
        st.setString(1, name);
        st.setString(2, address);
        st.setString(3, phoneNumber);
        st.setString(4, pass);
        st.setString(5, accountID);
        st.execute();

        //TODO either restrict inputs to avoid NULLs or have a response/check for empty fields

        closeConnection();
    }

    /**
     * Removes a school admin from the system
     */
    @Path("/removeAdmin")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void removeAdmin(@FormParam("email") String accountID) throws SQLException {
        openConnection();

        String accountCMD = "DELETE FROM account WHERE account_id LIKE ?";
        PreparedStatement st = db.prepareStatement(accountCMD);
        st.setString(1, accountID);
        st.execute();

        closeConnection();
    }

    /**
     * Removes a school from the system
     */
    @Path("/removeSchool/{id}")
    @DELETE
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void removeSchool(@PathParam("id") int id) throws SQLException {
        openConnection();

        String cmd = "DELETE FROM school WHERE school_id = ?";
        PreparedStatement st = db.prepareStatement(cmd);
        st.setInt(1, id);
        st.execute();

        closeConnection();
    }

    /**
     * Generates a school id based on the highest id
     */
    public int generateSchoolID() throws SQLException {
        String query = "SELECT MAX(school_id) FROM school;";
        PreparedStatement st = db.prepareStatement(query);
        ResultSet rs = st.executeQuery();
        int newID;

        // if there are no rows
        if (!rs.next()) {
            return 1;
        } else {
            newID = rs.getInt(1) + 1;
        }
        return newID;
    }

    /**
     * Hashes a byte array of a password into a hexString
     */
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

    /**
     * Queries for a school of a particular school id
     */
    public int getSchoolID(String schoolName) throws SQLException {
        String query = "SELECT school_id FROM school WHERE school_name LIKE ?";
        PreparedStatement st = db.prepareStatement(query);
        st.setString(1, schoolName);
        ResultSet rs = st.executeQuery();

        if (rs.next()) {
            return rs.getInt(1);
        } else {
            return -1;
        }
    }

    /**
     * Topicus admin login
     */
    @Path("/login")
    @POST
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response login(@FormParam("adminEmail") String email,
                          @FormParam("adminPass") String pass) throws SQLException, URISyntaxException, NoSuchAlgorithmException {
        openConnection();
        URI failed = new URI("http://localhost:8080/Topicus/loginAdminFailed.html");
        URI success = new URI("http://localhost:8080/Topicus/adminPage.html");

        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] bytePass = digest.digest(pass.getBytes(StandardCharsets.UTF_8));
        String hashedPass = hashLoginPass(bytePass);
        
        if (!attemptTopicusLogin(email, hashedPass)) {
            closeConnection();
            return Response.seeOther(failed).build();
        } else {
            closeConnection();
            return Response.seeOther(success).build();
        }
    }

    private boolean attemptTopicusLogin(String email, String pass) throws SQLException {
        String query = "SELECT * FROM account WHERE account_id LIKE ? AND password LIKE ? AND role='T'";
        PreparedStatement st = db.prepareStatement(query);
        st.setString(1, email);
        st.setString(2, pass);
        ResultSet rs = st.executeQuery();
        return rs.next();
    }

}
