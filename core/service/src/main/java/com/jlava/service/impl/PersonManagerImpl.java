package com.jlava.service.impl;

import com.jlava.model.Person;
import com.jlava.model.Role;
import com.jlava.model.Address;
import com.jlava.dao.PersonDao;
import com.jlava.dao.RoleDao;
import com.jlava.service.PersonManager;

import org.hibernate.ObjectNotFoundException;
import java.util.*;

public class PersonManagerImpl implements PersonManager{
	private PersonDao personDao;
	private RoleDao roleDao;

	public PersonManagerImpl() {
		this.personDao = new PersonDao();
		this.roleDao = new RoleDao();
	}

	public int addPerson(String lastName, String firstName, String midName, String suffix, String title,
				Date birthDate, float gwa, Date dateHired, boolean employed, String street, String barangay, String municipality, int zipCode) {
		Address address = new Address(street, barangay, municipality, zipCode);
		Person person = new Person(lastName, firstName, midName, suffix, title, birthDate, gwa, dateHired, employed, address);

		Integer personId = personDao.addPerson(person);
		if(personId != null) {
			System.out.println("[Successfully added " + person.getLastName() + ", " + person.getFirstName() + " with ID : " + personId + "]");
			return personId;
		} else {
			System.out.println("[Add person failed]");
			return 0;
		}
	}

	public void deletePerson(int personId) {
		int ctr = personDao.deletePerson(personId);
		if(ctr > 0) {
			System.out.println("[Successfully deleted person " + personId + "]");
		} else {
			System.out.println("[Failed : Person with id " + personId + " not found]");
		}
	}

	public void updatePerson(int personId, String lastName, String firstName, String midName, String suffix, String title,
				Date birthDate, float gwa, Date dateHired, boolean employed, String street, String barangay, String municipality, int zipCode) {
		Person person = getPerson(personId);

		if(person != null) {
			person.setLastName(lastName);
			person.setFirstName(firstName);
			person.setMidName(midName);
			person.setSuffix(suffix);
			person.setTitle(title);
			person.setBirthDate(birthDate);
			person.setGwa(gwa);
			person.setDateHired(dateHired);
			person.setEmployed(employed);

			person.getAddress().setStreet(street);
			person.getAddress().setBarangay(barangay);
			person.getAddress().setMunicipality(municipality);
			person.getAddress().setZipCode(zipCode);

			personDao.updatePerson(person);
			System.out.println("[Successfully updated person " + personId + "]");
		}
	}

	public void updatePerson(Person person) {
		if(person != null) {
			personDao.updatePerson(person);
			System.out.println("[Successfully updated person " + person.getPersonId() + "]");
		}
	}

	public void listPersons(int sortBy) {
		List<Person> persons = personDao.listPerson(sortBy);

		if(persons == null || persons.size() < 1) {
			System.out.println("No persons found!");
		} else {
			persons.forEach(person -> printPerson(person));
		}
	}

	public void searchPerson(int personId) {
		Person person = getPerson(personId);
		
		if(person != null) {	
			printPerson(person);
		}
	}

	public void printPerson(Person person) {
		Address address = person.getAddress();
		String suffix = (person.getSuffix() != null)?person.getSuffix():"";
		String title = (person.getTitle() != null)?person.getTitle() + " ":"";
		String midName = (person.getMidName() != null)?person.getMidName():"";
		String addr = (address != null)?address.toString():"N/A";

		System.out.println("\n[PERSON ID : " + person.getPersonId() + "]");
		System.out.println("\tName : " + title + person.getFirstName() + " " + midName + " " + person.getLastName() + " " + suffix);
		System.out.println("\tBirth Date : " + person.getBirthDate());
		System.out.println("\tGeneral Weighted Average (GWA) : " + person.getGwa());
		System.out.println("\tDate Hired : " + person.getDateHired());
		System.out.println("\tEmployed : " + person.isEmployed());
		System.out.println("\tAddress : " + addr);

		System.out.println("\n\t[ Contact Info ]");
		if(person.getContacts().size() > 0) {
			person.getContacts().forEach(contact -> {
				System.out.println("\n\t\tContact ID : " + contact.getContactId());
				System.out.println("\t\tLandline : " + contact.getLandline());
				System.out.println("\t\tMobile : " + contact.getMobileNo());
				System.out.println("\t\te-mail : " + contact.getEmail());
			});
		} else {
			System.out.println("\tNo contacts.");
		}		

		System.out.println("\n\t[ Role(s) ]");
		if(person.getRoles().size() > 0) {
			person.getRoles().forEach(role -> {
				System.out.println("\t\tRole : " + role.getRoleDesc() + " - " + role.getRoleId());
			});
		} else {
			System.out.println("\tNo roles.");
		}
	}

	public Person getPerson(int personId) {
		Person person = personDao.getPerson(personId);

		if(person != null) {
			return person;
		} else {
			System.out.println("[Person with id " + personId + " not found]");
			return null;
		}
	}

	public void addPersonRole(int personId, int roleId) {
		Person person = getPerson(personId);

		if(person != null) {
			Role role = person.getRoles()
							.stream()
							.filter(r -> r.getRoleId() == roleId)
							.findFirst()
							.orElse(null);
			role = (role != null)?role:getRole(roleId);

			if(role != null) {
				if(person.getRoles().contains(role)) {
					System.out.println("[Failed to add role : person " + personId + " already has a " + role.getRoleDesc() + " role]");
				} else {
					person.getRoles().add(role);
					updatePerson(person);
					System.out.println("[Successfully added " + role.getRoleDesc() + " role to person " + personId + "]");
				}
			}
		}
	}

	public void deletePersonRole(int personId, int roleId) {
		Person person = getPerson(personId);

		if(person != null) {
			Role role = person.getRoles()
							.stream()
							.filter(r -> r.getRoleId() == roleId)
							.findFirst()
							.orElse(null);
			
			if(role != null) {
				person.getRoles().remove(role);
				updatePerson(person);
				System.out.println("[Successfully deleted " + role.getRoleDesc() + " role from person " + personId + "]");
			} else {
				System.out.println("[Role with id : " + roleId + " does not exist]");
			}
		}
	}

	public Role getRole(int roleId) {
		Role role = roleDao.getRole(roleId);
		if(role == null) {
			System.out.println("[Role with id " + roleId + " not found]");
		}

		return role;
	}
}