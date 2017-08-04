package com.jlava.webapp.controller;

import java.io.IOException;
import java.util.List;
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
import com.jlava.service.RoleManager;
import com.jlava.service.impl.RoleManagerImpl;
import com.jlava.service.ContactManager;
import com.jlava.service.impl.ContactManagerImpl;
import com.jlava.model.Person;
import com.jlava.model.Role;
import com.jlava.model.Contact;
import com.jlava.model.ContactType;
import com.jlava.apputil.AppUtil;

@WebServlet("/manage-person")
public class ManagePersonServlet extends HttpServlet{
	PersonManager personManager;
	RoleManager roleManager;
	ContactManager contactManager;
	HttpSession session;
	RequestDispatcher dispatcher;

	String dispatchTo;
	String updateResult;
	String roleResult;
	String contactResult;
	String deleteResult;
	String goodUpdate;
	List<Role> roles;
	List<ContactType> contactTypes;

	public void init() throws ServletException {
		this.personManager = new PersonManagerImpl();
		this.roleManager = new RoleManagerImpl(personManager);
		this.contactManager = new ContactManagerImpl(personManager);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		dispatchTo = "manage-person.jsp";
		roleResult = "";
		updateResult = "";
		contactResult = "";
		goodUpdate = "";
		roles = roleManager.listRoles();
		contactTypes = contactManager.getContactTypes();

		session = request.getSession(false);
		Person person = (Person)session.getAttribute("person");

		if(person == null) {
			request.setAttribute("searchResult", "No person searched");
			dispatchTo = "home";
		}

		request.setAttribute("roles", roles);
		request.setAttribute("roleResult", roleResult);
		request.setAttribute("updateResult", updateResult);
		request.setAttribute("deleteResult", deleteResult);
		request.setAttribute("contactResult", contactResult);
		request.setAttribute("goodUpdate", goodUpdate);
		request.setAttribute("contactTypes", contactTypes);
		dispatcher = request.getRequestDispatcher(dispatchTo);
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String dispatchTo = "manage-person.jsp";
		roleResult = "";
		updateResult = "";
		contactResult = "";
		goodUpdate = "";
		roles = roleManager.listRoles();
		contactTypes = contactManager.getContactTypes();

		session = request.getSession(false);
		if(session.getAttribute("person") != null) {
			Person person = (Person)session.getAttribute("person");
			int result;

			if(request.getParameter("updatePerson") != null) { // update person
				try{
					String lastName = AppUtil.readLine(request.getParameter("lastName"), false, 50, "Last Name");
					String firstName = AppUtil.readLine(request.getParameter("firstName"), false, 50, "First Name");
					String midName = AppUtil.readLine(request.getParameter("midName"), true, 20, "Middle Name");
					String suffix = AppUtil.readLine(request.getParameter("suffix"), true, 4, "Suffix");
					String title = AppUtil.readLine(request.getParameter("title"), true, 6, "Title");
					Date birthDate = AppUtil.readDate(request.getParameter("birthDate"), false, "Birth Date");
					Float gwa = AppUtil.readFloat(request.getParameter("gwa"), new Float(0), new Float(100.00), "GWA");
					Date dateHired = AppUtil.readDate(request.getParameter("dateHired"), true, "Date Hired");
					boolean employed = AppUtil.readBool(request.getParameter("employed"));

					String street = AppUtil.readLine(request.getParameter("street"), true, 50, "Street");
					String barangay = AppUtil.readLine(request.getParameter("barangay"), true, 50, "Barangay");
					String municipality = AppUtil.readLine(request.getParameter("municipality"), false, 50, "Municipality");
					Integer zipCode = AppUtil.readInt(request.getParameter("zipCode"), 0, 9999, true, "Zip Code");

					person.getName().setLastName(lastName);
					person.getName().setFirstName(firstName);
					person.getName().setMidName(midName);
					person.getName().setSuffix(suffix);
					person.getName().setTitle(title);
					person.setBirthDate(birthDate);
					person.setGwa(gwa);
					person.setDateHired(dateHired);
					person.setEmployed(employed);

					person.getAddress().setStreet(street);
					person.getAddress().setBarangay(barangay);
					person.getAddress().setMunicipality(municipality);
					person.getAddress().setZipCode(zipCode);

					result = personManager.updatePerson(person);
					if(result == 1) {
						goodUpdate = "Update Successful";
					}else {
						updateResult = "Update Unsuccessful";
					}
				} catch(Exception e) {
					updateResult = e.getMessage();
				}
			} else if(request.getParameter("deletePerson") != null) { // delete person
				result = personManager.deletePerson(person.getId());
				if(result > 0) {
					dispatchTo = "home";
					deleteResult = "Successfully deleted person with id = " + person.getId();
				} else {
					deleteResult = "Error in deleting this record";
				}
			} else if(request.getParameter("addRole") != null) { // add role
				Long roleId = Long.valueOf(request.getParameter("selectRole"));
				result = personManager.addPersonRole(person, roleId);
				if(result == 0) {
					roleResult = "Role already listed";
				}
			} else if(request.getParameter("deleteRole") != null) { // delete role
				Long roleId = Long.valueOf(request.getParameter("deleteRole"));
				result = personManager.deletePersonRole(person, roleId);
				if(result == 0) {
					roleResult = "Role does not exist";
				}
			} else if(request.getParameter("updateContact") != null) { // update contact
				Long contactId = Long.valueOf(request.getParameter("updateContact"));
				String contactDesc = request.getParameter("contactDesc" + contactId);

				contactManager.updateContact(person, contactId, contactDesc);

			} else if(request.getParameter("addContact") != null) { // add contact
				Long typeId = Long.valueOf(request.getParameter("selectContactType"));
				String contactInfo = request.getParameter("contactInfo");

				contactManager.addContact(person, typeId, contactInfo);
			} else if(request.getParameter("deleteContact") != null) { // delete contact
				Long contactId = Long.valueOf(request.getParameter("deleteContact"));
				contactManager.deleteContact(person, contactId);
			}

			request.setAttribute("roles", roles);
			request.setAttribute("roleResult", roleResult);
			request.setAttribute("updateResult", updateResult);
			request.setAttribute("deleteResult", deleteResult);
			request.setAttribute("contactResult", contactResult);
			request.setAttribute("goodUpdate", goodUpdate);
			request.setAttribute("contactTypes", contactTypes);
			session.setAttribute("person", person);

			dispatcher = request.getRequestDispatcher(dispatchTo);
			dispatcher.forward(request, response);
		} else {
			response.sendRedirect("home");
		}
	}
}