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
import javax.persistence.Query;

import com.jslsolucoes.nginx.admin.i18n.Messages;
import com.jslsolucoes.nginx.admin.model.Server;
import com.jslsolucoes.nginx.admin.repository.ServerRepository;

@RequestScoped
public class ServerRepositoryImpl extends RepositoryImpl<Server> implements ServerRepository {

	public ServerRepositoryImpl() {

	}

	@Inject
	public ServerRepositoryImpl(EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	public List<String> validateBeforeSaveOrUpdate(Server server) {
		List<String> errors = new ArrayList<String>();

		if (hasEquals(server) != null) {
			errors.add(Messages.getString("server.already.exists"));
		}
		
		return errors;
	}

	private Server hasEquals(Server server) {
		try {
			StringBuilder  hql = new StringBuilder("from Server where ip = :ip ");
			if(server.getId() != null){
				hql.append("and id <> :id");
			}
			Query query = entityManager.createQuery(hql.toString())
			.setParameter("ip", server.getIp());
			if(server.getId() != null){
				query.setParameter("id", server.getId());
			}
			return (Server) 
					query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	
}
