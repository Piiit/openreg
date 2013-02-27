package database.query;

import java.util.ArrayList;

import database.DatabaseTools;
import database.Row;

public class ClassQuery {

	public static ArrayList<Row> getDataset() throws Exception {
		return null;
	}

	public static ArrayList<Row> getDataset(Object id) throws Exception {
		return DatabaseTools.getQueryResult("SELECT * FROM class WHERE id = ? ORDER BY level, stream", (Long)id);
	}

	public static ArrayList<Row> getFullDataset() throws Exception {
		return DatabaseTools.getQueryResult("SELECT * FROM class ORDER BY level, stream");
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
		DatabaseTools.executeUpdate(
				"UPDATE class SET level = ?, stream = ?, notes = ? WHERE id = ?",
				row.getValueAsString("level"),
				row.getValueAsString("stream"),
				row.getValueAsString("notes"),
				row.getValueAsLong("id")
				);
	}

	public static void delete(Object id) throws Exception {
		DatabaseTools.executeUpdate("DELETE FROM class WHERE id = ?", (Long)id);
	}

}
