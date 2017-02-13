package com.jslsolucoes.nginx.admin.hibernate;

import java.sql.DriverManager;
import java.sql.SQLException;

import org.h2.Driver;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class Hibernate {

	private Session session;
	private Transaction transaction;
	private SessionFactory sessionFactory;
	
	private Hibernate() {
		
	}
	
	public static Hibernate build() {
		return new Hibernate();
	}
	
	public Hibernate buildSessionFactory() throws SQLException{
		DriverManager.registerDriver(new Driver());
		Configuration cfg = new Configuration().configure();
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties())
				.build();
		sessionFactory = cfg.buildSessionFactory(serviceRegistry);
		return this;
	}
	
	public Session session(){
		return session;
	}
	
	public Hibernate openSession(){
		session = sessionFactory.openSession();
		return this;
	}

	public void close() {
		if (session != null && session.isOpen()){
			session.close();
		}
		
		if (sessionFactory != null && !sessionFactory.isClosed()) {
			sessionFactory.close();
		}
	}

	public Hibernate beginTransaction() {
		transaction = session.beginTransaction();
		return this;
	}

	public Hibernate rollback() {
		transaction.rollback();
		return this;
	}
}
