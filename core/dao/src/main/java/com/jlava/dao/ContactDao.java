package com.jlava.dao;

import com.jlava.persistence.HibernateUtil;
import com.jlava.model.Contact;

import org.hibernate.Session;
import org.hibernate.Query;
import org.hibernate.HibernateException;
import org.hibernate.Transaction;

public class ContactDao {
	
	public Contact getContact(int personId, int contactId) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Query query = session.createQuery("FROM Contact WHERE contactId = :contactId AND person.personId = :personId");
		query.setParameter("contactId", contactId);
		query.setParameter("personId", personId);

		Contact contact = (Contact)query.uniqueResult();
		session.close();

		return contact;
	}

	public int updateContact(int personId, Contact contact) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		int updated = 0;

		try {
			transaction = session.beginTransaction();
			Query query = session.createQuery("UPDATE Contact SET landline = :landline, "
				+ "mobileNo = :mobileNo, email = :email WHERE person.personId = :personId AND contactId = :contactId");
			query.setParameter("landline", contact.getLandline());
			query.setParameter("mobileNo", contact.getMobileNo());
			query.setParameter("email", contact.getEmail());
			query.setParameter("personId", personId);
			query.setParameter("contactId", contact.getContactId());
			
			updated = query.executeUpdate();
			transaction.commit();
		} catch(HibernateException he) {
			if(transaction != null) {
				transaction.rollback();
			}
			he.printStackTrace();
		}
		
		session.close();
		return updated;
	}

	public int deleteContact(int personId, int contactId) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		int deleted = 0;

		try{
			transaction = session.beginTransaction();
			Query query = session.createQuery("DELETE Contact where person.personId = :personId AND contactId = :contactId");
			query.setParameter("personId", personId);
			query.setParameter("contactId", contactId);
	
			deleted = query.executeUpdate();
			transaction.commit();
		} catch(HibernateException he) {
			if(transaction != null) {
				transaction.rollback();
			}
			he.printStackTrace();
		}

		session.close();
		return deleted;
	}
}