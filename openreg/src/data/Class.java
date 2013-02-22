package data;

import java.util.ArrayList;

import database.DatabaseTools;
import database.Row;

public class Class {
	
	private Long id = null;
	private String level; //NOT NULL
	private String stream = null; 
	private String notes = null;

	public Class(Long id, String level) {
		if(level == null || id == null) {
			throw new NullPointerException("Class: id and level must be set!");
		}
		this.level = level;
		this.id = id;
	}
	
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
	
	public static Class getClassByID(Long id) throws Exception {
		ArrayList<Row> rows = DatabaseTools.getQueryResult("SELECT * FROM class WHERE id = ? LIMIT 1", id);
		Row row = rows.get(0);
		return new Class(
				row.getValueAsString("level"), 
				row.getValueAsString("stream"),
				row.getValueAsString("notes")
				);
	}
	
	public void store() throws Exception {
		if(id == null) {
			DatabaseTools.executeUpdate( 
					"INSERT INTO class (level, stream, notes) VALUES (?, ?, ?)",
					getLevel(),
					getStream(),
					getNotes()
					);
		} else {
			DatabaseTools.executeUpdate(
					"INSERT INTO class (id, level, stream, notes) VALUES (?, ?, ?, ?)", 
					id,
					getLevel(),
					getStream(),
					getNotes()
					);
		}
		
	}
	
	public void delete() throws Exception {
		Class.delete(id);
	}
	
	public static void delete(long id) throws Exception {
		DatabaseTools.executeUpdate("DELETE FROM class WHERE id = ?", id);
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
