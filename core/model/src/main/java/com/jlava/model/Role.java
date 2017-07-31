package com.jlava.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.ManyToMany;
import javax.persistence.FetchType;

@Entity
@Table(name="role")
public class Role extends BaseModel{
	@Column(name="role_code")
	private String code;

	@Column(name="role")
	private String roleDesc;

	@ManyToMany(fetch=FetchType.LAZY, mappedBy="roles")
	private List<Person> persons;

	public Role() {}
	public Role(String code, String roleDesc) {
		this.code = code;
		this.roleDesc = roleDesc;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	public List<Person> getPersons() {
		return persons;
	}

	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}
}