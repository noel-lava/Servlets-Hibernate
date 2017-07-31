package com.jlava.persistence;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;

import com.jlava.model.*;

public class HibernateUtil {
	private static final SessionFactory sessionFactory = buildSessionFactory();

	private static SessionFactory buildSessionFactory() {
		try {
			// Configuration config = new Configuration().configure("com/jlava/persistence/hibernate.cfg.xml");
			//  config.addAnnotatedClass(Person.class);

			StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
                    //.applySettings(config.getProperties())
					.configure("com/jlava/persistence/hibernate.cfg.xml")
                    .build();
            Metadata metaData = new MetadataSources(standardRegistry).getMetadataBuilder().build();
            
            return metaData.getSessionFactoryBuilder().build();
            /*return config.buildSessionFactory();*/
		} catch(Exception e) {
			System.out.println("An exception occured upon building hibernate session : \n" + e);
			throw new ExceptionInInitializerError(e);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static void shutdown() {
		sessionFactory.close();
	}
}