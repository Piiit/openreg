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

	public static void update(Object id, Row row) throws Exception {
		DatabaseTools.executeUpdate(
				"UPDATE teacher SET name = ?, surname = ?, login = ?, password = ?, " +
				"birthday = ?, address_id = ?, phone_number = ?, picture = ?, " +
				"notes = ? WHERE id = ?",
				row.getValueAsString("name"),
				row.getValueAsString("surname"),
				row.getValueAsString("login"),
				row.getValueAsString("password"),
				row.getValueAsSimpleDate("birthday").toSqlDate(),
				row.getValueAsLong("address_id"),
				row.getValueAsString("phone_number"),
				row.getValue("picture"),
				row.getValueAsString("notes"),
				(Long)id
				);
	}

	public static void delete(Object id) throws Exception {
		DatabaseTools.executeUpdate("DELETE FROM teacher WHERE id = ?", (Long)id);
	}

}