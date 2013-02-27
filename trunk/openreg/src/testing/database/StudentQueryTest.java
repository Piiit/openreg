package testing.database;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import data.SimpleDate;
import database.DatabaseConnection;
import database.Row;
import database.query.AddressQuery;
import database.query.ClassQuery;
import database.query.StudentQuery;

public class StudentQueryTest {

	@Before
	public void setUp() throws Exception {
		DatabaseConnection.setup(DatabaseConnection.getConnectionURL());
	}

	@After
	public void tearDown() throws Exception {
		DatabaseConnection.close();
	}

	@Test
	public void test() throws Exception {
		
		Row studentsClass = new Row();
		studentsClass.setValue("level", "7");
		Long classId = ClassQuery.insert(studentsClass);
		
		Row address = new Row();
		address.setValue("street", "s1");
		address.setValue("no", "n1");
		address.setValue("zip_code", "z1");
		address.setValue("city", "c1");
		address.setValue("country", "co1");
		Long addressId = AddressQuery.insert(address);
		
		Row student = new Row();
		student.setValue("name", "Test1");
		student.setValue("surname", "Test2");
		student.setValue("birthday", new SimpleDate(16, 5, 1981).toSqlDate());
		student.setValue("enrolment_year", 1990);
		student.setValue("address_id", addressId);
		student.setValue("class_id", classId);
		
		Long studentId = StudentQuery.insert(student);
		
		ArrayList<Row> rows = StudentQuery.getDataset(studentId);
		assertEquals(1, rows.size());
		Row fetchedStudent = rows.get(0);
		assertEquals("Test1", fetchedStudent.getValueAsString("name"));
		assertEquals(addressId, fetchedStudent.getValueAsLong("address_id"));
		assertEquals(classId, fetchedStudent.getValueAsLong("class_id"));
		
	}

}
