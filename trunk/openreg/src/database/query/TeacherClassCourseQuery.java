package database.query;

import java.util.ArrayList;
import log.Log;
import database.DatabaseTools;
import database.Row;

public class TeacherClassCourseQuery {

	public static ArrayList<Row> getDataset() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public static ArrayList<Row> getDataset(Object id) throws Exception {
		Log.info("Loading teacher with ID " + id.toString());
		return DatabaseTools.getQueryResult("SELECT te.name AS teacher_name, co.name AS class_name, * FROM teacher_class_course tcc" +
				"INNER JOIN teacher te ON teacher_id = te.id " +
				"INNER JOIN class cl ON class_id = cl.id " +
				"INNER JOIN course co ON course_id = co.id WHERE teacher_id = ? " +
				"ORDER BY teacher_id, class_id, course_id", (Long)id
				);
	}

	public static ArrayList<Row> getFullDataset() throws Exception {
		return DatabaseTools.getQueryResult("SELECT  * FROM teacher_class_course tcc" +
				"INNER JOIN teacher te ON teacher_id = te.id " +
				"INNER JOIN class cl ON class_id = cl.id " +
				"INNER JOIN course co ON course_id = co.id"
				);
	
	}

	public static Long insert(Row row) throws Exception {
		return (Long)DatabaseTools.executeUpdate(
				"INSERT INTO teacher_class_course (teacher_id, class_id, course_id) VALUES (?, ?, ?)",
				row.getValueAsLong("teacher_id"),
				row.getValueAsLong("class_id"),
				row.getValueAsLong("course_id")
				);
	}

	public static void update(Row newValues, Row updateValues) throws Exception {
		DatabaseTools.executeUpdate(
				"UPDATE teacher_class_course SET teacher_id = ?, class_id = ?, course_id = ? " +
				"WHERE teacher_id = ? AND class_id = ? AND course_id = ?",
				newValues.getValueAsLong("teacher_id"),
				newValues.getValueAsLong("class_id"),
				newValues.getValueAsLong("course_id"),
				updateValues.getValueAsLong("teacher_id"),
				updateValues.getValueAsLong("class_id"),
				updateValues.getValueAsLong("course_id")
				);
	}

	public static void delete(Row row) throws Exception {
		Log.info(DatabaseTools.executeUpdate("DELETE FROM teacher_class_course WHERE " +
				"teacher_id = ? AND class_id = ? AND course_id = ?", 
				row.getValueAsLong("teacher_id"),
				row.getValueAsLong("class_id"),
				row.getValueAsLong("course_id")).toString());
	}

}
