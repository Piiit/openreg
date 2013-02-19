package data;

import java.sql.Date;
import java.util.ArrayList;

import database.DatabaseTools;
import database.Row;

public class Student {

	private Long id;
	private String name;
	private String surname;
	private Date birthday;
	private int addressID;
	private Class studentsClass;
	private int enrolmentYear;
	
	public Student(String name, String surname, Date birthday, int enrolmentYear) {
		super();
		this.name = name;
		this.surname = surname;
		this.birthday = birthday;
		this.enrolmentYear = enrolmentYear;
	}
	
	public static ArrayList<Student> getAllStudents() throws Exception {
		ArrayList<Student> students = new ArrayList<Student>();
		ArrayList<Row> rows = DatabaseTools.getQueryResult(
				"SELECT * FROM student INNER JOIN class cl ON class_id=cl.id"
				);
		for(Row row : rows) {
			Student newStudent = new Student(
					row.getValueAsString("name"), 
					row.getValueAsString("surname"),
					(Date)row.getValue("birthday"),
					(int)row.getValue("enrolment_year")
					);
			Class thisClass = new Class(
					row.getValueAsString("level"), 
					row.getValueAsString("stream"),
					row.getValueAsString("notes")
					);
			thisClass.setID(Long.valueOf(row.getValueAsString("class_id")));
			newStudent.setID((Long)row.getValue("id"));
			newStudent.setStudentsClass(thisClass);
			students.add(newStudent);
		}
		return students;
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

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public int getAddressID() {
		return addressID;
	}

	public void setAddressID(int addressID) {
		this.addressID = addressID;
	}

	public int getEnrolmentYear() {
		return enrolmentYear;
	}

	public void setEnrolmentYear(int enrolmentYear) {
		this.enrolmentYear = enrolmentYear;
	}

	public Long getId() {
		return id;
	}

	public Class getStudentsClass() {
		return studentsClass;
	}

	public void setStudentsClass(Class studentsClass) {
		this.studentsClass = studentsClass;
	}
	
	
}
