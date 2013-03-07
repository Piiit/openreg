package database.query;
import java.util.ArrayList;

import log.Log;

import database.DatabaseTools;
import database.Row;

public class TopicQuery {

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
		return DatabaseTools.getQueryResult(
				
				"SELECT t.id, t.description, t.course_id, t.topic_id, co.name, " +
				"t2.description AS main_topic_description " +
				"FROM topic t LEFT JOIN course co ON t.course_id = co.id " +
				"LEFT JOIN topic t2 ON t.topic_id = t2.topic_id " +
				"WHERE t.id = ? ORDER BY t.description DESC", id);
	}
	
	/**
	 * Selecting specific tuples by id, returning an ArrayList
	 * Note: Please keep they return value as it is, to standardize our queries classes. 
	 */
	public static ArrayList<Row> getCourseDataset(Object id) throws Exception{
		return DatabaseTools.getQueryResult("SELECT * FROM topic WHERE course_id = ?", id);
		/*
		return DatabaseTools.getQueryResult(
				"SELECT t.id AS top_id, t.description AS sub_topic_description, * FROM topic t " +
				"INNER JOIN course co ON t.course_id = co.id " +
				"INNER JOIN topic t2 ON t.course_id = t2.course_id " +
				"WHERE t.course_id = ? ORDER BY t.description DESC", id);
		*/
	}
	
	/**
	 * Returns all tuples needed to list them inside GUI-modules.
	 */
	public static ArrayList<Row> getFullDataset() throws Exception{
		return DatabaseTools.getQueryResult("SELECT * FROM topic ORDER BY description");
		/*return DatabaseTools.getQueryResult(
				"SELECT t.id AS top_id, t.description AS sub_topic_description, * FROM topic t " +
				"INNER JOIN course co ON t.course_id = co.id " +
				"INNER JOIN topic t2 ON t.course_id = t2.course_id ORDER BY t.description DESC");
		*/
	}
	
	/**
	 * Inserts a new tuple.
	 * @return ID of the new tuple formerly inserted.
	 */
	public static Long insert(Row row) throws Exception{
		return (Long)DatabaseTools.executeUpdate(
				"INSERT INTO topic (description, course_id, topic_id) VALUES (?, ?, ?)",
				row.getValueAsString("description"),
				row.getValueAsLong("course_id"),
				row.getValueAsLong("topic_id")
				);
	}
	
	/**
	 * Update a certain tuple.
	 */
	public static void update(Object id, Row row) throws Exception{
		DatabaseTools.executeUpdate(
				"UPDATE topic SET description = ?, course_id = ?, topic_id = ? WHERE id = ?", 
				row.getValueAsString("description"),
				row.getValueAsLong("course_id"),
				row.getValueAsLong("topic_id"),
				id
				);
	}
	
	/**
	 * Delete a certain tuple.
	 */
	public static void delete(Object id) throws Exception{
		DatabaseTools.executeUpdate("DELETE FROM topic WHERE id = ?", (Long)id);
	}

}
