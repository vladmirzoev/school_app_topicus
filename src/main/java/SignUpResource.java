import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Path("/signup")
public class SignUpResource {
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

    //Hash login pass to see if it is similar to the hashed pass in the database
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
     * Creates a new account table entry using all the fields from the signup page and checks for invalid/empty fields
     */
    @POST
    @Path("/newaccount/{fname}/{lname}/{email}/{p_no1}/{p_no2}/{address}/{pass}")
    @Produces(MediaType.TEXT_HTML)
    public Response CreateAccDB(@PathParam("fname") String fname,
                                @PathParam("lname") String lname,
                                @PathParam("email") String email,
                                @PathParam("p_no1") String p_no1,
                                @PathParam("p_no2") String p_no2,
                                @PathParam("address") String address,
                                @PathParam("pass") String pass) throws Exception {
        openConnection();
        String email_format = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(email_format);
        Matcher matcher = pattern.matcher(email);
        URI success = new java.net.URI("http://localhost:8080/Topicus/signUpSuccessful.html");

        //hash passwords
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] bytePass = digest.digest(pass.getBytes(StandardCharsets.UTF_8));
        String hashedPass = hashLoginPass(bytePass);


        if (!accountExists(email)) {
            addAccount(fname + " " + lname, p_no1, p_no2, email, address, hashedPass);
            return Response.seeOther(success).build();
        }
        closeConnection();
        return null; //TODO stub
    }

    /**
     * Checks if an account already exists
     */
    public boolean accountExists(String email) throws SQLException {
        String query = "SELECT * FROM account WHERE account_id LIKE ?";
        PreparedStatement st = db.prepareStatement(query);
        st.setString(1, email);
        ResultSet rs = st.executeQuery();
        return rs.next();
    }

    /**
     * SQL command for a new account table entry
     */
    private void addAccount(String guardianName,
                            String telephone1,
                            String telephone2,
                            String email,
                            String address,
                            String pass) throws SQLException {
        if (!accountExists(email)) { //if an account doesn't exist, make a new account entry
            String account = "INSERT INTO account (account_id, name, address, phone_number_1, phone_number_2, password, role) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement acc = db.prepareStatement(account);
            acc.setString(1, email);
            acc.setString(2, guardianName);
            acc.setString(3, address);
            acc.setString(4, telephone1);
            acc.setString(5, telephone2);
            acc.setString(6, pass);
            acc.setString(7, "G");
            acc.execute();
            String guardian = "INSERT INTO guardian (guardian_id) VALUES (?)";
            PreparedStatement guar = db.prepareStatement(guardian);
            guar.setString(1, email);
            guar.execute();
        }
    }
}
