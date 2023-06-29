import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

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
            Class.forName("org.postgresql.Driver");
            db = DriverManager.getConnection(url, username, password);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void closeConnection() throws SQLException {
        db.close();
    }

    /**
     * Sends a message to a specific account
     */
    @Path("/sendaccountmessage/{senderid}/{receiverid}/{subject}/{content}")
    @POST
    public void sendAccountMessage(@PathParam("senderid") String senderid,
                                   @PathParam("receiverid") String receiverid,
                                   @PathParam("content") String content,
                                   @PathParam("subject") String subject) throws SQLException {
        openConnection();
        byte[] contentBytes = content.getBytes(StandardCharsets.UTF_8);

        String cmd = "INSERT INTO message (sender, receiver, content, datesent, subject) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement st = db.prepareStatement(cmd);
        st.setString(1, senderid);
        st.setString(2, receiverid);
        st.setString(3, Arrays.toString(contentBytes));
        st.setDate(4, Date.valueOf(java.time.LocalDate.now()));
        st.setString(5, subject);
        st.execute();

        closeConnection();
    }

    /**
     * Sends a message to a school
     */
    @Path("/sendschoolmessage/{senderid}/{schoolname}/{subject}/{content}")
    @POST
    public void sendSchoolMessage(@PathParam("senderid") String senderid,
                                  @PathParam("schoolname") String schoolname,
                                  @PathParam("content") String content,
                                  @PathParam("subject") String subject) throws Exception {
        openConnection();
        byte[] contentBytes = content.getBytes(StandardCharsets.UTF_8);

        //get school id of the school being sent to
        String receiverid = Integer.toString(getSchoolID(schoolname));

        String cmd = "INSERT INTO message (sender, receiver, content, datesent, subject) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement st = db.prepareStatement(cmd);
        st.setString(1, senderid);
        st.setString(2, receiverid);
        st.setString(3, Arrays.toString(contentBytes));
        st.setDate(4, Date.valueOf(java.time.LocalDate.now()));
        st.setString(5, subject);

        closeConnection();
    }

    /**
     * Gets messages sent to an admins' school
     */
    @Path("/fetchschoolmessages/{accountid}")
    @GET
    public ArrayList<Message> fetchSchoolMessages(@PathParam("accountid") String accountid) throws SQLException {
        openConnection();
        String query = "SELECT m.* FROM message m, schooladmin s WHERE s.account_id = ? AND m.receiver = s.school_id";
        PreparedStatement st = db.prepareStatement(query);
        st.setString(1, accountid);
        ResultSet rs = st.executeQuery();

        ArrayList<Message> messageList = new ArrayList<>();

        while (rs.next()) {
            Message currentMessage = new Message();
            currentMessage.setSender(rs.getString(1));
            currentMessage.setReceiver(rs.getString(2));

            String str1 = rs.getString(3);
            String str2 = str1.substring(1, str1.length()-1);
            String[] st3 = str2.split(", ");
            byte[] contentBytes = new byte[st3.length];
            for (int i = 0; i < st3.length; i++) {
                contentBytes[i] = Byte.parseByte(st3[i]);
            }
            String content = new String(contentBytes, StandardCharsets.UTF_8);

            currentMessage.setContent(content);
            currentMessage.setDateSent(rs.getDate(4).toString());
            currentMessage.setSubject(rs.getString(5));
            messageList.add(currentMessage);
        }

        closeConnection();
        return messageList;
    }

    /**
     * Gets messages sent to an account
     */
    @Path("/fetchaccountmessages/{accountid}")
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public ArrayList<Message> fetchAccountMessages(@PathParam("accountid") String accountid) throws SQLException {
        openConnection();
        String query = "SELECT m.* FROM message m WHERE m.receiver = ? OR m.sender = ?";
        PreparedStatement st = db.prepareStatement(query);
        st.setString(1, accountid);
        st.setString(2, accountid);
        ResultSet rs = st.executeQuery();

        ArrayList<Message> messageList = new ArrayList<>();

        while(rs.next()) {
            Message currentMessage = new Message();
            currentMessage.setSender(rs.getString(1));
            currentMessage.setReceiver(rs.getString(2));

            String str1 = rs.getString(3);
            String str2 = str1.substring(1, str1.length()-1);
            String[] st3 = str2.split(", ");
            byte[] contentBytes = new byte[st3.length];
            for (int i = 0; i < st3.length; i++) {
                contentBytes[i] = Byte.parseByte(st3[i]);
            }
            String content = new String(contentBytes, StandardCharsets.UTF_8);

            currentMessage.setContent(content);
            currentMessage.setDateSent(rs.getDate(4).toString());
            currentMessage.setSubject(rs.getString(5));
            messageList.add(currentMessage);
        }

        closeConnection();
        return messageList;
    }

    /**
     * Gets the school ID depending on the school name
     */
    public int getSchoolID(String schoolName) throws Exception {
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
        return schoolID;
    }
}
