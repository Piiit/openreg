package database;

import java.sql.Connection;
import java.sql.DriverManager;

import log.Log;

public final class DatabaseConnection {
	
	private static final int LOGINTIMEOUT = 1;  //in seconds
	
	private final static String USER = "ds_group2";
	private final static String PASSWORD = "Iezedoo6";
	public final static String URL = "jdbc:postgresql://alcor.inf.unibz.it:5432/ds_group2?user=" + USER + "&password=" + PASSWORD;

	private final static String TESTUSER = "user";
	private final static String TESTPASSWORD = "qwertz";
	public final static String TESTURL = "jdbc:postgresql://localhost/openreg?user=" + TESTUSER + "&password=" + TESTPASSWORD;
	
	private static Connection connection = null;
	
	public static void setup(final String connectionURL) throws Exception {
		Log.info("Connecting to " + safeConnectionURL(connectionURL) + "...");
		Class.forName("org.postgresql.Driver");
		close();
		DriverManager.setLoginTimeout(LOGINTIMEOUT);
		connection = DriverManager.getConnection(connectionURL);
		Log.info("New database connection established: " + safeConnectionURL(connectionURL));
	}
	
	public static void setup() throws Exception {
		setup(URL);
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
		}
	}
	
	protected static String safeConnectionURL(String url) {
		return url.substring(0, url.indexOf("?"));
	}
	

}