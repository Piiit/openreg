package database;

import java.util.Date;
import java.util.HashMap;

import data.SimpleDate;

/**
 * Some ideas taken from prom/database written by A. Janes
 * @author Peter Moser (pemoser)
 *
 */
public class Row {

	HashMap<String,Object> data = new HashMap<String,Object>();
	
	public void setValue(String key, Object value) {
		data.put(key, value);
	}
	
	public Object getValue(String key) {
		return data.get(key);
	}

	public String getValueAsString(String key) {
		if(data.get(key) == null) {
			return null;
		}
		return data.get(key).toString();
	}
	
	public String getValueAsStringNotNull(String key) {
		if(data.get(key) == null) {
			return "";
		}
		return data.get(key).toString();
	}
	
	public Long getValueAsLong(String key) {
		if(data.get(key) == null) {
			return null;
		}
		return (Long)data.get(key);
	}
	
	public Integer getValueAsInt(String key) {
		if(data.get(key) == null) {
			return null;
		}
		return (Integer)data.get(key);
	}
	
	public Date getValueAsDate(String key) {
		if(data.get(key) == null) {
			return null;
		}
		return (Date)data.get(key);
	}
	
	public SimpleDate getValueAsSimpleDate(String key) {
		if(data.get(key) == null) {
			return null;
		}
		return SimpleDate.fromDate((Date)data.get(key));
	}

	@Override
	public String toString() {
		return "Row=" + data + ";";
	}
	
	
}
