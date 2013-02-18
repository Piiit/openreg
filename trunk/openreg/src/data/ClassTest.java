package data;

import static org.junit.Assert.*;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
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
		
		Class.addNewClass(new Class("1", "A"));
		Class.addNewClass(new Class("2", "B"));
		Class.addNewClass(new Class("3", "C"));
		Class.addNewClass(new Class("1", "B"));
		
		ArrayList<Class> classes = Class.getAllClasses();
		
		assertEquals(4, classes.size());
		assertEquals("1A", classes.get(0).toString());
		assertEquals("2B", classes.get(1).toString());
		assertEquals("3C", classes.get(2).toString());
		assertEquals("1B", classes.get(3).toString());
		
		Class.removeAllClasses();
		classes = Class.getAllClasses();
		assertEquals(0, classes.size());
	}

}
