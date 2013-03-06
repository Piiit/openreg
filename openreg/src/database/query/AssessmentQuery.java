package database.query;

import java.util.ArrayList;
import log.Log;
import database.DatabaseTools;
import database.Row;

public class AssessmentQuery {

	public static ArrayList<Row> getDataset() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public static ArrayList<Row> getDataset(Object id) throws Exception {
		return null;
//		Log.info("Loading teacher with ID " + id.toString());
//		return DatabaseTools.getQueryResult(
//				"SELECT te.id AS teacher_id, * FROM  teacher te " +
//				"INNER JOIN address ad ON ad.id = address_id " +
//				"WHERE te.id = ? " +
//				"ORDER BY te.surname, te.name", (Long)id);
	}

	public static ArrayList<Row> getFullDataset() throws Exception {
		return DatabaseTools.getQueryResult("SELECT a.id AS assessment_id, " +
				"a.description AS assessment_description, " +
				"at.description AS assessment_type_description, " +
				"t.description AS topic_description FROM assessment a " +
				"INNER JOIN assessment_type at ON a.id = at.id " +
				"INNER JOIN topic t ON a.topic_id = t.id");
	
	}

	public static Long insert(Row row) throws Exception {
		return null;
//		return (Long)DatabaseTools.executeUpdate(
//				"INSERT INTO teacher (name, surname, login, password, birthday, " +
//				"address_id, phone_number, picture, notes) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
//				row.getValueAsString("name"),
//				row.getValueAsString("surname"),
//				row.getValueAsString("login"),
//				row.getValueAsString("password"),
//				row.getValueAsSimpleDate("birthday").toSqlDate(),
//				row.getValueAsLong("address_id"),
//				row.getValueAsString("phone_number"),
//				row.getValue("picture"),
//				row.getValueAsString("notes")
//				);
	}

	public static void update(Object id, Row row) throws Exception {
//		DatabaseTools.executeUpdate(
//				"UPDATE teacher SET name = ?, surname = ?, login = ?, password = ?, " +
//				"birthday = ?, address_id = ?, phone_number = ?, picture = ?, " +
//				"notes = ? WHERE id = ?",
//				row.getValueAsString("name"),
//				row.getValueAsString("surname"),
//				row.getValueAsString("login"),
//				row.getValueAsString("password"),
//				row.getValueAsSimpleDate("birthday").toSqlDate(),
//				row.getValueAsLong("address_id"),
//				row.getValueAsString("phone_number"),
//				row.getValue("picture"),
//				row.getValueAsString("notes"),
//				id
//				);
	}

	public static void delete(Object id) throws Exception {
//		DatabaseTools.executeUpdate("DELETE FROM teacher WHERE id = ?", (Long)id);
	}

}
