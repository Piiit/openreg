package testing.data;

import static org.junit.Assert.*;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import data.Class;
import database.DatabaseConnection;

public class ClassTest {

	@Before
	public void setupTest() throws Exception {
		DatabaseConnection.setup(DatabaseConnection.TESTURL);
		Class.removeAllClasses();
	}
	
	@After
	public void tearDown() throws Exception {
		Class.removeAllClasses();
		DatabaseConnection.close();
	}
	
	@Test
	public void test() throws Exception {
		
		(new Class(new Long(13), "1st")).store();
		(new Class("2", "B", "Some information")).store();
		(new Class("3", "C")).store();
		(new Class("1", "B")).store();
		
		ArrayList<Class> classes = Class.getAllClasses();
		
		assertEquals(0, classes.get(0).size());
		
		assertEquals(4, classes.size());
		assertEquals("1st", classes.get(0).toString());
		assertEquals(new Long(13), classes.get(0).getID());
		assertEquals("2B", classes.get(1).toString());
		assertEquals("Some information", classes.get(1).getNotes());
		assertEquals("3C", classes.get(2).toString());
		assertEquals("1B", classes.get(3).toString());
		
		Class.removeAllClasses();
		classes = Class.getAllClasses();
		assertEquals(0, classes.size());
	}

}
