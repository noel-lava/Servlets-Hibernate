package com.jlava.webapp.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jlava.service.RoleManager;
import com.jlava.service.PersonManager;
import com.jlava.service.impl.PersonManagerImpl;
import com.jlava.service.impl.RoleManagerImpl;
import com.jlava.model.Role;
import com.jlava.model.Person;

@WebServlet("/roles")
public class ManageRolesServlet extends HttpServlet {
	RoleManager roleManager;

	public void init() throws ServletException {
		this.roleManager = new RoleManagerImpl(new PersonManagerImpl());
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String dispatchTo = "/roles.jsp";
		String operationResult = "";
		Long sortRoles = new Long(0);

		if(request.getParameter("updateRole") != null) {
			Long id = Long.valueOf(request.getParameter("updateRole"));
			String roleDesc = request.getParameter("roleDesc-" +id);

			if(roleDesc != null && !roleDesc.isEmpty()) {
				if(roleManager.validCodeDesc(roleDesc)) {
					roleManager.updateRole(id, roleDesc);	
					operationResult = "Role Updated"; 	
				} else {
					operationResult = "Description already exist";
				}
			} else {
				operationResult = "Invalid Role Description.";
			}
		} else if (request.getParameter("deleteRole") != null) {
			Long id = Long.valueOf(request.getParameter("deleteRole"));

			if(roleManager.roleExists(id)) {
				roleManager.deleteRole(id);	
				operationResult = "Successfully Deleted Role"; 
			} else {
				operationResult = "Role Does Not Exist";
			}
		} else if (request.getParameter("addNewRole") != null) {
			// Add role
			String newCode = request.getParameter("newCode");
			String newRole = request.getParameter("newRole");

			if(newCode != null && newRole != null 
				&& !newRole.isEmpty() && !newCode.isEmpty()) {
				if(roleManager.validCodeDesc(newCode, newRole)) {
					roleManager.addRole(newCode, newRole);	
					operationResult = "New Role Added"; 
				} else {
					operationResult = "Role already exist";
				}
			} else {
				operationResult = "Invalid Code or Description.";
			}
		}

		List<Role> roles = roleManager.listRoles();
		List<Person> persons = null;

		if(roles != null && request.getParameter("filter") != null) {
			// List persons by role
			Long roleId = Long.valueOf(request.getParameter("filterBy"));
			sortRoles = roleId;
			persons = roleManager.getPersonsWithRole(sortRoles);
		} else if(roles != null) {
			sortRoles = roles.get(0).getId();
			persons = roleManager.getPersonsWithRole(sortRoles);
		}

		request.setAttribute("roles", roles);
		request.setAttribute("sortRoles", sortRoles);
		request.setAttribute("persons", persons);
		request.setAttribute("operationResult", operationResult);

		RequestDispatcher dispatcher = request.getRequestDispatcher(dispatchTo);
		dispatcher.forward(request, response);
	}
}