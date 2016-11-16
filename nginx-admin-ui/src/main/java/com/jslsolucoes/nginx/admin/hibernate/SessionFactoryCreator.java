/*******************************************************************************
 * Copyright (c) 2016 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package com.jslsolucoes.nginx.admin.hibernate;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class SessionFactoryCreator {

	@Inject
	private HibernateConfigurationManager hibernateConfigurationManager;

	@SuppressWarnings("rawtypes")
	@Produces
	@ApplicationScoped
	public SessionFactory getInstance() {

		Configuration configuration = new Configuration();
		configuration.configure();
		for (Class clazz : hibernateConfigurationManager.getEntities()) {
			configuration.addAnnotatedClass(clazz);
		}
		StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
		ServiceRegistry serviceRegistry = builder.applySettings(configuration.getProperties()).build();
		return configuration.buildSessionFactory(serviceRegistry);
	}

	public void destroy(@Disposes SessionFactory sessionFactory) {
		if (!sessionFactory.isClosed()) {
			sessionFactory.close();
		}
	}
}