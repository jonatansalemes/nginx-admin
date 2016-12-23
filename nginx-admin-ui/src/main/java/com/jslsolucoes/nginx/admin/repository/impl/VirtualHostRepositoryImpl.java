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
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.commons.io.FileUtils;

import com.jslsolucoes.nginx.admin.i18n.Messages;
import com.jslsolucoes.nginx.admin.model.Nginx;
import com.jslsolucoes.nginx.admin.model.VirtualHost;
import com.jslsolucoes.nginx.admin.model.VirtualHostAlias;
import com.jslsolucoes.nginx.admin.model.VirtualHostLocation;
import com.jslsolucoes.nginx.admin.repository.NginxRepository;
import com.jslsolucoes.nginx.admin.repository.ResourceIdentifierRepository;
import com.jslsolucoes.nginx.admin.repository.VirtualHostAliasRepository;
import com.jslsolucoes.nginx.admin.repository.VirtualHostLocationRepository;
import com.jslsolucoes.nginx.admin.repository.VirtualHostRepository;
import com.jslsolucoes.nginx.admin.template.TemplateProcessor;

@RequestScoped
public class VirtualHostRepositoryImpl extends RepositoryImpl<VirtualHost> implements VirtualHostRepository {

	private ResourceIdentifierRepository resourceIdentifierRepository;
	private NginxRepository nginxRepository;
	private VirtualHostAliasRepository virtualHostAliasRepository;
	private VirtualHostLocationRepository virtualHostLocationRepository;
	

	public VirtualHostRepositoryImpl() {

	}

	@Inject
	public VirtualHostRepositoryImpl(EntityManager entityManager,
			ResourceIdentifierRepository resourceIdentifierRepository, NginxRepository nginxRepository,
			VirtualHostAliasRepository virtualHostAliasRepository,
			VirtualHostLocationRepository virtualHostLocationRepository) {
		super(entityManager);
		this.resourceIdentifierRepository = resourceIdentifierRepository;
		this.nginxRepository = nginxRepository;
		this.virtualHostAliasRepository = virtualHostAliasRepository;
		this.virtualHostLocationRepository = virtualHostLocationRepository;
	}

	@Override
	public OperationResult saveOrUpdate(VirtualHost virtualHost,List<VirtualHostAlias> aliases,List<VirtualHostLocation> locations) throws Exception {
		if (virtualHost.getId() == null) {
			virtualHost.setResourceIdentifier(resourceIdentifierRepository.create());
		}
		if (virtualHost.getHttps() == 0) {
			virtualHost.setSslCertificate(null);
		}
		OperationResult operationResult = super.saveOrUpdate(virtualHost);
		virtualHostAliasRepository.recreate(new VirtualHost(operationResult.getId()), aliases);
		virtualHostLocationRepository.recreate(new VirtualHost(operationResult.getId()), locations);
		flushAndClear();
		configure(virtualHost);
		return operationResult;
	}
	
	@Override
	public OperationType delete(VirtualHost virtualHost) throws Exception {
		virtualHostAliasRepository.deleteAllFor(virtualHost);
		virtualHostLocationRepository.deleteAllFor(virtualHost);
		virtualHost = load(virtualHost);
		String hash = virtualHost.getResourceIdentifier().getHash();
		FileUtils.forceDelete(new File(nginxRepository.configuration().virtualHost(),hash + ".conf"));
		super.delete(virtualHost);
		resourceIdentifierRepository.delete(hash);
		return OperationType.DELETE;
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
	public List<String> validateBeforeSaveOrUpdate(VirtualHost virtualHost,List<VirtualHostAlias> aliases,List<VirtualHostLocation> locations) {
		List<String> errors = new ArrayList<String>();

		if (hasEquals(virtualHost,aliases) != null) {
			errors.add(Messages.getString("virtualHost.already.exists"));
		}
		
		if (aliases
				.stream()
				.map(VirtualHostAlias::getAlias)
				.collect(Collectors.toSet()).size() != aliases.size()) {
			errors.add(Messages.getString("virtualHost.alias.mapped.twice"));
		}
		
		if (locations
				.stream()
				.map(VirtualHostLocation::getPath)
				.collect(Collectors.toSet()).size() != locations.size()) {
			errors.add(Messages.getString("virtualHost.location.mapped.twice"));
		}

		return errors;
	}

	@Override
	public VirtualHost hasEquals(VirtualHost virtualHost,List<VirtualHostAlias> aliases) {
		try {
			StringBuilder hql = new StringBuilder("select distinct virtualHost from VirtualHost virtualHost inner join virtualHost.aliases aliases where aliases.alias in :aliases ");
			if (virtualHost.getId() != null) {
				hql.append("and virtualHost.id <> :id");
			}
			Query query = entityManager.createQuery(hql.toString())
					.setParameter("aliases", aliases
							.stream()
							.map(VirtualHostAlias::getAlias)
							.collect(Collectors.toList()));
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
		return entityManager.createQuery("select distinct virtualHost from VirtualHost virtualHost inner join virtualHost.aliases aliases where lower(aliases.alias) like lower(:term)")
				.setParameter("term","%" + term + "%")
				.getResultList();
	}

}
