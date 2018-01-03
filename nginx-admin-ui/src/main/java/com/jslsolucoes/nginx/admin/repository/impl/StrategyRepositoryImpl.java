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

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.jslsolucoes.nginx.admin.model.Strategy;
import com.jslsolucoes.nginx.admin.model.Strategy_;
import com.jslsolucoes.nginx.admin.repository.StrategyRepository;

@RequestScoped
public class StrategyRepositoryImpl extends RepositoryImpl<Strategy> implements StrategyRepository {

	public StrategyRepositoryImpl() {
		// Default constructor
	}

	@Inject
	public StrategyRepositoryImpl(EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	public Strategy findByName(String name) {
		try {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Strategy> criteriaQuery = criteriaBuilder.createQuery(Strategy.class);
			Root<Strategy> root = criteriaQuery.from(Strategy.class);	
			criteriaQuery.where(
					criteriaBuilder.equal(root.get(Strategy_.name), name)
			);
			return entityManager.createQuery(criteriaQuery).getSingleResult();
		} catch (NoResultException noResultException) {
			return null;
		}
	}

}
