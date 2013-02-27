package testing.database;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import data.SimpleDate;
import database.Row;

public class RowTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		Row student = new Row();
		student.setValue("birthdaySQL", new SimpleDate(16, 5, 1981).toSqlDate());
		student.setValue("birthday", new SimpleDate(16, 5, 1981));
		
		assertEquals(null, student.getValueAsSimpleDate("I am not here!"));
		assertEquals(5, student.getValueAsSimpleDate("birthdaySQL").getMonth());
		assertEquals(5, student.getValueAsSimpleDate("birthday").getMonth());
		assertEquals(1981, student.getValueAsSimpleDate("birthdaySQL").getYear());
		assertEquals(1981, student.getValueAsSimpleDate("birthday").getYear());

	}

}
