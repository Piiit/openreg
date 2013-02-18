package data;

import java.util.ArrayList;

import database.DatabaseTools;
import database.Row;

public class Class {
	
	private Long id;
	private String level;
	private String stream;
	private String notes;
	
	public Class(String level, String stream) {
		this.level = level;
		this.stream = stream;
	}

	public static ArrayList<Class> getAllClasses() throws Exception {
		ArrayList<Class> classes = new ArrayList<Class>();
		ArrayList<Row> rows = DatabaseTools.getQueryResult("SELECT * FROM class");
		for(Row row : rows) {
			Class newClass = new Class(row.getValue("level").toString(), row.getValue("stream").toString());
			newClass.setID(Long.parseLong(row.getValue("id").toString()));
			newClass.setNotes(row.getValue("notes").toString());
			classes.add(newClass);
		}
		return classes;
	}
	
	public static void addNewClass(Class cl) throws Exception {
		DatabaseTools.executeUpdate(
				"INSERT INTO class (level, stream, notes) VALUES (?, ?, ?)", 
				cl.getLevel(),
				cl.getStream(),
				cl.getNotes()
				);
	}

	public static void removeAllClasses() throws Exception {
		DatabaseTools.executeUpdate("DELETE FROM class");
	}

	
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getStream() {
		return stream;
	}

	public void setStream(String stream) {
		this.stream = stream;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Long getID() {
		return id;
	}

	public void setID(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return level + stream;
	}
	
	
	
}
