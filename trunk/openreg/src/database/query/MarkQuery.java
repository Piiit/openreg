package database.query;

import java.util.ArrayList;

import database.DatabaseTools;
import database.Row;

/**
 * This is just a template to copy/paste for new Query classes!
 */

public class MarkQuery {
	
	/**
	 * Selecting tuples, that are needed to choose from, when inserting a new tuple (e.g. in drop-down menus)
	 */
	public static ArrayList<Row> getDataset() throws Exception {
		return null;
	}
	
	/**
	 * Selecting specific tuples by id, returning an ArrayList
	 * Note: Please keep they return value as it is, to standardize our queries classes. 
	 */
	public static ArrayList<Row> getDataset(Object id) throws Exception{
		return DatabaseTools.getQueryResult("SELECT * FROM mark m WHERE id = ? ", id);
	}
	
	public static ArrayList<Row> getAllMarksOfType(Object id) throws Exception {
		return DatabaseTools.getQueryResult(
				"SELECT *, m.id AS mark_id FROM mark m " +
				"INNER JOIN mark_type mt ON m.mark_type_id = mt.id " +
				"WHERE mark_type_id = ? " +
				"ORDER BY bound DESC", 
				id);
	}

	/**
	 * Returns all tuples needed to list them inside GUI-modules.
	 */
	public static ArrayList<Row> getFullDataset() throws Exception{
		return null;
	}
	
	/**
	 * Inserts a new tuple.
	 * @return ID of the new tuple formerly inserted.
	 */
	public static Long insert(Row row) throws Exception{
		return (Long)DatabaseTools.executeUpdate(
				"INSERT INTO mark (representation, bound, mark_type_id) VALUES (?, ?, ?)", 
				row.getValueAsString("representation"),
				row.getValue("bound"),
				row.getValueAsLong("mark_type_id"));
	}
	
	/**
	 * Update a certain tuple.
	 */
	public static void update(Object id, Row row) throws Exception {
		DatabaseTools.executeUpdate(
				"UPDATE mark SET representation = ?, bound = ? WHERE id = ?",
				row.getValueAsString("representation"),
				row.getValue("bound"),
				id);
	}
	
	/**
	 * Delete a certain tuple.
	 */
	public static void delete(Object id) throws Exception {
		DatabaseTools.executeUpdate("DELETE FROM mark WHERE id = ?", id);
	}
}
