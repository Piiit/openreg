package database.query;

import java.util.ArrayList;
import log.Log;
import database.DatabaseTools;
import database.Row;

public class GradeQuery {

	public static ArrayList<Row> getDataset() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static ArrayList<Row> getDataset(Object id) throws Exception {
		return DatabaseTools.getQueryResult(
				"SELECT a.id AS assessment_id, a.notes, " +
				"a.description AS assessment_description, " +
				"ast.description AS assessment_type_description, " +
				"t.description AS topic_description FROM assessment a " +
				"LEFT JOIN assessment_type ast ON a.assessment_type_id = ast.id " +
				"LEFT JOIN topic t ON a.topic_id = t.id " +
				"WHERE a.id = ? ORDER BY a.description DESC", id);
	}

	public static ArrayList<Row> getFullDataset() throws Exception {
		
		return DatabaseTools.getQueryResult(
				"SELECT a.id AS assessment_id, a.notes, " +
				"a.description AS assessment_description, " +
				"ast.description AS assessment_type_description, " +
				"t.description AS topic_description FROM assessment a " +
				"LEFT JOIN assessment_type ast ON a.assessment_type_id = ast.id " +
				"LEFT JOIN topic t ON a.topic_id = t.id");
	
	}
	
	/**
	 * Selecting specific tuples by id, returning an ArrayList
	 * Note: Please keep they return value as it is, to standardize our queries classes. 
	 */
	public static ArrayList<Row> getCourseDataset(Object id) throws Exception{
		
		return DatabaseTools.getQueryResult(
				"SELECT a.id AS assessment_id, a.notes, " +
				"a.description AS assessment_description, " +
				"ast.description AS assessment_type_description, " +
				"t.description AS topic_description, t.course_id FROM assessment a " +
				"INNER JOIN assessment_type ast ON a.assessment_type_id = ast.id " +
				"INNER JOIN topic t ON a.topic_id = t.id " +
				"WHERE course_id = ?", id);
		
	}

	public static Long insert(Row row) throws Exception {
		return (Long)DatabaseTools.executeUpdate(
				"INSERT INTO assessment (assessment_type_id, topic_id, description, notes) VALUES (?, ?, ?, ?)",
					row.getValueAsLong("assessment_type_id"),
					row.getValueAsLong("topic_id"),
					row.getValueAsString("description"),
					row.getValueAsString("notes")
				);
	}

	public static void update(Object id, Row row) throws Exception {
		DatabaseTools.executeUpdate(
				"UPDATE assessment SET assessment_type_id = ?, topic_id = ?, description = ?, notes = ? WHERE id = ?", 
					row.getValueAsLong("assessment_type_id"),
					row.getValueAsLong("topic_id"),
					row.getValueAsString("description"),
					row.getValueAsString("notes"),
					id
				);
	}

	public static void delete(Object id) throws Exception {
		DatabaseTools.executeUpdate("DELETE FROM assessment WHERE id = ?", (Long)id);
	}

}
