package testing.data;

import static org.junit.Assert.*;
import java.util.Calendar;
import java.util.Date;
import org.junit.Test;
import data.SimpleDate;

public class SimpleDateTest {

	private static final int EPSILON = 1;

	@Test
	public void test() {
		
		// Notice: Date counts the months from 0 = JANUARY...
		SimpleDate date = new SimpleDate(16, 4, 1981);
		assertEquals(4, date.getMonth());
		assertEquals("1981-05-16", date.toSqlDate().toString());
		
		Calendar calendar;
		calendar = Calendar.getInstance();
		calendar.set(1981, Calendar.MAY, 16);
		Date date2 = calendar.getTime();
		
		// SimpeDate starts with 1 = JANUARY, java.util.Date starts with 0 = JANUARY...
		assertEquals(calendar.get(Calendar.MONTH), date.getMonth());
		
		// Sometimes there are small rounding errors within two "same" times...
		assertTrue(Math.abs(date2.getTime() - date.toDate().getTime()) <= EPSILON);

		date = new SimpleDate(new Date(calendar.getTimeInMillis()));
		assertEquals(4, date.getMonth());
	}
}
