package com.jlava.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Filter;
import org.hibernate.stat.Statistics;

import java.util.*;

import com.jlava.persistence.HibernateUtil;
import com.jlava.model.Person;

public class PersonDao {
	private SessionFactory sessionFactory;

	public PersonDao() {
		this.sessionFactory = HibernateUtil.getSessionFactory();
	}

	public Long addPerson(Person person) {
		person.setDeleted(false);
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		Long personId = null;

		try {
			transaction = session.beginTransaction();
			personId = (Long) session.save(person);
			transaction.commit();
		} catch(HibernateException he) {
			if(transaction != null) {
				transaction.rollback();
			}
			he.printStackTrace();
		} finally {
			session.close();
		}

		return personId;
	}

	public int deletePerson(Long personId) {
		Person person = getPerson(personId);
		person.setDeleted(true);
		return updatePerson(person);
	}

	public int updatePerson(Person person) {
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = null;
		int updated = 0;

		try {
			transaction = session.beginTransaction();
			session.update(person);

			transaction.commit();
			updated = 1;
		} catch(HibernateException he) {
			if(transaction != null) {
				transaction.rollback();
			}
			he.printStackTrace();
		} finally {
			session.close();
		}

		return updated;
	}

	public List<Person> listPerson(int sortBy) {
		List<Person> persons = null;
		Session session = sessionFactory.openSession();

		switch(sortBy) {
			case 1 : // SORT BY GWA (JAVA)
				persons = session.createQuery("FROM Person WHERE deleted = false").list();
				Collections.sort(persons, (p1, p2) -> Float.compare(p1.getGwa(), p2.getGwa()));

				break;
			case 2 : // SORT BY DATE HIRED
				persons = session.createQuery("FROM Person WHERE deleted = false ORDER BY dateHired ASC").list();
				break;
			case 3 : // SORT BY LAST NAME
			default :
				persons = session.createQuery("FROM Person p WHERE p.deleted = false ORDER BY p.name.lastName ASC").list();
				break;
		}

		session.close();

		return persons;
	}

	public Person getPerson(Long personId) {
		Session session = sessionFactory.openSession();
		Filter filter = session.enableFilter("contactsFilter");
		filter.setParameter("isDeleted", false);

		Person person = (Person)session.createQuery("FROM Person WHERE id = :id AND deleted = false").setParameter("id", personId).uniqueResult();
		//session.get(Person.class, personId);

		session.close();
		return person;
	}	
}