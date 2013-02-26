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

	public static void update(Row row) throws Exception {
		DatabaseTools.executeUpdate(
				"UPDATE address SET street = ?, no = ?, zip_code = ?, city = ?, country = ?" +
				"WHERE id = ?",
				row.getValueAsString("street"),
				row.getValueAsString("no"),
				row.getValueAsString("zip_code"),
				row.getValueAsString("city"),
				row.getValueAsString("country"),
				row.getValueAsLong("id")
				);
	}

	public static void delete(Object id) throws Exception {
		// TODO Auto-generated method stub

	}

}
