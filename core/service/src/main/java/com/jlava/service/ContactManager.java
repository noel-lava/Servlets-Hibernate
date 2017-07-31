package com.jlava.service;

import com.jlava.model.Contact;

public interface ContactManager {
	void addContact(Long personId, Long typeId, String contactDesc);
	void updateContact(Long personId, Long oldContactId, String contactDesc);
	void deleteContact(Long personId, Long contactId);
	Contact getContact(Long personId, Long contactId);
}