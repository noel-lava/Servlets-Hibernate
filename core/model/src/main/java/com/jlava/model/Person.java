package com.jlava.model;

import java.util.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.OneToMany;
import javax.persistence.ManyToMany;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Entity
@Table(name="person")
@FilterDef(name="contactsFilter", 
	parameters=@ParamDef(name="isDeleted", type="boolean"))
public class Person extends BaseModel{
	private Name name;

	@Temporal(TemporalType.DATE)
	@Column(name="birth_date")
	private Date birthDate;

	@Column(name="gwa")
	private Float gwa;

	@Temporal(TemporalType.DATE)
	@Column(name="date_hired")
	private Date dateHired;

	@Column(name="employed")
	private Boolean employed;
	private Address address;

	@OneToMany(fetch=FetchType.EAGER, mappedBy="person", cascade=CascadeType.ALL)
	@Filter(name="contactsFilter", condition="deleted = :isDeleted")
	private Set<Contact> contacts = new HashSet<Contact>(0);
	
	@ManyToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinTable(name="person_role", joinColumns = {@JoinColumn(name="person_id", updatable=false)},
		inverseJoinColumns = {@JoinColumn(name="role_id", updatable=false)})
	private Set<Role> roles = new HashSet<Role>(0);

	public Person(){}
	public Person(Name name, Date birthDate, Float gwa, Date dateHired, boolean employed, Address address) {
		this.name = name;
		this.birthDate = birthDate;
		this.gwa = gwa;
		this.dateHired = dateHired;
		this.employed = employed;
		this.address = address;
	}

	public Name getName() {
		return name;
	}

	public void setName(Name name) {
		this.name = name;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public Float getGwa() {
		return gwa;
	}

	public void setGwa(Float gwa) {
		this.gwa = gwa;
	}

	public Date getDateHired() {
		return dateHired;
	}

	public void setDateHired(Date dateHired) {
		this.dateHired = dateHired;
	}

	public Boolean isEmployed() {
		return employed;
	}

	public void setEmployed(Boolean employed) {
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

	public String getDateHiredToString() {
		DateFormat ndf = new SimpleDateFormat("yyyy-MM-dd");
		return (this.dateHired != null) ? ndf.format(this.dateHired) : "";
	}

	public String getBirthDateToString() {
		DateFormat ndf = new SimpleDateFormat("yyyy-MM-dd");
		return (this.birthDate != null)?ndf.format(this.birthDate):"";
	}
}