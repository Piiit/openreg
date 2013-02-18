package database;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

//TODO create a test database
public class DatabaseToolsTest {

	@Before
	public void setUp() throws Exception {
		DatabaseConnection.setup(DatabaseConnection.TESTURL);
	}

	@After
	public void tearDown() throws Exception {
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
