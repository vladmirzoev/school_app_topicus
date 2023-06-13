import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Path("/signup")
public class Signup {
    String host = "bronto.ewi.utwente.nl";
    String dbName = "dab_di22232b_81";
    String url = "jdbc:postgresql://" + host + ":5432/" + dbName + "?currentSchema=TopicusDatabase";
    String username = "dab_di22232b_81";
    String password = "uZQ2Mqk82/Kx6s5l";
    Connection db = null;

    /**
     * Connects to db server.
     */
    public void establishConnection() {
        try {
            db = DriverManager.getConnection(url, username, password);
        } catch (
                SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @POST
    @Path("/newaccount")
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response CreateAccDB(@FormParam("fname") String fname,
                                @FormParam("lname") String lname,
                                @FormParam("email") String email,
                                @FormParam("p_no1") String p_no1,
                                @FormParam("p_no2") String p_no2,
                                @FormParam("address") String address,
                                @FormParam("pass") String pass,
                                @FormParam("conf_pass") String conf_pass) throws Exception {
        establishConnection();
        String email_format = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(email_format);
        Matcher matcher = pattern.matcher(email);
        URI success = new java.net.URI("http://localhost:8080/Topicus/signUpSuccessful.html");

        if (!accountExists(email) && pass.equals(conf_pass)) {
            addAccount(fname + " " + lname, p_no1, p_no2, email, address, pass);
            return Response.seeOther(success).build();
        } else if (accountExists(email)) {
            //TODO: cannot create account as the email already exists
        } else if (!pass.equals(conf_pass)) {
            //TODO: wrong password
        } else if (p_no1.length() != 10) {
            //TODO: invalid phone number
        } else if (p_no2 != null && p_no2.length() != 10) {
            //TODO: invalid phone number
        } else if (!matcher.matches()) {
            //TODO: invalid email format
        }

        return null; //TODO stub
    }

    public boolean accountExists(String email) throws SQLException {
        String query = "SELECT * FROM account WHERE account_id = ?";
        PreparedStatement st = db.prepareStatement(query);
        st.setString(1, email);
        ResultSet rs = st.executeQuery();
        return rs.next();
    }

    private void addAccount(String guardianName, String telephone1, String telephone2, String email, String address, String pass) throws SQLException {
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
            guar.setString(1,email);
            guar.execute();
        }
    }
}
