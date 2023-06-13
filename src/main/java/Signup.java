import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.sql.*;

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
    public void CreateAccDB(@FormParam(fname) String f_name, @FormParam(lname) String l_name, @FormParam(email) String email,
                          @FormParam(p_no1) String p_no1, @FormParam(p_no2) String p_no2, @FormParam(address) String address,
                          @FormParam(pass) String pass, @FormParam(conf_pass) String conf_pass){

            addAccount(f_name+" "+l_name, p_no1, p_no2, email, address, pass);


    }

    public boolean accountExists(String email) throws SQLException {
        String query = "SELECT * FROM account WHERE account_id = ?";
        PreparedStatement st = db.prepareStatement(query);
        st.setString(1, email);
        ResultSet rs = st.executeQuery();
        return rs.next();
    }

    private void addAccount(String guardianName, String telephone1, String telephone2, String email, String address, String pass, String role) throws SQLException {
        if (!accountExists(email)) { //if an account doesnt exist, make a new account entry
            String account = "INSERT INTO account (account_id, name, address, phone_number_1, phone_number_2, role) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement acc = db.prepareStatement(account);
            role = ""
            acc.setString(1, email);
            acc.setString(2, guardianName);
            acc.setString(3, address);
            acc.setString(4, telephone1);
            acc.setString(5, telephone2);
            acc.setString(6,pass);
            acc.setString(7, "G");
            acc.execute();
        }
}
