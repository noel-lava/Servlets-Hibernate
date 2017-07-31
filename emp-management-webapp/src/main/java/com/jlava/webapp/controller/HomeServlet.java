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

	public void init() throws ServletException {
		this.personManager = new PersonManagerImpl();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String dispatchTo = "index.jsp";
		String searchResult = "";
		String deleteResult = "";
		int sortBy;

		try {
			sortBy = Integer.parseInt(request.getParameter("sortBy"));
		} catch (Exception e) {
			sortBy = 3;
		}

		if(request.getParameter("sort") != null) {
			System.out.println("SORT BY : " + sortBy);
		} else if(request.getParameter("search") != null) {
			String searchId = request.getParameter("searchId");

			if(searchId != null) {
				try {
					Long personId = Long.valueOf(searchId);
					System.out.println("SEARCH : " + personId);
					if(personManager.getPerson(personId) != null) {
						// if id is found, forward to Person Page DISPATCH TO
						System.out.println("FOUND : " + personId);
					} else {
						// else return, display 'Person with id -id- not found'
						searchResult = "Person with id " + searchId + " not found";
					}
				} catch(Exception e) {
					searchResult = "Invalid Id";
				}
			} 
		} else if(request.getParameter("action") != null
					&& request.getParameter("action").equals("delete")) {
			Long personId = null;
			String deleteId = request.getParameter("id");
			if(deleteId != null) {
				try {
					personId = Long.valueOf(deleteId);
					int deleted = personManager.deletePerson(personId);
					deleteResult = 	(deleted > 0)?"Successfully deleted person with id is " + personId:"Cannot delete person with id = " + personId;
				} catch(Exception e) {
					deleteResult = "Person with id " + personId + " does not exist";				
				}
			} 
			System.out.println("DELETE : " + deleteId);
		}

		List<Person> persons = personManager.listPersons(sortBy);
		request.setAttribute("persons", persons);
		request.setAttribute("sortBy", sortBy);
		request.setAttribute("searchResult", searchResult);
		request.setAttribute("deleteResult", deleteResult);

		RequestDispatcher dispatcher = request.getRequestDispatcher(dispatchTo);
		dispatcher.forward(request, response);
	}
}