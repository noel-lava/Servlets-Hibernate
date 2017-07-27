package com.jlava.service.impl;

import com.jlava.model.Contact;
import com.jlava.model.Person;
import com.jlava.dao.ContactDao;
import com.jlava.service.PersonManager;
import com.jlava.service.ContactManager;

public class ContactManagerImpl implements ContactManager{
	private PersonManager pManager;
	private ContactDao contactDao;

	public ContactManagerImpl(PersonManager pManager) {
		this.pManager = pManager;
		this.contactDao = new ContactDao();
	}
	
	public void addContact(int personId, String landline, String mobileNo, String email) {
		Person person = pManager.getPerson(personId);
		Contact contact = new Contact(landline, mobileNo, email);

		if(person != null) {
			contact.setPerson(person);
			person.getContacts().add(contact);
			pManager.updatePerson(person);
			System.out.println("[Added contact to person " + personId + "]");
		} else {
			System.out.println("[Person with id " + personId + " not found]");	
		}
	}

	public void updateContact(int personId, int oldContactId, String landline, String mobileNo, String email) {
		Contact contact = contactDao.getContact(personId, oldContactId);

		if(contact != null) {
			contact.setLandline(landline);
			contact.setMobileNo(mobileNo);
			contact.setEmail(email);
			int status = contactDao.updateContact(personId, contact);

			if(status > 0) {
				System.out.println("[Updated contact " + contact.getContactId() + " of person "+ personId + "]");
			} else {
				System.out.println("[Update Failed]");
			}
		} else {
			System.out.println("[Person with id " + personId + " or contact with id " + oldContactId + " not found]");
		}
	}

	public void deleteContact(int personId, int contactId) {
		Contact contact = contactDao.getContact(personId, contactId);

		if(contact != null) {
			int status = contactDao.deleteContact(personId, contactId);

			if(status > 0) {
				System.out.println("[Deleted contact " + contactId + " of person " + personId + "]");
			} else {
				System.out.println("[Person or contact not found]");
			}
		} else {
			System.out.println("[Person with id " + personId + " or contact with id " + contactId + " not found]");
		}
	}

	public Contact getContact(int personId, int contactId) {
		Contact contact = contactDao.getContact(personId, contactId);
		if(contact == null) {
			System.out.println("[Contact with id " + contactId + " of person " + personId + " not found]");
		}
		return contact;
	}
}