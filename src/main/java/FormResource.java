import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.sql.*;

@Path("/form")
public class FormResource {
    String host = "bronto.ewi.utwente.nl";
    String dbName = "dab_di22232b_81";
    String url = "jdbc:postgresql://" + host + ":5432/" + dbName + "?currentSchema=TopicusDatabase";
    String username = "dab_di22232b_81";
    String password = "uZQ2Mqk82/Kx6s5l";
    Connection db = null;

    public void establishConnection() {
        try {
            db = DriverManager.getConnection(url, username, password);
        } catch (
                SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Path("/search")
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Form getForm(@QueryParam("schoolid") int id, @QueryParam("grade") int grade) throws SQLException {
        establishConnection();
        Form form = new Form();
        String query = "SELECT b.question FROM form a, fields b WHERE a.form_id = b.form_id AND b.form_id = ?";
        PreparedStatement st = db.prepareStatement(query);
        st.setInt(1, grade);
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            Form.Field question = new Form.Field();
            question.setQuestion(rs.getString(1));
            form.appendField(question);
        }
        String query2 = "SELECT * FROM form WHERE form_id = ?";
        PreparedStatement st2 = db.prepareStatement(query);
        st2.setInt(1, id);
        ResultSet rs2 = st.executeQuery();
        while (rs2.next()) {
            form.setForm_id(1);
            form.setGrade(2);
            form.setSchool_id(3);
        }
        return form;
    }

}
