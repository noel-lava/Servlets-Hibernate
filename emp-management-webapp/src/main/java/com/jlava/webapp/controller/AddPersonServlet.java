package com.jlava.webapp.controller;

import java.io.IOException;
import java.util.Date;
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
import com.jlava.apputil.AppUtil;

@WebServlet("/add-person")
public class AddPersonServlet extends HttpServlet{
	PersonManager personManager;
	HttpSession session;
	RequestDispatcher dispatcher;

	String dispatchTo;
	String addResult;
	String updateResult;

	public void init() throws ServletException {
		this.personManager = new PersonManagerImpl();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.print("ADD-GET");
		Person person;		
		dispatchTo = "add-person.jsp";
		session = request.getSession();
		updateResult = ((String)session.getAttribute("updateResult") != null)?(String)session.getAttribute("updateResult"):"";

		if((Person)session.getAttribute("newPerson") == null) {	
			person = new Person();
			person.setGwa(new Float(0));
			person.setEmployed(false);	
			session.setAttribute("gwa", new Float(0));
		} else {
			person = (Person)session.getAttribute("newPerson");
		}

		session.setAttribute("newPerson", person);
		session.setAttribute("updateResult", updateResult);
		dispatcher = request.getRequestDispatcher(dispatchTo);
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String dispatchTo = "add-person";
		addResult = "";
		updateResult = "";
		String lastName = request.getParameter("lastName");
		String firstName = request.getParameter("firstName");
		String midName = request.getParameter("midName");
		String suffix = request.getParameter("suffix");
		String title = request.getParameter("title");
		String birth = request.getParameter("birthDate");
		String gw = request.getParameter("gwa");
		String hired = request.getParameter("dateHired");
		boolean employed = AppUtil.readBool(request.getParameter("employed"));

		String street = request.getParameter("street");
		String barangay = request.getParameter("barangay");
		String municipality = request.getParameter("municipality");
		String zipCode = request.getParameter("zipCode");

		session = request.getSession(false);
		Person person = (Person) session.getAttribute("newPerson");
		Long id = new Long(0);
		if(request.getParameter("addPerson") != null) { // add person
			try{
				lastName = AppUtil.readLine(request.getParameter("lastName"), false, 50, "Last Name");
				firstName = AppUtil.readLine(request.getParameter("firstName"), false, 50, "First Name");
				midName = AppUtil.readLine(request.getParameter("midName"), true, 20, "Middle Name");
				suffix = AppUtil.readLine(request.getParameter("suffix"), true, 4, "Suffix");
				title = AppUtil.readLine(request.getParameter("title"), true, 6, "Title");
				Date birthDate = AppUtil.readDate(request.getParameter("birthDate"), false, "Birth Date");
				Float gwa = AppUtil.readFloat(request.getParameter("gwa"), new Float(0), new Float(100.00), "GWA");
				Date dateHired = AppUtil.readDate(request.getParameter("dateHired"), true, "Date Hired");
				employed = AppUtil.readBool(request.getParameter("employed"));

				street = AppUtil.readLine(request.getParameter("street"), true, 50, "Street");
				barangay = AppUtil.readLine(request.getParameter("barangay"), true, 50, "Barangay");
				municipality = AppUtil.readLine(request.getParameter("municipality"), false, 50, "Municipality");
				Integer zip = AppUtil.readInt(request.getParameter("zipCode"), 0, 9999, true, "Zip Code");

				id = personManager.addPerson(lastName, firstName, midName, suffix, title,
					birthDate, gwa, dateHired, employed, street, barangay, municipality, zip);
				if(id > new Long(0)) {
					person = personManager.getPerson(id);
					addResult = "Successfully added a new employee";
					dispatchTo = "manage-person";

					session.removeAttribute("newPerson");
					session.removeAttribute("updateResult");
					session.removeAttribute("lastName");
					session.removeAttribute("firstName");
					session.removeAttribute("midName");
					session.removeAttribute("suffix");
					session.removeAttribute("title");
					session.removeAttribute("birthDate");
					session.removeAttribute("gwa");
					session.removeAttribute("dateHired");
					session.removeAttribute("employed");
					session.removeAttribute("street");
					session.removeAttribute("barangay");
					session.removeAttribute("municipality");
					session.removeAttribute("zipCode");

					session.setAttribute("person", person);
				}
			} catch(Exception e) {
				updateResult = e.getMessage();
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		} 

		if( dispatchTo.equals("add-person") ) {
			session.setAttribute("updateResult", updateResult);
			session.setAttribute("addResult", addResult);

			session.setAttribute("lastName", lastName);
			session.setAttribute("firstName", firstName);
			session.setAttribute("midName", midName);
			session.setAttribute("suffix", suffix);
			session.setAttribute("title", title);
			session.setAttribute("birthDate", birth);
			session.setAttribute("gwa", gw);
			session.setAttribute("dateHired", hired);
			session.setAttribute("employed", employed);
			session.setAttribute("street", street);
			session.setAttribute("barangay", barangay);
			session.setAttribute("municipality", municipality);
			session.setAttribute("zipCode", zipCode);

			session.setAttribute("newPerson", person);
			// dispatcher = request.getRequestDispatcher(dispatchTo);
			// dispatcher.forward(request, response);
		}

		System.out.print("ADD-POST");
		response.sendRedirect(dispatchTo);
	}
}