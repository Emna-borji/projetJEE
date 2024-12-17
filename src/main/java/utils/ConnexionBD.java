package utils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnexionBD {
	public static Connection connect() throws SQLException {
		// Connexion à la base de données
        String url = "jdbc:postgresql://localhost:5432/gestion_bib"; 
        String user = "postgres";  
        String password = "emnaemna";  

        // Attempt to establish and return the connection
        return DriverManager.getConnection(url, user, password);
    }
}
