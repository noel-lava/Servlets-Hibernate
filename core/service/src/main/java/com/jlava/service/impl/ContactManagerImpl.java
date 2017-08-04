package com.jlava.service.impl;

import com.jlava.model.Contact;
import com.jlava.model.ContactType;
import com.jlava.model.Person;
import com.jlava.dao.ContactDao;
import com.jlava.dao.PersonDao;
import com.jlava.service.PersonManager;
import com.jlava.service.ContactManager;

import java.util.List;

public class ContactManagerImpl implements ContactManager{
	private PersonManager pManager;
	private ContactDao contactDao;
	private PersonDao personDao;

	public ContactManagerImpl(PersonManager pManager) {
		this.pManager = pManager;
		this.contactDao = new ContactDao();
		this.personDao = new PersonDao();
	}
	
	public void addContact(Person person, Long typeId, String contactDesc) {
		//Person person = personDao.getPerson(personId);
		Contact contact = new Contact(typeId, contactDesc);
		contact.setContactType(getContactType(typeId));

		if(person != null) {
			contact.setPerson(person);
			contact.setDeleted(false);
			person.getContacts().add(contact);
			pManager.updatePerson(person);
			System.out.println("[Added contact to person " + person.getId() + "]");
		} else {
			System.out.println("[Person with id " + person.getId() + " not found]");	
		}
	}

	public void updateContact(Person person, Long oldContactId, String contactDesc) {
		//Contact contact = contactDao.getContact(personId, oldContactId);
		//Person person = pManager.getPerson(personId);
		Contact contact = person.getContacts()
							.stream()
							.filter(c -> c.getId() == oldContactId)
							.findFirst()
							.orElse(null);

		if(contact != null) {
			contact.setContactDesc(contactDesc);
			int status = contactDao.updateContact(person.getId(), contact);

			if(status > 0) {
				System.out.println("[Updated contact " + contact.getId() + " of person "+ person.getId() + "]");
			} else {
				System.out.println("[Update Failed]");
			}
		} else {
			System.out.println("[Person with id " + person.getId() + " or contact with id " + oldContactId + " not found]");
		}
	}

	public void deleteContact(Person person, Long contactId) {
		// Contact contact = contactDao.getContact(person.getId(), contactId);
		Contact contact = person.getContacts()
							.stream()
							.filter(c -> c.getId() == contactId)
							.findFirst()
							.orElse(null);

		if(contact != null) {
			person.getContacts().remove(contact);
			int status = contactDao.deleteContact(person.getId(), contact);

			if(status > 0) {
				System.out.println("[Deleted contact " + contactId + " of person " + person.getId() + "]");
			} else {
				System.out.println("[Person or contact not found]");
			}
		} else {
			System.out.println("[Person with id " + person.getId() + " or contact with id " + contactId + " not found]");
		}
	}

	public Contact getContact(Long personId, Long contactId) {
		Contact contact = contactDao.getContact(personId, contactId);
		if(contact == null) {
			System.out.println("[Contact with id " + contactId + " of person " + personId + " not found]");
		}
		return contact;
	}

	public List<ContactType> getContactTypes() {
		return contactDao.getContactTypes();
	}

	public ContactType getContactType(Long typeId) {
		return contactDao.getContactType(typeId);
	}
}