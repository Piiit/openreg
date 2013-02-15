package database;

import java.util.HashMap;

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

	@Override
	public String toString() {
		return "Row [" + data + "]";
	}
	
	
}
