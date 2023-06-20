import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

@Path("/account")
public class AccountResource {
    String host = "bronto.ewi.utwente.nl";
    String dbName = "dab_di22232b_81";
    String url = "jdbc:postgresql://" + host + ":5432/" + dbName + "?currentSchema=TopicusDatabase";
    String username = "dab_di22232b_81";
    String password = "uZQ2Mqk82/Kx6s5l";
    Connection db = null;

    public void establishConnection() {
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
            if(hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    /**
     * Parent login
     */
    @Path("/login")
    @POST
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response login(@FormParam("regularEmail") String email, @FormParam("regularPass") String pass) throws SQLException, URISyntaxException, NoSuchAlgorithmException {
        establishConnection();
        URI failed = new java.net.URI("http://localhost:8080/Topicus/failedLogin.html");
        URI success = new java.net.URI("http://localhost:8080/Topicus/userDashboard.html");

        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] bytePass = digest.digest(pass.getBytes(StandardCharsets.UTF_8));
        String hashedPass = hashLoginPass(bytePass);

        if (!attemptRegularLogin(email, hashedPass)) {
            closeConnection();
            return Response.seeOther(failed).build();
        } else {
            System.out.println("CREDENTIALS OK");
            closeConnection();
            return Response.seeOther(success).build();
        }
    }

    /**
     * Admin login
     */
    @Path("/loginadmin")
    @POST
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response adminLogin(@FormParam("adminEmail") String email, @FormParam("adminPass") String pass) throws SQLException, URISyntaxException, NoSuchAlgorithmException {
        establishConnection();
        URI failed = new java.net.URI("http://localhost:8080/Topicus/failedLoginadmin.html");
        URI success = new java.net.URI("http://localhost:8080/Topicus/registrations.html");

        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] bytePass = digest.digest(pass.getBytes(StandardCharsets.UTF_8));
        String hashedPass = hashLoginPass(bytePass);

        if (!attemptAdminLogin(email, hashedPass)) {
            closeConnection();
            return Response.seeOther(failed).build();
        } else {
            closeConnection();
            return Response.seeOther(success).build();
        }
    }

    /**
     * Check account_id, password and role of a parent login
     */
    private boolean attemptRegularLogin(String email, String pass) throws SQLException {
        String query = "SELECT * FROM account WHERE account_id = ? AND password = ? AND role='G'";
        PreparedStatement st = db.prepareStatement(query);
        st.setString(1, email);
        st.setString(2, pass);
        ResultSet rs = st.executeQuery();
        return rs.next();
    }

    /**
     * Check account_id, password and role of an admin login
     */
    private boolean attemptAdminLogin(String email, String pass) throws SQLException {
        String query = "SELECT * FROM account WHERE account_id = ? AND password = ? AND role='A'";
        PreparedStatement st = db.prepareStatement(query);
        st.setString(1, email);
        st.setString(2, pass);
        ResultSet rs = st.executeQuery();
        return rs.next();
    }
}
