package testing.data;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import data.Class;
import data.Address;
import data.SimpleDate;
import data.Student;
import database.DatabaseConnection;

public class StudentTest {

	private Long studentId = new Long(1);
	private Long classId = new Long(1);
	private Long addressId = new Long(1);

	@Before
	public void setUp() throws Exception {
		DatabaseConnection.setup(DatabaseConnection.TESTURL);
	}

	@After
	public void tearDown() throws Exception {
		Student.delete(studentId);
		Class.delete(classId);
		Address.delete(addressId);
		DatabaseConnection.close();
	}
	
	@Test
	public void test() throws Exception {
		
		int stCount = Student.getAllStudents().size();
		Address ad1 = new Address(addressId, "1", "2", "3", "4", "5");
		ad1.store();
		Class cl1 = new Class(classId, "lvl1");
		cl1.store();
		Student st1 = new Student(studentId, "Pinco", "Pallino", new SimpleDate(22, 5, 1990), 2000, classId, addressId);
		st1.store();
		assertEquals(stCount+1, Student.getAllStudents().size());
	}

}
