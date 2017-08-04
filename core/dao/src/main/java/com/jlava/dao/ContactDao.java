package com.jlava.dao;

import com.jlava.persistence.HibernateUtil;
import com.jlava.model.Contact;
import com.jlava.model.ContactType;

import org.hibernate.Session;
import org.hibernate.Query;
import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import java.util.List;

public class ContactDao {
	
	public Contact getContact(Long personId, Long contactId) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Query query = session.createQuery("SELECT c FROM Contact c WHERE id = :contactId AND person.id = :personId AND deleted=false");
		query.setParameter("contactId", contactId);
		query.setParameter("personId", personId);

		Contact contact = (Contact)query.uniqueResult();
		session.close();

		return contact;
	}

	public int updateContact(Long personId, Contact contact) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		int updated = 0;

		try {
			transaction = session.beginTransaction();
			Query query = session.createQuery("UPDATE Contact SET contactDesc = :contactDesc, deleted = :deleted "
				+ "WHERE person.id = :personId AND id = :contactId");
			query.setParameter("contactDesc", contact.getContactDesc());
			query.setParameter("deleted", contact.isDeleted());
			query.setParameter("personId", personId);
			query.setParameter("contactId", contact.getId());
			
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

	public int deleteContact(Long personId, Contact contact) {
		contact.setDeleted(true);
		return updateContact(personId, contact);
	}

	public List<ContactType> getContactTypes() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		return session.createQuery("FROM ContactType").list();
	}

	public ContactType getContactType(Long typeId) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		return (ContactType)session.createQuery("FROM ContactType WHERE id = :id")
					.setParameter("id", typeId)
					.uniqueResult();
	}
}