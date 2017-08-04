package com.jlava.service;

import com.jlava.model.Person;
import com.jlava.model.Role;
import java.util.*;

public interface PersonManager {
	Long addPerson(String lastName, String firstName, String midName, String suffix, String title,
				Date birthDate, Float gwa, Date dateHired, boolean employed, String street, String barangay, String municipality, Integer zipCode);

	int deletePerson(Long personId);

	int updatePerson(Long personId, String lastName, String firstName, String midName, String suffix, String title,
				Date birthDate, Float gwa, Date dateHired, boolean employed, String street, String barangay, String municipality, Integer zipCode);

	int updatePerson(Person person);

	// list person by gwa(sort in java)
	// list person by date hired
	// list person by last name
	List<Person> listPersons(int sortBy);

	void searchPerson(Long personId);

	Person getPerson(Long personId);

	// add person role
	int addPersonRole(Person person, Long roleId);

	// delete person role
	int deletePersonRole(Person person, Long roleId);
}