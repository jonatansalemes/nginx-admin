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

import com.jslsolucoes.nginx.admin.model.VirtualHost;
import com.jslsolucoes.nginx.admin.model.VirtualHostLocation;
import com.jslsolucoes.nginx.admin.repository.VirtualHostLocationRepository;

@RequestScoped
public class VirtualHostLocationRepositoryImpl extends RepositoryImpl<VirtualHostLocation>
		implements VirtualHostLocationRepository {

	public VirtualHostLocationRepositoryImpl() {

	}

	@Inject
	public VirtualHostLocationRepositoryImpl(Session session) {
		super(session);
	}
	
	@SuppressWarnings("unchecked")
	private List<VirtualHostLocation> listAll(VirtualHost virtualHost){
		Criteria criteria = session.createCriteria(VirtualHostLocation.class);
		criteria.createCriteria("virtualHost", "virtualHost", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("virtualHost.id", virtualHost.getId()));
		return criteria.list();
	}

	@Override
	public void deleteAllFor(VirtualHost virtualHost) throws Exception {
		for(VirtualHostLocation virtualHostLocation : listAll(virtualHost)){
			super.delete(virtualHostLocation);
		}
	}

	@Override
	public void recreate(VirtualHost virtualHost, List<VirtualHostLocation> aliases) throws Exception {
		deleteAllFor(virtualHost);
		for (VirtualHostLocation virtualHostLocation : aliases) {
			virtualHostLocation.setVirtualHost(virtualHost);
			super.insert(virtualHostLocation);
		}
	}
}
