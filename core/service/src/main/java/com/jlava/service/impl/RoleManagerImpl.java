package com.jlava.service.impl;

import com.jlava.model.Person;
import com.jlava.model.Role;
import com.jlava.dao.RoleDao;
import com.jlava.service.PersonManager;
import com.jlava.service.RoleManager;
import java.util.*;

import org.hibernate.ObjectNotFoundException;

public class RoleManagerImpl implements RoleManager{
	private RoleDao roleDao;
	private PersonManager personManager;

	public RoleManagerImpl(PersonManager personManager) {
		this.roleDao = new RoleDao();
		this.personManager = personManager;
	}

	public void addRole(String roleCode, String roleDesc) {
		Role role = new Role(roleCode, roleDesc);
		Long roleId = roleDao.addRole(role);	
		if(roleId != null) {
			System.out.println("[New role added] : " + role.getId() + " - " + roleDesc);
		}
	}

	public void updateRole(Long roleId, String roleDesc) {
		Role role = getRole(roleId);
		if(role != null) {
			role.setRoleDesc(roleDesc);
			int updated = roleDao.updateRole(role);
			
			if(updated > 0) {
				System.out.println("[Successfully updated role " + roleId + " to " + roleDesc + "]");
			} else {
				System.out.println("[Update Failed]");
			}
		}
	}

	public void deleteRole(Long roleId) {
		Role role = getRole(roleId);
		if(role != null) {
			// Get all person with role = roleId
			List<Person> persons = getPersonsWithRole(roleId);

			if(persons!= null && persons.size() > 0) {
			// delete all person role with roleId = roleId;
				persons.forEach(person -> {
					personManager.deletePersonRole(person, roleId);
				});
			}

			int deleted = roleDao.deleteRole(role);

			if(deleted > 0) {
				System.out.println("[Successfully deleted role " + roleId + "]");	
			} else {
				System.out.println("[Deletion Failed]");
			}
		}		
	}

	public List<Role> listRoles() {
		List<Role> roles= roleDao.getRoles();
		if(roles != null && roles.size() > 0) {
			System.out.println("\n[ ROLES ]");
			roles.forEach(role -> {
				System.out.println("\t["+role.getId()+"] " + role.getRoleDesc());
			});
		} else {
			System.out.println("[No roles found]");
			return null;
		}

		return roles;
	}

	public Role getRole(Long roleId) {
		Role role = roleDao.getRole(roleId);
		if(role == null) {
			System.out.println("[Role with id " + roleId + " not found]");
		}

		return role;
	}

	public List<Person> getPersonsWithRole(Long roleId) {
		List<Person> persons = roleDao.getPersonsWithRole(roleId);

		if(persons != null && persons.size() < 1) {
			System.out.println("[No persons with role " + roleId + " found]");
		} else {
			System.out.println(persons.size() + " persons found.");
		}

		return persons;
	}

	public boolean roleExists(Long roleId) {
		return (getRole(roleId) != null)?true:false;
	}

	public boolean validCodeDesc(String roleDesc) {
		boolean valid = true;
		for(Role role : listRoles()) {
			if(role.getRoleDesc().equals(roleDesc)) {
				return false;
			}
		}

		return valid;
	}

	public boolean validCodeDesc(String code, String roleDesc) {
		boolean valid = true;
		for(Role role : listRoles()) {
			if(role.getCode().equals(code) || role.getRoleDesc().equals(roleDesc)) {
				return false;
			}
		}

		return valid;
	}
}