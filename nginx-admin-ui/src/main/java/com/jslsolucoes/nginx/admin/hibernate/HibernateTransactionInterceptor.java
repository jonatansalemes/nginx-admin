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
import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.caelum.vraptor.AroundCall;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.http.MutableResponse;
import br.com.caelum.vraptor.interceptor.SimpleInterceptorStack;
import br.com.caelum.vraptor.validator.Validator;

@Intercepts
@RequestScoped
public class HibernateTransactionInterceptor {

	@Inject
	private Session session;

	@Inject
	private Validator validator;

	@Inject
	private MutableResponse response;

	@AroundCall
	public void intercept(SimpleInterceptorStack stack) {
		addRedirectListener();

		Transaction transaction = session.beginTransaction();

		try {
			stack.next();
			commit(transaction);
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
			}
		}
	}

	private void commit(Transaction transaction) {
		if (!validator.hasErrors() && transaction.isActive()) {
			transaction.commit();
		}
	}

	private void addRedirectListener() {
		response.addRedirectListener(new MutableResponse.RedirectListener() {
			@Override
			public void beforeRedirect() {
				commit(session.getTransaction());
			}
		});
	}
}