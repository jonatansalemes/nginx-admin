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

import com.jslsolucoes.nginx.admin.model.Upstream;
import com.jslsolucoes.nginx.admin.model.UpstreamServer;
import com.jslsolucoes.nginx.admin.repository.UpstreamServerRepository;

@RequestScoped
public class UpstreamServerRepositoryImpl extends RepositoryImpl<UpstreamServer> implements UpstreamServerRepository {

	public UpstreamServerRepositoryImpl() {

	}

	@Inject
	public UpstreamServerRepositoryImpl(EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	public void deleteAllFor(Upstream upstream) {
		entityManager
				.createQuery(
						"delete from UpstreamServer upstreamServer where upstreamServer.upstream.id = :idUpstream")
				.setParameter("idUpstream", upstream.getId()).executeUpdate();
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
