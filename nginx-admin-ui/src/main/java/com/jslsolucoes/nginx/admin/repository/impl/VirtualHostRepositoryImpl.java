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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.jslsolucoes.nginx.admin.i18n.Messages;
import com.jslsolucoes.nginx.admin.model.Nginx;
import com.jslsolucoes.nginx.admin.model.VirtualHost;
import com.jslsolucoes.nginx.admin.repository.NginxRepository;
import com.jslsolucoes.nginx.admin.repository.ResourceIdentifierRepository;
import com.jslsolucoes.nginx.admin.repository.VirtualHostRepository;
import com.jslsolucoes.nginx.admin.template.TemplateProcessor;

@RequestScoped
public class VirtualHostRepositoryImpl extends RepositoryImpl<VirtualHost> implements VirtualHostRepository {

	private ResourceIdentifierRepository resourceIdentifierRepository;
	private NginxRepository nginxRepository;
	

	public VirtualHostRepositoryImpl() {

	}

	@Inject
	public VirtualHostRepositoryImpl(EntityManager entityManager,
			ResourceIdentifierRepository resourceIdentifierRepository, NginxRepository nginxRepository) {
		super(entityManager);
		this.resourceIdentifierRepository = resourceIdentifierRepository;
		this.nginxRepository = nginxRepository;
	}

	@Override
	public OperationResult saveOrUpdate(VirtualHost virtualHost) {
		try {
			if (virtualHost.getId() == null) {
				virtualHost.setResourceIdentifier(resourceIdentifierRepository.create());
			}
			if (virtualHost.getHttps() == 0) {
				virtualHost.setSslCertificate(null);
			}
			OperationResult operationResult = super.saveOrUpdate(virtualHost);
			flushAndClear();
			configure(virtualHost);
			return operationResult;
		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	private void configure(VirtualHost virtualHost) throws Exception {
		virtualHost = load(virtualHost);
		Nginx nginx = nginxRepository.configuration();
		new TemplateProcessor().withTemplate("virtual-host.tpl").withData("virtualHost", virtualHost)
				.withData("nginx", nginx)
				.toLocation(new File(nginx.virtualHost(), virtualHost.getResourceIdentifier().getHash() + ".conf"))
				.process();
	}

	@Override
	public List<String> validateBeforeSaveOrUpdate(VirtualHost virtualHost) {
		List<String> errors = new ArrayList<String>();

		if (hasEquals(virtualHost) != null) {
			errors.add(Messages.getString("virtualHost.already.exists"));
		}

		return errors;
	}

	@Override
	public VirtualHost hasEquals(VirtualHost virtualHost) {
		try {
			StringBuilder hql = new StringBuilder("from VirtualHost where domain = :domain ");
			if (virtualHost.getId() != null) {
				hql.append("and id <> :id");
			}
			Query query = entityManager.createQuery(hql.toString()).setParameter("domain", virtualHost.getDomain());
			if (virtualHost.getId() != null) {
				query.setParameter("id", virtualHost.getId());
			}
			return (VirtualHost) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VirtualHost> search(String term) {
		return entityManager.createQuery("from VirtualHost where lower(domain) like lower(:term)")
				.setParameter("term","%" + term + "%")
				.getResultList();
	}

}
