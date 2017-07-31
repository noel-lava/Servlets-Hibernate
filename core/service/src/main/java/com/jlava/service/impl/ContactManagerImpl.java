package com.jlava.service.impl;

import com.jlava.model.Contact;
import com.jlava.model.Person;
import com.jlava.dao.ContactDao;
import com.jlava.dao.PersonDao;
import com.jlava.service.PersonManager;
import com.jlava.service.ContactManager;

public class ContactManagerImpl implements ContactManager{
	private PersonManager pManager;
	private ContactDao contactDao;
	private PersonDao personDao;

	public ContactManagerImpl(PersonManager pManager) {
		this.pManager = pManager;
		this.contactDao = new ContactDao();
		this.personDao = new PersonDao();
	}
	
	public void addContact(Long personId, Long typeId, String contactDesc) {
		Person person = personDao.getPerson(personId);
		Contact contact = new Contact(typeId, contactDesc);

		if(person != null) {
			contact.setPerson(person);
			person.getContacts().add(contact);
			pManager.updatePerson(person);
			System.out.println("[Added contact to person " + personId + "]");
		} else {
			System.out.println("[Person with id " + personId + " not found]");	
		}
	}

	public void updateContact(Long personId, Long oldContactId, String contactDesc) {
		Contact contact = contactDao.getContact(personId, oldContactId);

		if(contact != null) {
			contact.setContactDesc(contactDesc);
			int status = contactDao.updateContact(personId, contact);

			if(status > 0) {
				System.out.println("[Updated contact " + contact.getId() + " of person "+ personId + "]");
			} else {
				System.out.println("[Update Failed]");
			}
		} else {
			System.out.println("[Person with id " + personId + " or contact with id " + oldContactId + " not found]");
		}
	}

	public void deleteContact(Long personId, Long contactId) {
		Contact contact = contactDao.getContact(personId, contactId);

		if(contact != null) {
			int status = contactDao.deleteContact(personId, contact);

			if(status > 0) {
				System.out.println("[Deleted contact " + contactId + " of person " + personId + "]");
			} else {
				System.out.println("[Person or contact not found]");
			}
		} else {
			System.out.println("[Person with id " + personId + " or contact with id " + contactId + " not found]");
		}
	}

	public Contact getContact(Long personId, Long contactId) {
		Contact contact = contactDao.getContact(personId, contactId);
		if(contact == null) {
			System.out.println("[Contact with id " + contactId + " of person " + personId + " not found]");
		}
		return contact;
	}
}