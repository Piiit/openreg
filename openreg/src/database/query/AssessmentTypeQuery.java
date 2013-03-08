package database.query;

import java.util.ArrayList;
import log.Log;
import database.DatabaseTools;
import database.Row;

public class AssessmentTypeQuery {

	public static ArrayList<Row> getDataset() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public static ArrayList<Row> getDataset(Object id) throws Exception {
		return DatabaseTools.getQueryResult(
				"SELECT * FROM assessment_type " +
				"WHERE id = ? ORDER BY description DESC", id);
	}

	public static ArrayList<Row> getFullDataset() throws Exception {
		return DatabaseTools.getQueryResult(
				"SELECT * FROM assessment_type ORDER BY description DESC"
		);
	}
	
	/**
	 * Selecting specific tuples by id, returning an ArrayList
	 * Note: Please keep they return value as it is, to standardize our queries classes. 
	 */
	public static ArrayList<Row> getCourseDataset(Object id) throws Exception{
		return DatabaseTools.getQueryResult("SELECT * FROM assessment WHERE course_id = ?", id);
	}

	public static Long insert(Row row) throws Exception {
		return (Long)DatabaseTools.executeUpdate(
				"INSERT INTO assessment_type (description) VALUES (?)",
				row.getValueAsString("description")
		);
	}

	public static void update(Object id, Row row) throws Exception {
		DatabaseTools.executeUpdate(
				"UPDATE assessment_type SET description = ? WHERE id = ?", 
				row.getValueAsString("description"),
				id
				);
	}

	public static void delete(Object id) throws Exception {
		DatabaseTools.executeUpdate("DELETE FROM assessment WHERE assessment_type_id = ?", id);
		DatabaseTools.executeUpdate("DELETE FROM assessment_type WHERE id = ?", id);
	}

}
