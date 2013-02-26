package database;

import java.util.ArrayList;
import log.Log;

public class StudentsView implements DatabaseView {
	
	@Override
	public ArrayList<Row> getDataset() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Row> getDataset(Object id) throws Exception {
		Log.info("Loading student with ID " + id.toString());
		return DatabaseTools.getQueryResult(
				"SELECT * FROM student st " +
				"INNER JOIN class cl ON cl.id = class_id " +
				"INNER JOIN address ad ON ad.id = address_id " +
				"LEFT JOIN ability_description ab ON ab.id = ability_description_id " +
				"WHERE st.id = ?", id);
	}

	@Override
	public void insert(ArrayList<Row> rows) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(ArrayList<Row> rows) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Object id) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<Row> getFullDataset() throws Exception {
		return DatabaseTools.getQueryResult("SELECT * FROM student " +
				"INNER JOIN class cl ON class_id = cl.id " +
				"INNER JOIN address ad ON address_id = ad.id " +
				"LEFT JOIN ability_description ab ON ability_description_id = ab.id");
	}

}
