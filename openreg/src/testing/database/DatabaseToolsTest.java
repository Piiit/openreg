package testing.database;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import database.DatabaseConnection;
import database.DatabaseTools;

public class DatabaseToolsTest {

	@Before
	public void setUp() throws Exception {
		DatabaseConnection.setup(DatabaseConnection.getConnectionURL());
	}

	@After
	public void tearDown() throws Exception {
		DatabaseConnection.close();
	}

	@Test
	public void testQueryWithoutSetup() throws Exception {
		try {
			DatabaseTools.getQueryResult("SELECT * FROM student WHERE class_id=? AND ability_description_id=?", 1, 20);
		} catch (Exception e) {
			fail("Initialization of database connection failed!");
		}
	}

}
