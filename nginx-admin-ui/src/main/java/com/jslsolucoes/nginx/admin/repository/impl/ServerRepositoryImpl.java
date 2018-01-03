/*******************************************************************************
 * Copyright 2016 JSL Solucoes LTDA - https://jslsolucoes.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.jslsolucoes.nginx.admin.repository.impl;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.jslsolucoes.nginx.admin.model.Server;
import com.jslsolucoes.nginx.admin.model.Server_;
import com.jslsolucoes.nginx.admin.repository.ServerRepository;
import com.jslsolucoes.vaptor4.misc.i18n.Messages;

@RequestScoped
public class ServerRepositoryImpl extends RepositoryImpl<Server> implements ServerRepository {

	public ServerRepositoryImpl() {
		// Default constructor
	}

	@Inject
	public ServerRepositoryImpl(EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	public List<String> validateBeforeSaveOrUpdate(Server server) {
		List<String> errors = new ArrayList<>();

		if (hasEquals(server) != null) {
			errors.add(Messages.getString("server.already.exists"));
		}

		return errors;
	}

	@Override
	public Server hasEquals(Server server) {
		try {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Server> criteriaQuery = criteriaBuilder.createQuery(Server.class);
			Root<Server> root = criteriaQuery.from(Server.class);
			List<Predicate> predicates = new ArrayList<>();
			predicates.add(criteriaBuilder.equal(root.get(Server_.ip), server.getIp()));
			if (server.getId() != null) {
				predicates.add(criteriaBuilder.notEqual(root.get(Server_.id), server.getId()));
			}
			criteriaQuery.where(predicates.toArray(new Predicate[] {}));
			return entityManager.createQuery(criteriaQuery).getSingleResult();
		} catch (NoResultException noResultException) {
			return null;
		}
	}

	@Override
	public Server findByIp(String ip) {
		try {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Server> criteriaQuery = criteriaBuilder.createQuery(Server.class);
			Root<Server> root = criteriaQuery.from(Server.class);	
			criteriaQuery.where(
					criteriaBuilder.equal(root.get(Server_.ip), ip)
			);
			return entityManager.createQuery(criteriaQuery).getSingleResult();
		} catch (NoResultException noResultException) {
			return null;
		}
	}
}
