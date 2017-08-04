package com.jlava.service;

import com.jlava.model.Contact;
import com.jlava.model.ContactType;
import com.jlava.model.Person;
import java.util.List;

public interface ContactManager {
	void addContact(Person person, Long typeId, String contactDesc);
	void updateContact(Person person, Long oldContactId, String contactDesc);
	void deleteContact(Person person, Long contactId);
	Contact getContact(Long personId, Long contactId);
	List<ContactType> getContactTypes();
	ContactType getContactType(Long typeId);
}