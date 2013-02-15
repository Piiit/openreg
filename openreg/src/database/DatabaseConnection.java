package database;

import java.sql.Connection;
import java.sql.DriverManager;

//TODO create a test database
public final class DatabaseConnection {

	private final static String USER = "postgres";
	private final static String PASSWORD = "qwertz";
	private final static String URL = "jdbc:postgresql://localhost/openreg?user=" + USER + "&password=" + PASSWORD;
	
	private static Connection connection = null;
	
	public static void setup() throws Exception {
		Class.forName("org.postgresql.Driver");
		connection = DriverManager.getConnection(URL);
	}

	public static Connection getConnection() throws Exception {
		if (connection == null) {
			setup();
		}
		return connection;
	}
}
