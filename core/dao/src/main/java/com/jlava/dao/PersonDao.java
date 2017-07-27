package com.jlava.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;
import org.hibernate.Query;

import java.util.*;

import com.jlava.persistence.HibernateUtil;
import com.jlava.model.Person;

public class PersonDao {
	private SessionFactory sessionFactory;

	public PersonDao() {
		this.sessionFactory = HibernateUtil.getSessionFactory();
	}

	public Integer addPerson(Person person) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		Integer personId = null;

		try {
			transaction = session.beginTransaction();
			personId = (Integer) session.save(person);
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

	public int deletePerson(int personId) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		int deleted = 0;

		try {
			transaction = session.beginTransaction();
			Person person = (Person)session.get(Person.class, personId);

			if(person != null) {
				session.delete(person);
				deleted = 1;
				transaction.commit();
			}
		} catch(HibernateException he) {
			if(transaction != null) {
				transaction.rollback();
			}
			he.printStackTrace();
		} finally {
			session.close();
		}

		return deleted;
	}

	public void updatePerson(Person person) {
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();
			session.update(person);

			transaction.commit();
		} catch(HibernateException he) {
			if(transaction != null) {
				transaction.rollback();
			}
			he.printStackTrace();
		} finally {
			session.close();
		}
	}

	public List<Person> listPerson(int sortBy) {
		List<Person> persons = null;
		Session session = sessionFactory.openSession();

		switch(sortBy) {
			case 1 : // SORT BY GWA (JAVA)
				persons = session.createQuery("FROM Person").list();
				Collections.sort(persons, (p1, p2) -> Float.compare(p1.getGwa(), p2.getGwa()));

				break;
			case 2 : // SORT BY DATE HIRED
				persons = session.createQuery("FROM Person ORDER BY dateHired ASC").list();
				break;
			case 3 : // SORT BY LAST NAME
			default :
				persons = session.createQuery("FROM Person ORDER BY lastName ASC").list();
				break;
		}

		session.close();

		return persons;
	}

	public Person getPerson(int personId) {
		Session session = sessionFactory.openSession();
		Person person = null;

		person =  (Person)session.get(Person.class, new Integer(personId));

		session.close();

		return person;
	}
}