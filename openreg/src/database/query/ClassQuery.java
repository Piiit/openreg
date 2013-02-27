package database.query;

import java.util.ArrayList;

import database.DatabaseTools;
import database.Row;

public class ClassQuery {

	public static ArrayList<Row> getDataset() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public static ArrayList<Row> getDataset(Object id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public static ArrayList<Row> getFullDataset() throws Exception {
		return DatabaseTools.getQueryResult("SELECT * FROM class");
	}

	public static Long insert(Row row) throws Exception {
		return (Long)DatabaseTools.executeUpdate(
				"INSERT INTO class (level, stream, notes) VALUES (?, ?, ?)",
				row.getValueAsString("level"),
				row.getValueAsString("stream"),
				row.getValueAsString("notes")
				);
	}

	public static void update(Row row) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public static void delete(Object id) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
