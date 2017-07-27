package com.jlava.model;

public class Contact {
	private int contactId;
	private String landline;
	private String mobileNo;
	private String email;
	private Person person;

	public Contact() {}
	public Contact(String landline, String mobileNo, String email) {
		this.landline = landline;
		this.mobileNo = mobileNo;
		this.email = email;
	}

	public int getContactId() {
		return contactId;
	}

	public void setContactId(int contactId) {
		this.contactId = contactId;
	}

	public String getLandline() {
		return landline;
	}

	public void setLandline(String landline) {
		this.landline = landline;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
}