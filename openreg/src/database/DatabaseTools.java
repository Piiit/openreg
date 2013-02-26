package database;

import java.sql.Statement;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;

import log.Log;

/**
 * Returns an iterable ArrayList of Rows that can be easily accessed.
 * Some ideas taken from prom/database written by A. Janes
 * @author Peter Moser (pemoser)
 *
 */
public class DatabaseTools {
	
	public static ArrayList<Row> getQueryResult(final String query, final Object... parameters) throws Exception {
		Connection con = DatabaseConnection.getConnection();
		PreparedStatement prepStatement = con.prepareStatement(query);
		
		for(int i = 0; i < parameters.length; i++) {
			prepStatement.setObject(i + 1, parameters[i]);
		}

		ResultSet rs = prepStatement.executeQuery();
		ArrayList<Row> result = new ArrayList<Row>();

		while(rs.next()) {
			ResultSetMetaData meta = rs.getMetaData();
			Row row = new Row();
			for(int i = 1; i <= meta.getColumnCount(); i++) {
				row.setValue(meta.getColumnName(i), rs.getObject(i));
			}
			result.add(row);
		}

		Log.info("Getting query result: " + prepStatement.toString());

		return result;
	}
	
	public static ArrayList<Row> getQueryResult(final String query) throws Exception {
		return getQueryResult(query, new Object[0]);
	}
	
	public static Object executeUpdate(final String query, final Object... parameters) throws Exception {
		Connection con = DatabaseConnection.getConnection();
		PreparedStatement prepStatement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		
		for(int i = 0; i < parameters.length; i++) {
			prepStatement.setObject(i + 1, parameters[i]);
		}
		
		int updatedLines = prepStatement.executeUpdate();
		
		Log.info("Executing update (" + updatedLines + " rows): " + prepStatement.toString());

		ResultSet rs = prepStatement.getGeneratedKeys();
		if (rs.next()) {
		  return rs.getObject(1);
		}
		
		return null;
	}
	
	public static Object executeUpdate(final String query) throws Exception {
		return executeUpdate(query, new Object[0]);
	}
	
	public static Long getRandomID() {
		SecureRandom sr = new SecureRandom();
		return sr.nextLong();
	}
}
