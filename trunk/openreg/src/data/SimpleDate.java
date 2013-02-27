package data;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Note: SimpleDate starts with 1 when indexing months, java.util.Date starts with 0...
 * @author user
 *
 */
public class SimpleDate {
	
	private Date date;
	private Calendar calendar;

	public SimpleDate(int day, int month, int year) {
		calendar = GregorianCalendar.getInstance();
		calendar.set(year, month - 1, day);
		date = calendar.getTime();
	}

	public Date toDate() {
		return date;
	}
	
	public int getDay() {
		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	public int getMonth() {
		return calendar.get(Calendar.MONTH) + 1;
	}

	public int getYear() {
		return calendar.get(Calendar.YEAR);
	}
	
	public java.sql.Date toSqlDate() {
		return new java.sql.Date(calendar.getTimeInMillis());
	}
	
	public static SimpleDate fromDate(Date date) {
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(date);
		return new SimpleDate(cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.YEAR));
	}

	@Override
	public String toString() {
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		return df.format(date);	
	}

}
