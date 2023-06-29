import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.xml.bind.annotation.XmlRootElement;


@XmlRootElement
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Registration {
    public int id;
    public int grade;
    public String registration_date;
    public int student_id;
    public String name;
    public int school_id;
    public String status;
    public String allowedit;
    public int registration_id;

    public int getRegistration_id() {
        return registration_id;
    }

    public void setRegistration_id(int registration_id) {
        this.registration_id = registration_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getRegistration_date() {
        return registration_date;
    }

    public void setRegistration_date(String registration_date) {
        this.registration_date = registration_date;
    }

    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public int getSchool_id() {
        return school_id;
    }

    public void setSchool_id(int school_id) {
        this.school_id = school_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAllowedit() {
        return allowedit;
    }

    public void setAllowedit(String edit) {
        this.allowedit = edit;
    }
}
