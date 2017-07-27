package com.jlava.persistence;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	private static final SessionFactory sessionFactory = buildSessionFactory();

	private static SessionFactory buildSessionFactory() {
		try {
			return new Configuration().configure("com/jlava/persistence/hibernate.cfg.xml").buildSessionFactory();

		} catch(Exception e) {
			System.out.println("An exception occured upon building hibernate session : \n" + e);
			throw new ExceptionInInitializerError(e);
		}
	}

	public static SessionFactory  getSessionFactory() {
		return sessionFactory;
	}

	public static void shutdown() {
		sessionFactory.close();
	}
}