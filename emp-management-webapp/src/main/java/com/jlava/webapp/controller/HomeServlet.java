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

import com.jlava.service.PersonManager;
import com.jlava.service.impl.PersonManagerImpl;
import com.jlava.model.Person;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {
	PersonManager personManager;
	HttpSession session;
	String searchResult;
	String deleteResult;	

	public void init() throws ServletException {
		this.personManager = new PersonManagerImpl();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session = request.getSession();
		int sortBy;

		if((Integer)session.getAttribute("sortBy") != null) {
			sortBy = (Integer)session.getAttribute("sortBy");
		} else {
			sortBy = 3;
			session.setAttribute("sortBy", sortBy);
		}

		List<Person> persons = personManager.listPersons(sortBy);
		session.setAttribute("persons", persons);
		session.setAttribute("searchResult", searchResult);
		session.setAttribute("deleteResult", deleteResult);

		RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session = request.getSession();
		String dispatchTo = "home";
		searchResult = "";
		deleteResult = "";
		int sortBy = (Integer)session.getAttribute("sortBy");

		if(request.getParameter("search") != null) {
			String searchId = request.getParameter("searchId");

			if(searchId != null) {
				try {
					Long personId = Long.valueOf(searchId);
					Person person = personManager.getPerson(personId);
					if(person != null) {
						session.setAttribute("person", person);
						dispatchTo = "manage-person";
					} else {
						searchResult = "Person with id " + searchId + " not found";
					}
				} catch(Exception e) {
					searchResult = "Invalid Id";
				}
			} 
		} else if(request.getParameter("delete") != null) {
			Long personId = null;
			String deleteId = request.getParameter("delete");
			if(deleteId != null) {
				try {
					personId = Long.valueOf(deleteId);
					int deleted = personManager.deletePerson(personId);
					deleteResult = 	(deleted > 0)?"Successfully deleted person with id = " + personId:"Cannot delete person with id = " + personId;
				} catch(Exception e) {
					deleteResult = "Person with id " + personId + " does not exist";				
				}
			} 
		} else if(request.getParameter("sortBy") != null) {
			sortBy = Integer.parseInt(request.getParameter("sortBy"));
		}

		if(dispatchTo.equals("home")) {
			session.setAttribute("sortBy", sortBy);
			session.setAttribute("searchResult", searchResult);
			session.setAttribute("deleteResult", deleteResult);
		}
		
		response.sendRedirect(dispatchTo);	
	}
}