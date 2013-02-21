package testing.data;

import static org.junit.Assert.*;

import java.sql.Date;
import java.text.SimpleDateFormat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import data.Birthday;
import data.Student;
import database.DatabaseConnection;

public class StudentTest {

	@Before
	public void setUp() throws Exception {
		DatabaseConnection.setup(DatabaseConnection.TESTURL);
	}

	@After
	public void tearDown() throws Exception {
		DatabaseConnection.close();
	}

	@Test
	public void test() throws Exception {
		
		int stCount = Student.getAllStudents().size();
		Student st1 = new Student("Pinco", "Pallino", new Birthday(22, 5, 1990), 2000);
		Student.addNewStudent(st1);
		
		assertEquals(stCount+1, Student.getAllStudents().size());
	}

}
