package com.jlava.dao;

import com.jlava.persistence.HibernateUtil;
import com.jlava.model.Person;
import com.jlava.model.Role;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;
import java.util.*;

public class RoleDao {
	public Integer addRole(Role role) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		Integer roleId = null;

		try {
			transaction = session.beginTransaction();
			roleId = (Integer)session.save(role);
			transaction.commit();
		} catch(HibernateException he) {
			if(transaction != null) {
				transaction.rollback();
			}
			he.printStackTrace();
		} finally {
			session.close();
		}
		return roleId;
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
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		int deleted = 0;

		try {
			transaction = session.beginTransaction();
			session.delete(role);
			deleted = 1;
			transaction.commit();
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

	public List<Role> getRoles() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<Role> roles = null;
		roles = session.createQuery("FROM Role ORDER BY roleId ASC").list();
		session.close();

		return roles;
	}

	public Role getRole(int roleId) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Role role = (Role)session.get(Role.class, new Integer(roleId));
		session.close();

		return role;
	}

	public List<Person> getPersonsWithRole(int roleId) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<Person> persons = session.createQuery("Select p FROM Person p JOIN p.roles a WHERE a.roleId = :roleId").setParameter("roleId", roleId).list();
		session.close();

		return persons;
	}
}