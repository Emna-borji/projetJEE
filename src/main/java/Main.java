import java.sql.Connection;
import java.sql.SQLException;
import utils.ConnexionBD;

public class Main {

    public static void main(String[] args) {
        // Test the database connection
        try (Connection connection = ConnexionBD.connect()) {
            if (connection != null) {
                System.out.println("Connection successful!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
