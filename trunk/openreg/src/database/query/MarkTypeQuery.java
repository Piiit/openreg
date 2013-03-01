package database.query;

import java.util.ArrayList;

import database.DatabaseTools;
import database.Row;

/**
 * This is just a template to copy/paste for new Query classes!
 */

public class MarkTypeQuery {
	
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
		return null;
	}
	
	/**
	 * Returns all tuples needed to list them inside GUI-modules.
	 */
	public static ArrayList<Row> getFullDataset() throws Exception{
		return DatabaseTools.getQueryResult("SELECT * FROM mark_type ORDER BY description");
	}
	
	/**
	 * Inserts a new tuple.
	 * @return ID of the new tuple formerly inserted.
	 */
	public static Long insert(Row row) throws Exception{
		return null;
	}
	
	/**
	 * Update a certain tuple.
	 */
	public static void update(Object id, Row row) throws Exception{
	}
	
	/**
	 * Delete a certain tuple.
	 */
	public static void delete(Object id) throws Exception{
	}
}
