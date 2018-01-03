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

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import com.jslsolucoes.nginx.admin.model.Upstream;
import com.jslsolucoes.nginx.admin.model.UpstreamServer;
import com.jslsolucoes.nginx.admin.model.UpstreamServer_;
import com.jslsolucoes.nginx.admin.model.Upstream_;
import com.jslsolucoes.nginx.admin.repository.UpstreamServerRepository;

@RequestScoped
public class UpstreamServerRepositoryImpl extends RepositoryImpl<UpstreamServer> implements UpstreamServerRepository {

	public UpstreamServerRepositoryImpl() {
		// Default constructor
	}

	@Inject
	public UpstreamServerRepositoryImpl(EntityManager entityManager) {
		super(entityManager);
	}

	private List<UpstreamServer> listAll(Upstream upstream) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<UpstreamServer> criteriaQuery = criteriaBuilder.createQuery(UpstreamServer.class);
		Root<UpstreamServer> root = criteriaQuery.from(UpstreamServer.class);
		Join<UpstreamServer, Upstream> join = root.join(UpstreamServer_.upstream, JoinType.INNER);
		criteriaQuery.where(criteriaBuilder.equal(join.get(Upstream_.id), upstream.getId()));
		return entityManager.createQuery(criteriaQuery).getResultList();
	}

	@Override
	public void deleteAllFor(Upstream upstream) {
		for (UpstreamServer upstreamServer : listAll(upstream)) {
			super.delete(upstreamServer);
		}
		flush();
	}

	@Override
	public void recreate(Upstream upstream, List<UpstreamServer> upstreamServers) {
		deleteAllFor(upstream);
		for (UpstreamServer upstreamServer : upstreamServers) {
			upstreamServer.setUpstream(upstream);
			super.insert(upstreamServer);
		}
	}

}
