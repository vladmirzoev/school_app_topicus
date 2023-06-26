import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.sql.*;
import java.util.ArrayList;

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
    @Path("{id}/{grade}")
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Form getForm(@PathParam("id") int id, @PathParam("grade") int grade) throws SQLException {
        openConnection();
        Form form = new Form();
        String query = "SELECT grade FROM form WHERE school_id = ?";
        PreparedStatement st = db.prepareStatement(query);
        st.setInt(1, id);
        ArrayList<Integer> formGrades = new ArrayList<>();
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            formGrades.add(rs.getInt(1));
        }

        int finalgrade = getFinalGrade(grade, formGrades);

        String query2 = "SELECT b.question FROM form a, fields b WHERE a.form_id = b.form_id AND a.school_id = ? AND a.grade = ?";
        PreparedStatement st2 = db.prepareStatement(query2);
        st2.setInt(1, id);
        st2.setInt(2, finalgrade);
        ResultSet rs2 = st2.executeQuery();
        while (rs2.next()) {
            Form.Field question = new Form.Field();
            question.setQuestion(rs2.getString(1));
            form.appendField(question);
        }

        String query3 = "SELECT a.form_id, a.school_id, b.school_name FROM form a, school b WHERE a.school_id = b.school_id AND a.school_id = ? AND a.grade = ?";
        PreparedStatement st3 = db.prepareStatement(query3);
        st3.setInt(1, id);
        st3.setInt(2, finalgrade);
        ResultSet rs3 = st3.executeQuery();
        while (rs3.next()) {
            form.setForm_id(rs3.getInt(1));
            form.setGrade(grade);
            form.setSchool_id(rs3.getInt(2));
            form.setSchool_name(rs3.getString(3));
        }
        closeConnection();
        return form;
    }

    private static int getFinalGrade(int grade, ArrayList<Integer> gradeSet) {
        boolean check = false;
        while (!check) {
            for (int comparedGrade : gradeSet) {
                if (comparedGrade >= grade) {
                    check = true;
                    return comparedGrade;
                } else if (comparedGrade == 12) {
                    return 12;
                }
            }
        }
        return 12;
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
