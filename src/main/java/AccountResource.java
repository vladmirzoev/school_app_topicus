import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;

@Path("/account")
public class AccountResource {
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

    @Path("/login")
    @POST
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response login(@FormParam("regularEmail") String email,
                        @FormParam("regularPass") String pass) throws SQLException, URISyntaxException {
        establishConnection();
        URI failed = new java.net.URI("http://localhost:8080/Topicus/failedLogin.html"); //TODO ask if hard coding is ok
        URI success = new java.net.URI("http://localhost:8080/Topicus/userDashboard.html");
        if (!attemptRegularLogin(email, pass)) {
            return Response.seeOther(failed).build(); //TODO maybe use document.getElementById.innerHTML
        } else {
            System.out.println("CREDENTIALS OK");
            return Response.seeOther(success).build();
        }
    }

    @Path("/adminLogin")
    @POST
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response adminLogin(@FormParam("adminEmail") String email,
                             @FormParam("adminPass") String pass) throws SQLException {
        establishConnection();
        if (!attemptAdminLogin(email, pass)) {
            return null; //TODO give warning to user that either the email or password is not available
        } else {
            return null; //TODO redirect school admin to their dashboard/webpage
        }
    }

    private boolean attemptRegularLogin(String email, String pass) throws SQLException {
        String query = "SELECT * FROM account WHERE account_id = ? AND password = ? AND role='G'";
        PreparedStatement st = db.prepareStatement(query);
        st.setString(1, email);
        st.setString(2, pass);
        ResultSet rs = st.executeQuery();
        return rs.next();
    }

    private boolean attemptAdminLogin(String email, String pass) throws SQLException {
        String query = "SELECT * FROM account WHERE account_id = ? AND password = ? AND role='A'";
        PreparedStatement st = db.prepareStatement(query);
        st.setString(1, email);
        st.setString(2, pass);
        ResultSet rs = st.executeQuery();
        return rs.next();
    }
}
