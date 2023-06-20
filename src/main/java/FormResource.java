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
     * Gets school-specific form
     */
    @Path("{id}")
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Form getForm(@PathParam("id") int id) throws SQLException {
        openConnection();
        Form form = new Form();
        String query = "SELECT b.question FROM form a, fields b WHERE a.form_id = b.form_id AND b.form_id = ?";
        PreparedStatement st = db.prepareStatement(query);
        st.setInt(1, id);
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            Form.Field question = new Form.Field();
            question.setQuestion(rs.getString(1));
            form.appendField(question);
        }

        String query2 = "SELECT a.*, b.school_name FROM form a, school b WHERE a.school_id = b.school_id AND form_id = ?";
        PreparedStatement st2 = db.prepareStatement(query2);
        st2.setInt(1, id);
        ResultSet rs2 = st2.executeQuery();
        while (rs2.next()) {
            form.setForm_id(rs2.getInt(1));
            form.setGrade(rs2.getInt(2));
            form.setSchool_id(rs2.getInt(3));
            form.setSchool_name(rs2.getString(4));
        }
        closeConnection();
        return form;
    }

    /**
     * Make a new field for a particular form ID
     */
    @Path("/createField")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_HTML)
    public void createField(@FormParam("formID") int formID, @FormParam("question") String question, @FormParam("inputType") String inputType) throws SQLException {
        openConnection();
        String cmd = "INSERT INTO fields (form_id, question, inputType) VALUES (?, ?, ?)";
        PreparedStatement st = db.prepareStatement(cmd);
        st.setInt(1, formID);
        st.setString(2, question);
        st.setString(3, inputType);
        st.execute();

        //TODO make the new field show up in the front end

        closeConnection();
    }
}
