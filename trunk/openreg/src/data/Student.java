package data;

import java.sql.Date;
import java.util.ArrayList;

import database.DatabaseTools;
import database.Row;

public class Student {

	private Long id;
	private String name;
	private String surname;
	private Birthday birthday;
	private Long classID;
	private Long addressID;
	private int enrolmentYear;
	
	public Student(String name, String surname, Birthday birthday, int enrolmentYear) {
		super();
		this.name = name;
		this.surname = surname;
		this.birthday = birthday;
		this.enrolmentYear = enrolmentYear;
	}
	
	public static ArrayList<Student> getAllStudents() throws Exception {
		ArrayList<Student> students = new ArrayList<Student>();
		ArrayList<Row> rows = DatabaseTools.getQueryResult("SELECT * FROM student");
		for(Row row : rows) {
			Student newStudent = new Student(
					row.getValueAsString("name"), 
					row.getValueAsString("surname"),
					Birthday.fromDate((Date)row.getValue("birthday")),
					(int)row.getValue("enrolment_year")
					);
			newStudent.setID((Long)row.getValue("id"));
			students.add(newStudent);
		}
		return students;
	}
	
	public static void addNewStudent(Student student) throws Exception {
		DatabaseTools.executeUpdate(
				"INSERT INTO student (name, surname, birthday, enrolment_year, class_id, address_id) VALUES (?, ?, ?, ?, ?, ?)",
				student.getName(),
				student.getSurname(),
				student.getBirthday(),
				student.getEnrolmentYear(),
				student.getClassID(),
				student.getAddressID()
				);
	}
	
	public Long getAddressID() {
		return addressID;
	}

	public void setAddressID(Long addressID) {
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

	public Birthday getBirthday() {
		return birthday;
	}

	public void setBirthday(Birthday birthday) {
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

	public void setStudentsClass(Long classID) {
		this.classID = classID;
	}
}
