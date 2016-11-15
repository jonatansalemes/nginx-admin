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

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class SessionCreator {

	@Inject
	private SessionFactory sessionFactory;

	public void destroy(@Disposes Session session) {
		if (session.isOpen()) {
			session.close();
		}

	}

	@Produces
	@RequestScoped
	public Session getInstance() {
		return sessionFactory.openSession();
	}

}