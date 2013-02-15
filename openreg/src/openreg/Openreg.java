package openreg;

import java.util.ArrayList;
import database.DatabaseTools;
import database.Row;

public class Openreg {

	public static void main(String[] args) {
		try {
			
			ArrayList<Row> rows = DatabaseTools.getQueryResult(
					"SELECT * FROM student " +
					"INNER JOIN ability_description ad ON ability_description_id = ad.id " +
					"WHERE class_id = ? AND ability_description_id = ?", 1, 1);
			
			for(Row row : rows) {
				System.out.println(row.getValue("name") + " " + row.getValue("description"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
