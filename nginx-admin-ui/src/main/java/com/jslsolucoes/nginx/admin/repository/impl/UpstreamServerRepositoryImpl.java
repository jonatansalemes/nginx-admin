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

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;

import com.jslsolucoes.nginx.admin.model.Upstream;
import com.jslsolucoes.nginx.admin.model.UpstreamServer;
import com.jslsolucoes.nginx.admin.repository.UpstreamServerRepository;

@RequestScoped
public class UpstreamServerRepositoryImpl extends RepositoryImpl<UpstreamServer> implements UpstreamServerRepository {

	public UpstreamServerRepositoryImpl() {

	}

	@Inject
	public UpstreamServerRepositoryImpl(Session session) {
		super(session);
	}

	@SuppressWarnings("unchecked")
	private List<UpstreamServer> listAll(Upstream upstream) {
		Criteria criteria = session.createCriteria(UpstreamServer.class);
		criteria.createCriteria("upstream", "upstream", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("upstream.id", upstream.getId()));
		return criteria.list();
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
