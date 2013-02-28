package database.query;

import java.util.ArrayList;

import database.DatabaseTools;
import database.Row;

public class AddressQuery {

	public static ArrayList<Row> getDataset() throws Exception {
		return null;
	}

	public static ArrayList<Row> getDataset(Object id) throws Exception {
		return DatabaseTools.getQueryResult("SELECT * FROM address WHERE id = ?", (Long)id);
	}

	public static ArrayList<Row> getFullDataset() throws Exception {
		return DatabaseTools.getQueryResult("SELECT * FROM address ORDER BY street, no, city");
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

	public static void update(Object id, Row row) throws Exception {
		DatabaseTools.executeUpdate(
				"UPDATE address SET street = ?, no = ?, zip_code = ?, city = ?, country = ? " +
				"WHERE id = ?",
				row.getValueAsString("street"),
				row.getValueAsString("no"),
				row.getValueAsString("zip_code"),
				row.getValueAsString("city"),
				row.getValueAsString("country"),
				(Long)id
				);
	}

	public static void delete(Object id) throws Exception {
		// TODO Auto-generated method stub

	}

}
