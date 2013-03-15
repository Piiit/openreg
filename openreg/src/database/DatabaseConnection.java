package database;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import log.Log;

public final class DatabaseConnection {
	
	public final static int LOGINTIMEOUT = 5;  //in seconds
	public final static String URL = "jdbc:postgresql://alcor.inf.unibz.it:5432/ds_group2?user=ds_group2&password=Iezedoo6";

	private static Connection connection = null;
	
	public static void setup(final String connectionURL) throws Exception {
		Log.info("Connecting to " + safeConnectionURL(connectionURL));
		Class.forName("org.postgresql.Driver");
		DriverManager.setLoginTimeout(LOGINTIMEOUT);
		connection = DriverManager.getConnection(connectionURL);
		Log.info("New database connection established: " + safeConnectionURL(connectionURL));
	}
	
	public static void setup() throws Exception {
		setup(URL);
	}
	
	public static String getConnectionURL() throws FileNotFoundException, IOException {
		Properties config = new Properties();
		config.load(new FileInputStream("resources/dbconfig.txt"));
		return config.getProperty("URL");
	}

	public static Connection getConnection() throws Exception {
		if (connection == null) {
			setup();
		}
		return connection;
	}
	
	public static void close() throws Exception {
		if(connection != null && !connection.isClosed()) {
			connection.close();
			Log.info("Database connection closed!");
		}
	}
	
	protected static String safeConnectionURL(String url) {
		return url.substring(0, url.indexOf("?"));
	}
	
}
