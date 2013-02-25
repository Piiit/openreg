package data;

import java.util.ArrayList;
import database.DatabaseTools;
import database.Row;

public class Student {

	private Long id;
	private String name;
	private String surname;
	private SimpleDate birthday;
	private Long classID;
	private Long addressID;
	private int enrolmentYear;

	public Student(Long id, String name, String surname, SimpleDate birthday, int enrolmentYear, Long classID, Long addressID) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.birthday = birthday;
		this.enrolmentYear = enrolmentYear;
		this.classID = classID;
		this.addressID = addressID;
	}
	
	public Student(String name, String surname, SimpleDate birthday, int enrolmentYear, Long classID, Long addressID) {
		this(null, name, surname, birthday, enrolmentYear, classID, addressID);
	}
	
	public static ArrayList<Student> getAll() throws Exception {
		ArrayList<Student> students = new ArrayList<Student>();
		ArrayList<Row> rows = DatabaseTools.getQueryResult("SELECT * FROM student");
		for(Row row : rows) {
			Student newStudent = new Student(
					row.getValueAsLong("id"),
					row.getValueAsString("name"), 
					row.getValueAsString("surname"),
					SimpleDate.fromDate(row.getValueAsDate("birthday")),
					row.getValueAsInt("enrolment_year"),
					row.getValueAsLong("class_id"),
					row.getValueAsLong("address_id")
					);
			students.add(newStudent);
		}
		return students;
	}
	
	public void store() throws Exception {
		if(id == null) {
			DatabaseTools.executeUpdate(
					"INSERT INTO student (name, surname, birthday, enrolment_year, class_id, address_id) VALUES (?, ?, ?, ?, ?, ?)",
					getName(),
					getSurname(),
					getBirthday().getSqlDate(),
					getEnrolmentYear(),
					getClassID(),
					getAddressID()
					);
		} else {
			DatabaseTools.executeUpdate(
					"INSERT INTO student (id, name, surname, birthday, enrolment_year, class_id, address_id) VALUES (?, ?, ?, ?, ?, ?, ?)",
					id,
					getName(),
					getSurname(),
					getBirthday().getSqlDate(),
					getEnrolmentYear(),
					getClassID(),
					getAddressID()
					);
		}
	}
	
	public void delete() throws Exception {
		Class.delete(id);
	}
	
	public static void delete(long id) throws Exception {
		DatabaseTools.executeUpdate("DELETE FROM student WHERE id = ?", id);
	}
	
	public Long getAddressID() {
		return addressID;
	}

	public void setAddressID(long addressID) {
		this.addressID = addressID;
	}

	public void setID(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public SimpleDate getBirthday() {
		return birthday;
	}

	public void setBirthday(SimpleDate birthday) {
		this.birthday = birthday;
	}

	public int getEnrolmentYear() {
		return enrolmentYear;
	}

	public void setEnrolmentYear(int enrolmentYear) {
		this.enrolmentYear = enrolmentYear;
	}

	public Long getID() {
		return id;
	}

	public Long getClassID() {
		return classID;
	}

	public void setClassID(long classID) {
		this.classID = classID;
	}
}
