import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Student {
    private int id;
    private String bsn;
    private String name;
    private String birthdate;
    private int guardian_id;

    public Student(int id, String bsn, String name, String birthdate, int guardian_id) {
        this.id = id;
        this.bsn = bsn;
        this.name = name;
        this.birthdate = birthdate;
        this.guardian_id = guardian_id;
    }
}
