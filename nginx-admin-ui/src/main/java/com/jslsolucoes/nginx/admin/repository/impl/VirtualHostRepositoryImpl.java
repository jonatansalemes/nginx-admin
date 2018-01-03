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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.SetJoin;

import org.apache.commons.io.FileUtils;

import com.jslsolucoes.nginx.admin.error.NginxAdminException;
import com.jslsolucoes.nginx.admin.model.Nginx;
import com.jslsolucoes.nginx.admin.model.VirtualHost;
import com.jslsolucoes.nginx.admin.model.VirtualHostAlias;
import com.jslsolucoes.nginx.admin.model.VirtualHostAlias_;
import com.jslsolucoes.nginx.admin.model.VirtualHostLocation;
import com.jslsolucoes.nginx.admin.model.VirtualHost_;
import com.jslsolucoes.nginx.admin.repository.NginxRepository;
import com.jslsolucoes.nginx.admin.repository.ResourceIdentifierRepository;
import com.jslsolucoes.nginx.admin.repository.VirtualHostAliasRepository;
import com.jslsolucoes.nginx.admin.repository.VirtualHostLocationRepository;
import com.jslsolucoes.nginx.admin.repository.VirtualHostRepository;
import com.jslsolucoes.template.TemplateProcessor;
import com.jslsolucoes.vaptor4.misc.i18n.Messages;

@RequestScoped
public class VirtualHostRepositoryImpl extends RepositoryImpl<VirtualHost> implements VirtualHostRepository {

	private ResourceIdentifierRepository resourceIdentifierRepository;
	private NginxRepository nginxRepository;
	private VirtualHostAliasRepository virtualHostAliasRepository;
	private VirtualHostLocationRepository virtualHostLocationRepository;

	public VirtualHostRepositoryImpl() {
		// Default constructor
	}

	@Inject
	public VirtualHostRepositoryImpl(EntityManager entityManager, ResourceIdentifierRepository resourceIdentifierRepository,
			NginxRepository nginxRepository, VirtualHostAliasRepository virtualHostAliasRepository,
			VirtualHostLocationRepository virtualHostLocationRepository) {
		super(entityManager);
		this.resourceIdentifierRepository = resourceIdentifierRepository;
		this.nginxRepository = nginxRepository;
		this.virtualHostAliasRepository = virtualHostAliasRepository;
		this.virtualHostLocationRepository = virtualHostLocationRepository;
	}

	@Override
	public OperationResult saveOrUpdate(VirtualHost virtualHost, List<VirtualHostAlias> aliases,
			List<VirtualHostLocation> locations) throws NginxAdminException {
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
	public OperationType deleteWithResource(VirtualHost virtualHost) throws IOException {
		virtualHostAliasRepository.deleteAllFor(virtualHost);
		virtualHostLocationRepository.deleteAllFor(virtualHost);

		VirtualHost virtualHostToDelete = load(virtualHost);
		String hash = virtualHostToDelete.getResourceIdentifier().getHash();
		FileUtils.forceDelete(new File(nginxRepository.configuration().virtualHost(), hash + ".conf"));
		super.delete(virtualHostToDelete);
		resourceIdentifierRepository.delete(hash);
		return OperationType.DELETE;
	}

	private void configure(VirtualHost virtualHost) throws NginxAdminException {
		try {
			VirtualHost virtualHostToConfigure = load(virtualHost);
			Nginx nginx = nginxRepository.configuration();
			TemplateProcessor.build().withTemplate("/template/dynamic/nginx","virtual-host.tpl").withData("virtualHost", virtualHostToConfigure)
					.withData("nginx", nginx).toLocation(new File(nginx.virtualHost(),
							virtualHostToConfigure.getResourceIdentifier().getHash() + ".conf"))
					.process();
		} catch (Exception e) {
			throw new NginxAdminException(e);
		}
	}

	@Override
	public List<String> validateBeforeSaveOrUpdate(VirtualHost virtualHost, List<VirtualHostAlias> aliases,
			List<VirtualHostLocation> locations) {
		List<String> errors = new ArrayList<>();

		if (hasEquals(virtualHost, aliases) != null) {
			errors.add(Messages.getString("virtualHost.already.exists"));
		}

		if (aliases.stream().map(VirtualHostAlias::getAlias).collect(Collectors.toSet()).size() != aliases.size()) {
			errors.add(Messages.getString("virtualHost.alias.mapped.twice"));
		}

		if (locations.stream().map(VirtualHostLocation::getPath).collect(Collectors.toSet()).size() != locations
				.size()) {
			errors.add(Messages.getString("virtualHost.location.mapped.twice"));
		}

		return errors;
	}

	@Override
	public VirtualHost hasEquals(VirtualHost virtualHost, List<VirtualHostAlias> aliases) {
		try {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<VirtualHost> criteriaQuery = criteriaBuilder.createQuery(VirtualHost.class);
			Root<VirtualHost> root = criteriaQuery.from(VirtualHost.class);
			SetJoin<VirtualHost, VirtualHostAlias> join = root.join(VirtualHost_.aliases, JoinType.INNER);
			List<Predicate> predicates = new ArrayList<>();
			predicates.add(join.get(VirtualHostAlias_.alias)
					.in(aliases.stream().map(VirtualHostAlias::getAlias).collect(Collectors.toList())));
			if (virtualHost.getId() != null) {
				predicates.add(criteriaBuilder.notEqual(root.get(VirtualHost_.id), virtualHost.getId()));
			}
			criteriaQuery.distinct(true).where(predicates.toArray(new Predicate[] {}));
			return entityManager.createQuery(criteriaQuery).getSingleResult();
		} catch (NoResultException noResultException) {
			return null;
		}
	}

	@Override
	public List<VirtualHost> search(String term) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<VirtualHost> criteriaQuery = criteriaBuilder.createQuery(VirtualHost.class);
		Root<VirtualHost> root = criteriaQuery.from(VirtualHost.class);
		SetJoin<VirtualHost, VirtualHostAlias> join = root.join(VirtualHost_.aliases, JoinType.INNER);
		criteriaQuery.where(criteriaBuilder.like(criteriaBuilder.lower(join.get(VirtualHostAlias_.alias)), term));
		return entityManager.createQuery(criteriaQuery).getResultList();
	}
}
