package testing.data;

import static org.junit.Assert.*;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import org.junit.Test;

import data.SimpleDate;

public class SimpleDateTest {

	private static final int EPSILON = 1;

	@Test
	public void test() {
		SimpleDate date = new SimpleDate(16, 5, 1981);
		assertEquals(5, date.getMonth());
		assertEquals("1981-05-16", date.toSqlDate().toString());
		
		Calendar calendar;
		calendar = GregorianCalendar.getInstance();
		calendar.set(1981, Calendar.MAY, 16);
		Date date2 = calendar.getTime();
		
		// SimpeDate starts with 1 = JANUARY, java.util.Date starts with 0 = JANUARY...
		assertEquals(calendar.get(Calendar.MONTH) + 1, date.getMonth());
		
		// Sometimes there are small rounding errors within two "same" times...
		assertTrue(Math.abs(date2.getTime() - date.toDate().getTime()) <= EPSILON);
	}

}
