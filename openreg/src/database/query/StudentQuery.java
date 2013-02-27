package database.query;

import java.util.ArrayList;

import database.DatabaseTools;
import database.Row;

public class StudentQuery {
	
	/*
	 * Selecting tuples, that are needed to choose from, when inserting a new student (e.g. in drop-down menus)
	 */
	public static ArrayList<Row> getDataset() throws Exception {
		return DatabaseTools.getQueryResult("SELECT * FROM class cl, ability_description ab");
	}

	public static ArrayList<Row> getDataset(Object id) throws Exception {
		return DatabaseTools.getQueryResult(
				"SELECT st.id AS student_id, * FROM student st " +
				"INNER JOIN class cl ON cl.id = class_id " +
				"INNER JOIN address ad ON ad.id = address_id " +
				"LEFT JOIN ability_description ab ON ab.id = ability_description_id " +
				"WHERE st.id = ?", id);
	}

	public static Long insert(Row row) throws Exception {
		return (Long)DatabaseTools.executeUpdate(
				"INSERT INTO student (name, surname, birthday, address_id, class_id, " +
				"phonenumber, enrolment_year, ability_description_id, picture, notes) " +
				"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
				row.getValueAsString("name"),
				row.getValueAsString("surname"),
				row.getValueAsSimpleDate("birthday").toSqlDate(),
				row.getValueAsLong("address_id"),
				row.getValueAsLong("class_id"),
				row.getValueAsString("phonenumber"),
				row.getValueAsInt("enrolment_year"),
				row.getValueAsLong("ability_description_id"),
				row.getValue("picture"),
				row.getValueAsString("notes")
				);
	}

	public static void update(Row row) throws Exception {
		DatabaseTools.executeUpdate(
				"UPDATE student SET name = ?, surname = ?, birthday = ?, address_id = ?, class_id = ?, " +
				"phonenumber = ?, enrolment_year = ?, ability_description_id = ?, picture = ?, notes = ? " +
				"WHERE id = ?",
				row.getValueAsString("name"),
				row.getValueAsString("surname"),
				row.getValueAsSimpleDate("birthday").toSqlDate(),
				row.getValueAsLong("address_id"),
				row.getValueAsLong("class_id"),
				row.getValueAsString("phonenumber"),
				row.getValueAsInt("enrolment_year"),
				row.getValueAsLong("ability_description_id"),
				row.getValue("picture"),
				row.getValueAsString("notes"),
				row.getValueAsLong("id")
				);		
	}

	public static void delete(Object id) throws Exception {
		DatabaseTools.executeUpdate("DELETE FROM student WHERE id = ?", (Long)id);
	}

	public static ArrayList<Row> getFullDataset() throws Exception {
		return DatabaseTools.getQueryResult("SELECT st.id AS student_id, * FROM student st " +
				"INNER JOIN class cl ON class_id = cl.id " +
				"INNER JOIN address ad ON address_id = ad.id " +
				"LEFT JOIN ability_description ab ON ability_description_id = ab.id");
	}
}
