import static org.junit.jupiter.api.Assertions.*;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

class AccountResourceTest {


    SignUpResource user = new SignUpResource();
    AccountResource acc = new AccountResource();


    @Test
    void loginTest() throws Exception{
        URI success = new URI("http://localhost:8080/Topicus/userDashboard.html");
        String email = "johndoe@gmail.com";
        String pass = "mynameisjohn";
        user.CreateAccDB("John","Doe",email,"0675643240",null,"Enschede", pass);
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] bytePass = digest.digest(pass.getBytes(StandardCharsets.UTF_8));
        String hashedPass = acc.hashLoginPass(bytePass);
        assertEquals(Response.seeOther(success).build().getStatus(), acc.login(email, hashedPass).getStatus());
    }

    @Test
    void adminLoginTest() throws Exception{
        URI success = new URI("http://localhost:8080/Topicus/registrations.html");
        String email = "admin@test.com";
        String pass = "admin";
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] bytePass = digest.digest(pass.getBytes(StandardCharsets.UTF_8));
        String hashedPass = acc.hashLoginPass(bytePass);
        assertEquals(Response.seeOther(success).build().getStatus(), acc.adminLogin(email, hashedPass).getStatus());
    }

    @Test
    void getNameTest() throws Exception{
        assertTrue(acc.getName("johndoe@gmail.com").getName().equals("John Doe"));
    }

    @Test
    void fetchAccountDetailsTest() throws Exception{
        String email = "johndoe@gmail.com";
        String name = "John Doe";
        String add = "Enschede";
        String pNo1 = "0675643240";
        String pNo2 = null;


        assertTrue(acc.fetchAccountDetails(email).getName().equals(name));
        assertTrue(acc.fetchAccountDetails(email).getAddress().equals(add));
        assertTrue(acc.fetchAccountDetails(email).getPhone_1().equals(pNo1));
        assertTrue(acc.fetchAccountDetails(email).getPhone_2() == pNo2);
    }


}

