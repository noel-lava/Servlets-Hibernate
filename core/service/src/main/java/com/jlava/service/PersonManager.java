package com.jlava.service;

import com.jlava.model.Person;
import com.jlava.model.Role;
import java.util.*;

public interface PersonManager {
	Long addPerson(String lastName, String firstName, String midName, String suffix, String title,
				Date birthDate, float gwa, Date dateHired, boolean employed, String street, String barangay, String municipality, int zipCode);

	int deletePerson(Long personId);

	int updatePerson(Long personId, String lastName, String firstName, String midName, String suffix, String title,
				Date birthDate, float gwa, Date dateHired, boolean employed, String street, String barangay, String municipality, int zipCode);

	int updatePerson(Person person);

	// list person by gwa(sort in java)
	// list person by date hired
	// list person by last name
	List<Person> listPersons(int sortBy);

	void searchPerson(Long personId);

	Person getPerson(Long personId);

	// add person role
	void addPersonRole(Long personId, Long roleId);

	// delete person role
	void deletePersonRole(Long personId, Long roleId);
}