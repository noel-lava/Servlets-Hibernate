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

		// List roles
		// Edit role
		// Delete role
		// Add role
		// List persons by role


		/*request.setAttribute("persons", persons);
		request.setAttribute("sortBy", sortBy);
		request.setAttribute("searchResult", searchResult);
		request.setAttribute("deleteResult", deleteResult);
*/
		RequestDispatcher dispatcher = request.getRequestDispatcher(dispatchTo);
		dispatcher.forward(request, response);
	}
}