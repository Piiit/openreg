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
	
//	select weighted_assessment_main_id, weighted_assessment_sub_id, a1.description as main_description, a2.description as sub_description from assview
//	inner join assessment a1 on a1.id = weighted_assessment_main_id
//	inner join assessment a2 on a2.id = weighted_assessment_sub_id

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
						"WHERE assessment_student_id = ?", (Long)id);
	}

	public static ArrayList<Row> getFullDataset() throws Exception {
		return DatabaseTools.getQueryResult("SELECT ass.id AS assessment_student_id, a1.description AS main_description," +
				"a2.description AS sub_description, * FROM assessment_student ass " +
				"INNER JOIN student on student_id = student.id " + 
				"INNER JOIN weighted_assessment wa ON weighted_assessment_main_id = wa.main_assessment_id " +
				"AND weighted_assessment_sub_id = wa.sub_assessment_id " +
				"INNER JOIN assessment a1 on weighted_assessment_main_id = a1.id " + 
				"INNER JOIN assessment a2 on weighted_assessment_sub_id = a2.id " +
				"LEFT JOIN mark on mark_id = mark.id ");
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

	public static void update(Object id, Row row) throws Exception {
		DatabaseTools.executeUpdate(
				"UPDATE assessment_student SET student_id = ?, weighted_assessment_main_id = ?, weighted_assessment_sub_id = ?, " +
				"mark_id = ?, mark = ?, date = ?, differntiated_evaluation = ?, notes = ?, " +
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
