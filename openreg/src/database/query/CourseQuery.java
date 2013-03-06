package database.query;

import java.util.ArrayList;

import database.DatabaseTools;
import database.Row;

public class CourseQuery {
	
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
		return DatabaseTools.getQueryResult("SELECT * FROM course WHERE id = ?", id);
	}
	
	/**
	 * Returns all tuples needed to list them inside GUI-modules.
	 */
	public static ArrayList<Row> getFullDataset() throws Exception{
		return DatabaseTools.getQueryResult("SELECT * FROM course ORDER BY name ASC");
	}
	
	/**
	 * Inserts a new tuple.
	 * @return ID of the new tuple formerly inserted.
	 */
	public static Long insert(Row row) throws Exception{
		return (Long)DatabaseTools.executeUpdate(
				"INSERT INTO course (name, credit_points) VALUES (?, ?)",
				row.getValueAsString("name"),
				row.getValueAsInt("credit_points")
				);
	}
	
	/**
	 * Update a certain tuple.
	 */
	public static void update(Object id, Row row) throws Exception {
		DatabaseTools.executeUpdate(
				"UPDATE course SET name = ?, credit_points = ? WHERE id = ?",
				row.getValueAsString("name"),
				row.getValueAsInt("credit_points"),
				id);
	}
	
	/**
	 * Delete a certain tuple.
	 */
	public static void delete(Object id) throws Exception {
		DatabaseTools.executeUpdate("DELETE FROM course WHERE id = ?", id);
	}
}
