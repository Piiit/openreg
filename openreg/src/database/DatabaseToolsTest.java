package database;

import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DatabaseToolsTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testQueryWithoutSetup() throws Exception {
		ArrayList<Row> rows;
		try {
			//TODO create a test database
			rows = DatabaseTools.getQueryResult(
					"SELECT * FROM student WHERE class_id=? AND ability_description_id=?", 1, 20);
			assertEquals(0, rows.size());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

}
