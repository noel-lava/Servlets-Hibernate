package com.jlava.model;

import java.util.*;

public class Person {
	private int personId;
	private String lastName;
	private String firstName;
	private String midName;
	private String suffix;
	private String title;
	private Date birthDate;
	private float gwa;
	private Date dateHired;
	private boolean employed;
	private Address address;
	private Set<Contact> contacts = new HashSet<Contact>(0);
	private Set<Role> roles = new HashSet<Role>(0);

	public Person(){}
	public Person(String lastName, String firstName, String midName, String suffix, String title,
				Date birthDate, float gwa, Date dateHired, boolean employed, Address address) {
		this.lastName = lastName;
		this.firstName = firstName;
		this.midName = midName;
		this.suffix = suffix;
		this.title = title;
		this.birthDate = birthDate;
		this.gwa = gwa;
		this.dateHired = dateHired;
		this.employed = employed;
		this.address = address;
	}

	public int getPersonId() {
		return personId;
	}

	public void setPersonId(int personId) {
		this.personId = personId;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMidName() {
		return midName;
	}

	public void setMidName(String midName) {
		this.midName = midName;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public float getGwa() {
		return gwa;
	}

	public void setGwa(float gwa) {
		this.gwa = gwa;
	}

	public Date getDateHired() {
		return dateHired;
	}

	public void setDateHired(Date dateHired) {
		this.dateHired = dateHired;
	}

	public boolean isEmployed() {
		return employed;
	}

	public void setEmployed(boolean employed) {
		this.employed = employed;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Set<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(Set<Contact> contacts) {
		this.contacts = contacts;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles	 = roles;
	}
}