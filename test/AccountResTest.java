import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
//import main.

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AccountResTest {

    @BeforeAll
    public void openConnection() {
        String host = "bronto.ewi.utwente.nl";
        String dbName = "dab_di22232b_81";
        String url = "jdbc:postgresql://" + host + ":5432/" + dbName + "?currentSchema=TopicusDatabase";
        String username = "dab_di22232b_81";
        String password = "uZQ2Mqk82/Kx6s5l";
        Connection db = null;
        try {
            Class.forName("org.postgresql.Driver");
            db = DriverManager.getConnection(url, username, password);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

//    @Test
//    public



}
