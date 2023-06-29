import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RegResponse {
    public String response;
    public int registration_id;
    public int question_id;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public int getRegistration_id() {
        return registration_id;
    }

    public void setRegistration_id(int registration_id) {
        this.registration_id = registration_id;
    }

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

}
