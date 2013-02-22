package data;

import database.DatabaseTools;

public class Address {
	private Long id = null;
	private String street;
	private String number;
	private String zipCode;
	private String city;
	private String country;
	
	public Address(Long id, String street, String number, String zipCode, String city, String country) {
		super();
		this.id = id;
		this.street = street;
		this.number = number;
		this.zipCode = zipCode;
		this.city = city;
		this.country = country;
	}
	public Address(String street, String number, String zipCode, String city, String country) {
		this(null, street, number, zipCode, city, country);
	}
	public Long getID() {
		return id;
	}
	public void setID(Long id) {
		this.id = id;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	public void store() throws Exception {
		DatabaseTools.executeUpdate(
				"INSERT INTO address (id, street, no, zip_code, city, country) VALUES (?, ?, ?, ?, ?, ?)",  
				id,
				getStreet(),
				getNumber(),
				getZipCode(),
				getCity(),
				getCountry()
				);
	}
	
	public void delete() throws Exception {
		Class.delete(id);
	}
	
	public static void delete(long id) throws Exception {
		DatabaseTools.executeUpdate("DELETE FROM address WHERE id = ?", id);
	}
	
}
