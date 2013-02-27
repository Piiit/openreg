package data;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class SimpleDate {
	
	private Date date;
	private Calendar calendar;

	public SimpleDate(int day, int month, int year) {
		calendar = GregorianCalendar.getInstance();
		calendar.set(year, month, day);
		date = calendar.getTime();
	}

	public Date toDate() {
		return date;
	}
	
	public int getDay() {
		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	public int getMonth() {
		return calendar.get(Calendar.MONTH);
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
		return new SimpleDate(cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH), cal.get(Calendar.YEAR));
	}

	@Override
	public String toString() {
		SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
		return df.format(date);	
	}

}
