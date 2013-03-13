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
				"SELECT DISTINCT ass.id, ass.description, ass.notes, " +
				"ass_s.weighted_assessment_main_id, ass_s.student_id, ass_s.date, " +
				"wa.main_assessment_id FROM assessment_student ass_s " +
				"LEFT JOIN weighted_assessment wa ON ass_s.weighted_assessment_main_id = wa.main_assessment_id " +
				"LEFT JOIN assessment ass ON wa.main_assessment_id = ass.id " +
				"WHERE wa.sub_assessment_id IS NOT NULL");
	
	}
	
	public static ArrayList<Row> getStudentDataset() throws Exception {
		
		return DatabaseTools.getQueryResult(
				"SELECT DISTINCT s.id, s.name, s.surname, s.class_id, " +
				"ass_s.weighted_assessment_main_id, ass_s.student_id, ma.representation, " +
				"wa.main_assessment_id FROM student s " +
				"LEFT JOIN assessment_student ass_s ON s.id  = ass_s.student_id " +
				"LEFT JOIN mark ma ON ma.id = ass_s.mark_id " +
				"LEFT JOIN class cla ON cla.id = s.class_id " +
				"LEFT JOIN weighted_assessment wa ON ass_s.weighted_assessment_main_id = wa.main_assessment_id ");
	
	}
	
	public static ArrayList<Row> getStudentDataset(Object id) throws Exception {
		
		return DatabaseTools.getQueryResult(
				"SELECT DISTINCT s.id, s.name, s.surname, s.class_id, " +
				"ass_s.weighted_assessment_main_id, ass_s.student_id, ma.representation, " +
				"wa.main_assessment_id FROM student s " +
				"LEFT JOIN assessment_student ass_s ON s.id  = ass_s.student_id " +
				"LEFT JOIN mark ma ON ma.id = ass_s.mark_id " +
				"LEFT JOIN class cla ON cla.id = s.class_id " +
				"LEFT JOIN weighted_assessment wa ON ass_s.weighted_assessment_main_id = wa.main_assessment_id " +
				"WHERE s.id = ?", id);
	
	}
	
	public static ArrayList<Row> getSubAssessmentDataset(Object id) throws Exception {
		
		return DatabaseTools.getQueryResult(
				"SELECT ass_s.weighted_assessment_main_id, ass_s.differentiated_evaluation," +
				"ass_s.weighted_assessment_sub_id, " +
				"ass_s.student_id, ass_s.mark_id, " +
				"ass.description, " +
				"ma.representation FROM assessment_student ass_s " +
				"LEFT JOIN mark ma ON ma.id  = ass_s.mark_id " +
				"LEFT JOIN student s ON ass_s.student_id = s.id " +
				"LEFT JOIN assessment ass ON ass.id  = ass_s.weighted_assessment_sub_id " +
				"WHERE s.id = ?", id);
	
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
