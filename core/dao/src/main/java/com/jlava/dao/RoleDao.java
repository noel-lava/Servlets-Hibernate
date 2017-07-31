package com.jlava.dao;

import com.jlava.persistence.HibernateUtil;
import com.jlava.model.Person;
import com.jlava.model.Role;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;
import java.util.*;

public class RoleDao {
	public Long addRole(Role role) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		Long id = null;

		try {
			transaction = session.beginTransaction();
			id = (Long)session.save(role);
			transaction.commit();
		} catch(HibernateException he) {
			if(transaction != null) {
				transaction.rollback();
			}
			he.printStackTrace();
		} finally {
			session.close();
		}
		return id;
	}

	public int updateRole(Role role) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		int updated = 0;

		try {
			transaction = session.beginTransaction();
			session.update(role);
			updated = 1;

			transaction.commit();
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

	public int deleteRole(Role role) {
		role.setDeleted(true);
		int deleted = updateRole(role);

		return deleted;
	}

	public List<Role> getRoles() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<Role> roles = null;
		roles = session.createQuery("FROM Role WHERE deleted=false ORDER BY id ASC").list();
		session.close();

		return roles;
	}

	public Role getRole(Long id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Role role = (Role)session.createQuery("FROM Role WHERE deleted=false AND id = :id")
						.setParameter("id", id).uniqueResult();
		session.close();

		return role;
	}

	public List<Person> getPersonsWithRole(Long id) {
		/*Session session = HibernateUtil.getSessionFactory().openSession();
		List<Person> persons = session.createQuery("Select p FROM Person p JOIN p.roles a WHERE a.roleId = :roleId").setParameter("roleId", roleId).list();
		session.close();*/
		return getRole(id).getPersons();

		//return persons;
	}
}