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
import com.jslsolucoes.nginx.admin.model.Upstream;
import com.jslsolucoes.nginx.admin.model.UpstreamServer;
import com.jslsolucoes.nginx.admin.nginx.runner.Runner;
import com.jslsolucoes.nginx.admin.repository.NginxRepository;
import com.jslsolucoes.nginx.admin.repository.ResourceIdentifierRepository;
import com.jslsolucoes.nginx.admin.repository.UpstreamRepository;
import com.jslsolucoes.nginx.admin.repository.UpstreamServerRepository;
import com.jslsolucoes.nginx.admin.template.TemplateProcessor;

@RequestScoped
public class UpstreamRepositoryImpl extends RepositoryImpl<Upstream> implements UpstreamRepository {

	private UpstreamServerRepository upstreamServerRepository;
	private NginxRepository nginxRepository;
	private ResourceIdentifierRepository resourceIdentifierRepository;
	private Runner runner;

	public UpstreamRepositoryImpl() {

	}

	@Inject
	public UpstreamRepositoryImpl(EntityManager entityManager, UpstreamServerRepository upstreamServerRepository,
			NginxRepository nginxRepository, ResourceIdentifierRepository resourceIdentifierRepository,
			Runner runner) {
		super(entityManager);
		this.upstreamServerRepository = upstreamServerRepository;
		this.nginxRepository = nginxRepository;
		this.resourceIdentifierRepository = resourceIdentifierRepository;
		this.runner = runner;
	}

	@Override
	public OperationResult saveOrUpdate(Upstream upstream, List<UpstreamServer> upstreamServers) throws Exception {

		if (upstream.getId() == null) {
			upstream.setResourceIdentifier(resourceIdentifierRepository.create());
		}
		OperationResult operationResult = super.saveOrUpdate(upstream);
		upstreamServerRepository.recreate(new Upstream(operationResult.getId()), upstreamServers);
		flushAndClear();
		configure(upstream);
		runner.reload();
		return operationResult;
	}

	private void configure(Upstream upstream) throws Exception {
		upstream = load(upstream);
		new TemplateProcessor().withTemplate("upstream.tpl").withData("upstream", upstream)
				.toLocation(new File(nginxRepository.configuration().upstream(),
						upstream.getResourceIdentifier().getHash() + ".conf"))
				.process();
	}

	@Override
	public List<String> validateBeforeSaveOrUpdate(Upstream upstream, List<UpstreamServer> upstreamServers) {
		List<String> errors = new ArrayList<String>();

		if (upstreamServers.stream().map(upstreamServer -> {
			return upstreamServer.getServer().getId() + ":" + upstreamServer.getPort();
		}).collect(Collectors.toSet()).size() != upstreamServers.size()) {
			errors.add(Messages.getString("upstream.servers.mapped.twice"));
		}

		if (hasEquals(upstream) != null) {
			errors.add(Messages.getString("upstream.already.exists"));
		}

		return errors;
	}

	@Override
	public OperationType delete(Upstream upstream) {
		upstreamServerRepository.deleteAllFor(upstream);
		try {
			upstream = load(upstream);
			String hash = upstream.getResourceIdentifier().getHash();
			FileUtils.forceDelete(new File(nginxRepository.configuration().upstream(),hash + ".conf"));
			super.delete(upstream);
			resourceIdentifierRepository.delete(hash);
			return OperationType.DELETE;
		} catch(Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	@Override
	public Upstream hasEquals(Upstream upstream) {
		try {
			StringBuilder hql = new StringBuilder("from Upstream where name = :name ");
			if (upstream.getId() != null) {
				hql.append("and id <> :id");
			}
			Query query = entityManager.createQuery(hql.toString()).setParameter("name", upstream.getName());
			if (upstream.getId() != null) {
				query.setParameter("id", upstream.getId());
			}
			return (Upstream) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public Upstream findByName(String name) {
		try {
			return (Upstream) entityManager
					.createQuery("from Upstream where name = :name ")
					.setParameter("name", name)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
}
