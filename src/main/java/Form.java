import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Form {
    public int form_id;
    public int grade;
    public int school_id;


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
    public int getForm_id() {
        return form_id;
    }

    class Field {
        public int form_id;
        public String question;
        public String input_type;

        public int getForm_id() {
            return form_id;
        }

        public void setForm_id(int form_id) {
            this.form_id = form_id;
        }

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public String getInput_type() {
            return input_type;
        }

        public void setInput_type(String input_type) {
            this.input_type = input_type;
        }
    }
}



