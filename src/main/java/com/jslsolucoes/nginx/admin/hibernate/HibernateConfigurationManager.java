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

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessAnnotatedType;
import javax.enterprise.inject.spi.WithAnnotations;
import javax.persistence.Entity;

@ApplicationScoped
@SuppressWarnings("rawtypes")
public class HibernateConfigurationManager implements Extension {

	private List<Class> entities = new ArrayList<Class>();

	public void processAnnotations(
			@Observes @WithAnnotations(Entity.class) ProcessAnnotatedType<?> processAnnotatedType) {
		entities.add(processAnnotatedType.getAnnotatedType().getJavaClass());
	}

	public List<Class> getEntities() {
		return entities;
	}
}