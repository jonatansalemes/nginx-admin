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

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.jslsolucoes.nginx.admin.model.Strategy;
import com.jslsolucoes.nginx.admin.repository.StrategyRepository;

@RequestScoped
public class StrategyRepositoryImpl extends RepositoryImpl<Strategy> implements StrategyRepository {

	public StrategyRepositoryImpl() {
		//Default constructor
	}

	@Inject
	public StrategyRepositoryImpl(Session session) {
		super(session);
	}

	@Override
	public Strategy findByName(String name) {
		Criteria criteria = session.createCriteria(Strategy.class);
		criteria.add(Restrictions.eq("name", name));
		return (Strategy) criteria.uniqueResult();
	}

}
