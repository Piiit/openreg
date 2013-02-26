package database;

import java.util.ArrayList;

public class ClassesView {

	public static ArrayList<Row> getDataset() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public static ArrayList<Row> getDataset(Object id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public static ArrayList<Row> getFullDataset() throws Exception {
		return DatabaseTools.getQueryResult("SELECT * FROM class");
	}

	public static Long insert(ArrayList<Row> rows) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public static void update(ArrayList<Row> rows) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public static void delete(Object id) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
