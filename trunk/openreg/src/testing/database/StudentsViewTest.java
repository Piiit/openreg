package testing.database;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import data.SimpleDate;
import database.DatabaseConnection;
import database.Row;
import database.query.AddressQuery;
import database.query.ClassQuery;
import database.query.StudentQuery;

public class StudentsViewTest {

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
		address.setValue("no", "s1");
		address.setValue("zip_code", "s1");
		address.setValue("city", "s1");
		address.setValue("country", "s1");
		Long addressId = AddressQuery.insert(address);
		
		Row student = new Row();
		student.setValue("name", "Test1");
		student.setValue("surname", "Test2");
		student.setValue("birthday", new SimpleDate(16, 5, 1981).toSqlDate());
		student.setValue("enrolment_year", 1990);
		student.setValue("address_id", addressId);
		student.setValue("class_id", classId);
		
		StudentQuery.insert(student);
	}

}
