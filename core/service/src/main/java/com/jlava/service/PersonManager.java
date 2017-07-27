package com.jlava.service;

import com.jlava.model.Person;
import com.jlava.model.Role;
import java.util.*;

public interface PersonManager {
	int addPerson(String lastName, String firstName, String midName, String suffix, String title,
				Date birthDate, float gwa, Date dateHired, boolean employed, String street, String barangay, String municipality, int zipCode);

	void deletePerson(int personId);

	void updatePerson(int personId, String lastName, String firstName, String midName, String suffix, String title,
				Date birthDate, float gwa, Date dateHired, boolean employed, String street, String barangay, String municipality, int zipCode);

	void updatePerson(Person person);

	// list person by gwa(sort in java)
	// list person by date hired
	// list person by last name
	void listPersons(int sortBy);

	void searchPerson(int personId);

	Person getPerson(int personId);

	// add person role
	void addPersonRole(int personId, int roleId);

	// delete person role
	void deletePersonRole(int personId, int roleId);
}