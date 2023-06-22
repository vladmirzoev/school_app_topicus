import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Path("/message")
public class MessageResource {
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
     * Sends a message to a specific account
     */
    @Path("/sendaccountmessage/{senderid}")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void sendAccountMessage(@PathParam("senderid") String senderid, @FormParam("receiverid") String receiverid, @FormParam("content") String content) throws SQLException {
        openConnection();
        byte[] contentBytes = content.getBytes();

        String cmd = "INSERT INTO message (sender, receiver, content) VALUES (?, ?, ?)";
        PreparedStatement st = db.prepareStatement(cmd);
        st.setString(1, senderid);
        st.setString(2, receiverid);
        st.setBytes(3, contentBytes);
        st.execute();

        closeConnection();
    }

    /**
     * Sends a message to a school
     */
    @Path("/sendschoolmessage/{senderid}")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void sendSchoolMessage(@PathParam("senderid") String senderid, @FormParam("schoolid") String receiverid) {
        
    }
}
