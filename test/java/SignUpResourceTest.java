import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.List;

class SignUpResourceTest {

    SignUpResource su = new SignUpResource();
    AccountResource acc = new AccountResource();

    @Test
    void createAccDBTest() throws Exception{
        String fn = "Hamada";
        String ln = "Helal";
        String email = "hamadahelal@january.com";
        String pNo1 = "1597532486";
        String pNo2 = "9517538426";
        String add = "Shar'e Hamada el dawly";
        String pass = "anahamadahelal";

        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] bytePass = digest.digest(pass.getBytes(StandardCharsets.UTF_8));
        String hashedPass = su.hashLoginPass(bytePass); //to test password

        su.CreateAccDB(fn,ln,email,pNo1,pNo2,add,pass);

        List<Account> getAccounts = acc.getAllAccounts();
        boolean accountCreatedSuccessfully = false;
        for (Account a : getAccounts){
            if (a.getName().equals(fn +" "+ln) && a.getAccount_id().equals(email) && a.getPhone_number_1().equals(pNo1)
                    && a.getPhone_number_2().equals(pNo2) && a.getAddress().equals(add)) {
                accountCreatedSuccessfully = true;
                break;
            }
        }
        assertTrue(accountCreatedSuccessfully);
    }

}