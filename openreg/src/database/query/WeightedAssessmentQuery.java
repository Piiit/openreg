package database.query;

import java.util.ArrayList;

import database.DatabaseTools;
import database.Row;

public class WeightedAssessmentQuery {
	
	public static ArrayList<Row> getAncestors(Object id) throws Exception {
		return DatabaseTools.getQueryResult(
				"SELECT * FROM weighted_assessment " +
				"INNER JOIN assessment ON main_assessment_id = id " +
				"WHERE sub_assessment_id = ? ORDER BY description", id);
	}
	
	public static ArrayList<Row> getSubAssessments(Object id) throws Exception {
		return DatabaseTools.getQueryResult(
				"SELECT * FROM weighted_assessment " +
				"INNER JOIN assessment ON sub_assessment_id = id " +
				"WHERE main_assessment_id = ? ORDER BY description", id);
	}

	public static Long insert(Row row) throws Exception {
		return (Long)DatabaseTools.executeUpdate(
				"INSERT INTO weighted_assessment (main_assessment_id, sub_assessment_id, weight) VALUES (?, ?, ?)",
					row.getValueAsLong("main_assessment_id"),
					row.getValueAsLong("sub_assessment_id"),
					row.getValueAsInt("weight")
				);
	}
	
	public static void delete(Long mainId, Long subId) throws Exception {
		DatabaseTools.executeUpdate(
				"DELETE FROM weighted_assessment " +
				"WHERE main_assessment_id = ? AND sub_assessment_id = ?",
				mainId, 
				subId);
	} 
}
