package com.jlava.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.FetchType;
import javax.persistence.ElementCollection;
import javax.persistence.CollectionTable;

@Entity
@Table(name="contact")
public class Contact extends BaseModel{
	@Column(name="contact_desc")
	private String contactDesc;

	@Column(name="contact_type_id", updatable=false, insertable=false)
	private Long typeId;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="contact_type_id", nullable=false, updatable=false)
	private ContactType contactType;

	/*@ElementCollection
	@CollectionTable(name="contact_type", joinColumns=@JoinColumn(name="contact_type_id"))
	@Column(name="type_desc")
	private String contactType;*/

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="person_id", nullable=false)
	private Person person;

	public Contact() {}
	public Contact(Long typeId, String contactDesc) {
		this.contactDesc = contactDesc;
		this.typeId = typeId;
	}

	public String getContactDesc() {
		return contactDesc;
	}

	public void setContactDesc(String contactDesc) {
		this.contactDesc = contactDesc;
	}

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	/*public String getContactType() {
		return contactType;
	}

	public void setContactType(String contactType) {
		this.contactType = contactType;
	}*/

	public ContactType getContactType() {
		return contactType;
	}

	public void setContactType(ContactType contactType) {
		this.contactType = contactType;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
}