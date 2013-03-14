package database.query;

import java.util.ArrayList;
import log.Log;
import database.DatabaseTools;
import database.Row;

public class AssessmentStudentQuery {

	public static ArrayList<Row> getDataset() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	

	public static ArrayList<Row> getDataset(Object id) throws Exception {
		Log.info("Loading teacher with ID " + id.toString());
		return DatabaseTools.getQueryResult(
				"SELECT ass.id AS assessment_student_id, a1.description AS main_description," +
						"a2.description AS sub_description, * FROM assessment_student ass " +
						"INNER JOIN student on student_id = student.id " + 
						"INNER JOIN weighted_assessment wa ON weighted_assessment_main_id = wa.main_assessment_id " +
						"AND weighted_assessment_sub_id = wa.sub_assessment_id " +
						"LEFT JOIN mark on mark_id = mark.id " +
						"INNER JOIN assessment a1 on weighted_assessment_main_id = a1.id " + 
						"INNER JOIN assessment a2 on weighted_assessment_sub_id = a2.id " +
						"WHERE assessment_student_id = ? ", (Long)id);
	}
	
	public static ArrayList<Row> getFullDataset() throws Exception {
		return DatabaseTools.getQueryResult("SELECT ass.id AS assessment_student_id, a1.description AS main_description," +
				"a2.description AS sub_description, mark.representation, * FROM assessment_student ass " +
				"INNER JOIN student on student_id = student.id " + 
				"INNER JOIN weighted_assessment wa ON weighted_assessment_main_id = wa.main_assessment_id " +
				"AND weighted_assessment_sub_id = wa.sub_assessment_id " +
				"INNER JOIN assessment a1 ON weighted_assessment_main_id = a1.id " + 
				"INNER JOIN assessment a2 ON weighted_assessment_sub_id = a2.id " +
				"inner join student_main_assignment_view smav on smav.student_id = ass.student_id " +
				"and smav.id = ass.weighted_assessment_sub_id " +
				"inner join mark on ass.mark_id = mark.id");
	}
	
	public static ArrayList<Row> getFullDataset1(Object id) throws Exception {
		return DatabaseTools.getQueryResult("SELECT ass.id AS assessment_student_id, a1.description AS main_description," +
				"a2.id as assessment_id, a2.description AS assessment_description, mark.representation, * FROM assessment_student ass " +
				"INNER JOIN student on student_id = student.id and student_id = ?" + 
				"INNER JOIN weighted_assessment wa ON weighted_assessment_main_id = wa.main_assessment_id " +
				"AND weighted_assessment_sub_id = wa.sub_assessment_id " +
				"INNER JOIN assessment a1 ON weighted_assessment_main_id = a1.id " + 
				"INNER JOIN assessment a2 ON weighted_assessment_sub_id = a2.id " +
				"inner join student_main_assignment_view smav on smav.student_id = ass.student_id " +
//				"and smav.id = ass.weighted_assessment_sub_id " +
				"inner join mark on ass.mark_id = mark.id ", (Long) id);
	}
		
	public static Long insert(Row row) throws Exception {
		return (Long)DatabaseTools.executeUpdate(
				"INSERT INTO assessment_student (student_id, weighted_assessment_main_id, weighted_assessment_sub_id, " +
				"mark_id, mark, date, differntiated_evaluation, notes) VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
				row.getValueAsLong("student_id"),
				row.getValueAsLong("weighted_assessment_main_id"),
				row.getValueAsLong("weighted_assessment_sub_id"),
				row.getValueAsLong("mark_id"),
				Double.parseDouble(row.getValueAsString("mark")),
				row.getValueAsSimpleDate("date").toSqlDate(),
				row.getValue("differntiated_evaluation"),
				row.getValueAsString("notes")
				);
	}
	
	public static ArrayList<Row> getSubAssessments(Object assessmentId, Object studentId) throws Exception {
		return DatabaseTools.getQueryResult(
				"SELECT * FROM weighted_assessment " +
				"INNER JOIN assessment ON sub_assessment_id = id " +
				"INNER JOIN assessment_student ON sub_assessment_id = weighted_assessment_sub_id " +
				"WHERE weighted_assessment_main_id = ? " +
				"AND student_id = ? ORDER BY description", assessmentId, studentId);
	}

	public static ArrayList<Row> getDataset(Object mainId, Object subId, Object studentId) throws Exception {
		return DatabaseTools.getQueryResult(
				"SELECT * FROM assessment_student " + 
				"WHERE weighted_assessment_main_id = ? AND weighted_assessment_sub_id = ? " +
				"AND student_id = ?", mainId, subId, studentId);
	}
	
	public static void update(Long mainId, Long subId, Long studentId, Row row) throws Exception{
		DatabaseTools.executeUpdate(
				"UPDATE assessment_student SET mark = ? " +
				"WHERE weighted_assessment_main_id = ? AND weighted_assessment_sub_id = ? AND student_id = ?", 
				Double.parseDouble(row.getValueAsString("mark")),
				mainId,
				subId,
				studentId
				);
	}
	
	public static void update(Object id, Row row) throws Exception {
		DatabaseTools.executeUpdate(
				"UPDATE assessment_student SET mark = ? " +
				"WHERE id = ?",
				row.getValueAsLong("student_id"),
				row.getValueAsLong("weighted_assessment_main_id"),
				row.getValueAsLong("weighted_assessment_sub_id"),
				row.getValueAsLong("mark_id"),
				Double.parseDouble(row.getValueAsString("mark")),
				row.getValueAsSimpleDate("date").toSqlDate(),
				row.getValue("differntiated_evaluation"),
				row.getValueAsString("notes"),
				(Long)id);
	}

	public static void delete(Object id) throws Exception {
		DatabaseTools.executeUpdate("DELETE FROM assessment_student WHERE id = ?", (Long)id);
	}
	
}
