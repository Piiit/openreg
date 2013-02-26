package database;

import java.util.ArrayList;
import log.Log;

public class StudentsView {
	
	public static ArrayList<Row> getDataset() {
		// TODO Auto-generated method stub
		return null;
	}

	public static ArrayList<Row> getDataset(Object id) throws Exception {
		Log.info("Loading student with ID " + id.toString());
		return DatabaseTools.getQueryResult(
				"SELECT * FROM student st " +
				"INNER JOIN class cl ON cl.id = class_id " +
				"INNER JOIN address ad ON ad.id = address_id " +
				"LEFT JOIN ability_description ab ON ab.id = ability_description_id " +
				"WHERE st.id = ?", id);
	}

	public static void insert(ArrayList<Row> rows) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public static void update(ArrayList<Row> rows) throws Exception {
		// TODO Auto-generated method stub
		
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
