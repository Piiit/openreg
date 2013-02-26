package database;

import java.util.ArrayList;

public class AddressView {

	public static ArrayList<Row> getDataset() throws Exception {
		return null;
	}

	public static ArrayList<Row> getDataset(Object id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public static ArrayList<Row> getFullDataset() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public static Long insert(Row row) throws Exception {
		return (Long)DatabaseTools.executeUpdate(
				"INSERT INTO address (street, no, zip_code, city, country) VALUES (?, ?, ?, ?, ?)",
				row.getValueAsString("street"),
				row.getValueAsString("no"),
				row.getValueAsString("zip_code"),
				row.getValueAsString("city"),
				row.getValueAsString("country")
				);

	}

	public static void update(ArrayList<Row> rows) throws Exception {
		// TODO Auto-generated method stub

	}

	public static void delete(Object id) throws Exception {
		// TODO Auto-generated method stub

	}

}
