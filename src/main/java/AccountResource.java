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
import java.util.List;

@Path("/account")
public class AccountResource {
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


    //Hash login pass to see if it is similar to the hashed pass in the database
    public static String hashLoginPass(byte[] hash) {
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
     * Parent login
     */
    @Path("/login")
    @POST
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response login(@FormParam("regularEmail") String email,
                          @FormParam("regularPass") String pass) throws SQLException, URISyntaxException, NoSuchAlgorithmException {
        openConnection();
        URI failed = new URI("http://localhost:8080/Topicus/failedLogin.html");
        URI success = new URI("http://localhost:8080/Topicus/userDashboard.html");

        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] bytePass = digest.digest(pass.getBytes(StandardCharsets.UTF_8));
        String hashedPass = hashLoginPass(bytePass);


        if (!attemptRegularLogin(email, hashedPass)) {
            closeConnection();
            return Response.seeOther(failed).build();
        } else {
            closeConnection();
            return Response.seeOther(success).build();
        }
    }

    /**
     * Parent login
     */
    @Path("/loginCode/")
    @POST
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response loginCode(@FormParam("parentCode") String email) throws SQLException, URISyntaxException, NoSuchAlgorithmException {
        openConnection();
        URI failed = new URI("http://localhost:8080/Topicus/failedLogin.html");
        URI success = new URI("http://localhost:8080/Topicus/userDashboard.html");


        if (!attemptCodeLogin(email)) {
            closeConnection();
            return Response.seeOther(failed).build();
        } else {
            closeConnection();
            return Response.seeOther(success).build();
        }
    }

    private boolean attemptCodeLogin(String email) throws SQLException {
        String query = "SELECT * FROM account WHERE account_id = ?";
        PreparedStatement st = db.prepareStatement(query);
        st.setString(1, email);
        ResultSet rs = st.executeQuery();
        return rs.next();
    }

    /**
     * Admin login
     */
    @Path("/loginadmin")
    @POST
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response adminLogin(@FormParam("adminEmail") String email,
                               @FormParam("adminPass") String pass) throws SQLException, URISyntaxException, NoSuchAlgorithmException {
        openConnection();
        URI failed = new URI("http://localhost:8080/Topicus/failedLoginadmin.html");
        URI success = new URI("http://localhost:8080/Topicus/registrations.html");

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
     * Gets the name of an account
     */
    @GET
    @Path("/getname/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Account getName(@PathParam("id") String id) throws SQLException {
        openConnection();
        String query = "SELECT a.name FROM account a WHERE a.account_id LIKE ?";
        PreparedStatement st = db.prepareStatement(query);
        st.setString(1, id);
        ResultSet rs = st.executeQuery();
        Account queriedName = new Account();
        while (rs.next()) {
            queriedName.setName(rs.getString(1));
        }
        closeConnection();
        return queriedName;
    }

    /**
     * Gets details of a particlar account id
     */
    @Path("/fetchaccountdetails/{id}")
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Parent fetchAccountDetails(@PathParam("id") String id) throws Exception {
        openConnection();
        String query = "SELECT account_id, name, address, phone_number_1, phone_number_2 FROM account WHERE account_id LIKE ?";
        PreparedStatement st = db.prepareStatement(query);
        st.setString(1, id);
        ResultSet rs = st.executeQuery();

        Parent queriedParent = new Parent();
        while (rs.next()) {
            queriedParent.setId(rs.getString(1));
            queriedParent.setName(rs.getString(2));
            queriedParent.setAddress(rs.getString(3));
            queriedParent.setPhone_1(rs.getString(4));
            queriedParent.setPhone_2(rs.getString(5));
        }
        closeConnection();
        return queriedParent;
    }

    /**
     * Check account_id, password and role of a parent login
     */
    private boolean attemptRegularLogin(String email, String pass) throws SQLException {
        String query = "SELECT * FROM account WHERE account_id LIKE ? AND password LIKE ? AND role='G'";
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
        String query = "SELECT * FROM account WHERE account_id LIKE ? AND password LIKE ? AND role = 'A'";
        PreparedStatement st = db.prepareStatement(query);
        st.setString(1, email);
        st.setString(2, pass);
        ResultSet rs = st.executeQuery();
        return rs.next();
    }

    /**
     * Gets the role of a particular account id
     */
    @Path("/checkrole/{account_id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Account fetchRole(@PathParam("account_id") String account_id) throws SQLException {
        String query = "SELECT account_id, role FROM account WHERE account_id LIKE ?";
        openConnection();
        PreparedStatement st = db.prepareStatement(query);
        st.setString(1, account_id);
        ResultSet rs = st.executeQuery();
        Account account = new Account();
        while (rs.next()) {
            account.setAccount_id(rs.getString(1));
            account.setRole(rs.getString(2));
        }
        return account;
    }

    public List<Account> getAllAccounts() throws Exception{
        openConnection();
        String query = "SELECT account_id, name, address, phone_number_1, phone_number_2 FROM account";
        PreparedStatement st = db.prepareStatement(query);
        ResultSet rs = st.executeQuery();
        List<Account> allAccounts = new ArrayList<>();

        while (rs.next()){
            Account queriedAccount = new Account();
            queriedAccount.setAccount_id(rs.getString(1));
            queriedAccount.setName(rs.getString(2));
            queriedAccount.setAddress(rs.getString(3));
            queriedAccount.setPhone_number_1(rs.getString(4));
            queriedAccount.setPhone_number_2(rs.getString(5));
            allAccounts.add(queriedAccount);
        }

        closeConnection();
        return allAccounts;
    }
}
