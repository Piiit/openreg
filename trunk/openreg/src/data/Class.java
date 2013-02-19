package data;

import java.util.ArrayList;

import database.DatabaseTools;
import database.Row;

public class Class {
	
	private Long id = null;
	private String level; //NOT NULL
	private String stream = null; 
	private String notes = null;
	
	public Class(String level, String stream, String notes) {
		if(level == null) {
			throw new NullPointerException("Class: level must be set!");
		}
		this.level = level;
		this.stream = stream;
		this.notes = notes;
	}
	
	public Class(String level, String stream) {
		this(level, stream, null);
	}
	
	public Class(String level) {
		this(level, null, null);
	}

	public static ArrayList<Class> getAllClasses() throws Exception {
		ArrayList<Class> classes = new ArrayList<Class>();
		ArrayList<Row> rows = DatabaseTools.getQueryResult("SELECT * FROM class");
		for(Row row : rows) {
			Class newClass = new Class(
					row.getValueAsString("level"), 
					row.getValueAsString("stream"),
					row.getValueAsString("notes")
					);
			newClass.setID((Long)row.getValue("id"));
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

	public int size() throws Exception {
		return DatabaseTools.getQueryResult("SELECT * FROM student WHERE class_id = ?",id).size();
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
		if(id == null) {
			throw new NullPointerException("Class ID can't be null!");
		}
		this.id = id;
	}

	@Override
	public String toString() {
		return level + (stream == null ? "" : stream);
	}
}
