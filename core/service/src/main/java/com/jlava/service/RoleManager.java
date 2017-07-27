package com.jlava.service;

import com.jlava.model.Role;
import com.jlava.model.Person;
import java.util.List;

public interface RoleManager {
	void addRole(String roleDesc);
	void updateRole(int roleId, String roleDesc);
	void deleteRole(int roleId);
	int listRoles();
	Role getRole(int roleId);
	List<Person> getPersonsWithRole(int roleId);
}