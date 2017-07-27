package com.jlava.service;

import com.jlava.model.Contact;

public interface ContactManager {
	void addContact(int personId, String landline, String mobileNo, String email);
	void updateContact(int personId, int oldContactId, String landline, String mobileNo, String email);
	void deleteContact(int personId, int contactId);
	Contact getContact(int personId, int contactId);
}