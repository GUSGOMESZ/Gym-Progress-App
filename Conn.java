import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Conn {

    Connection c;
    Statement s;

    private static final String password = YOUR_PASSWORD;

    public Conn() {
        try {
            c = DriverManager.getConnection("jdbc:mysql:///gymTracker", "root", password);
            s = c.createStatement();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
