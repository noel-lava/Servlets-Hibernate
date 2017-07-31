package com.jlava.service;

import com.jlava.model.Role;
import com.jlava.model.Person;
import java.util.List;

public interface RoleManager {
	void addRole(String roleCode, String roleDesc);
	void updateRole(Long roleId, String roleDesc);
	void deleteRole(Long roleId);
	int listRoles();
	Role getRole(Long roleId);
	List<Person> getPersonsWithRole(Long roleId);
}