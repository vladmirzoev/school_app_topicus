import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.ArrayList;

@XmlRootElement
public class Form {
    public int form_id;
    public int school_id;

    public String school_name;
    public int grade;

    public ArrayList<Field> fields;

    public Form() {
        this.fields = new ArrayList<>();
    }

    public String getSchool_name() {
        return school_name;
    }

    public void setSchool_name(String school_name) {
        this.school_name = school_name;
    }


    public int getForm_id() {
        return form_id;
    }

    public void setForm_id(int form_id) {
        this.form_id = form_id;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getSchool_id() {
        return school_id;
    }

    public void setSchool_id(int school_id) {
        this.school_id = school_id;
    }

    public ArrayList<Field> getFields() {
        return fields;
    }

    public void appendField(Field field) {
        fields.add(field);
    }

    @XmlRootElement
    static
    class Field {
        public String question;

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

    }
}



