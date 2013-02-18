package database;

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
		
		rs.close();
		prepStatement.close();
		
		return result;
	}
	
	public static int executeUpdate(final String query) throws Exception {
		Connection con = DatabaseConnection.getConnection();
		PreparedStatement prepStatement = con.prepareStatement(query);
		
		Log.info("Executing update: " + prepStatement.toString());
		
		return prepStatement.executeUpdate();
	}
}
