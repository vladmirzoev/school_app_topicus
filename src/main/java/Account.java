import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Account {
    private String account_id;
    private String name;
    private String address;
    private String phone_number_1;
    private String phone_number_2;
    private String password;
    private String role;

    public String getAccount_id() {
        return account_id;
    }

    public void setAccount_id(String account_id) {
        this.account_id = account_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone_number_1() {
        return phone_number_1;
    }

    public void setPhone_number_1(String phone_number_1) {
        this.phone_number_1 = phone_number_1;
    }

    public String getPhone_number_2() {
        return phone_number_2;
    }

    public void setPhone_number_2(String phone_number_2) {
        this.phone_number_2 = phone_number_2;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
