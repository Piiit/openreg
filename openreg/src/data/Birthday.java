package data;

import java.util.Calendar;
import java.util.Date;
import java.sql.*;
import java.util.GregorianCalendar;

public class Birthday {
	
	private Date date;
	private Calendar calendar;

	public Birthday(int day, int month, int year) {
		calendar = GregorianCalendar.getInstance();
		calendar.set(year, month - 1, day);  // months are counted from 0=January, 1=February, ...
		date = calendar.getTime();
	}

	public Date getDate() {
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
	
	public static Birthday fromDate(Date date) {
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(date);
		return new Birthday(cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH), cal.get(Calendar.YEAR));
	}

}
