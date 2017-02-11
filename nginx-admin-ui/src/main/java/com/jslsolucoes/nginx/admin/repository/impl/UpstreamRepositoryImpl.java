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

import org.apache.commons.io.FileUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.jslsolucoes.nginx.admin.i18n.Messages;
import com.jslsolucoes.nginx.admin.model.Upstream;
import com.jslsolucoes.nginx.admin.model.UpstreamServer;
import com.jslsolucoes.nginx.admin.repository.NginxRepository;
import com.jslsolucoes.nginx.admin.repository.ResourceIdentifierRepository;
import com.jslsolucoes.nginx.admin.repository.UpstreamRepository;
import com.jslsolucoes.nginx.admin.repository.UpstreamServerRepository;
import com.jslsolucoes.nginx.admin.template.TemplateProcessor;

import freemarker.template.TemplateException;

@RequestScoped
public class UpstreamRepositoryImpl extends RepositoryImpl<Upstream> implements UpstreamRepository {

	private UpstreamServerRepository upstreamServerRepository;
	private NginxRepository nginxRepository;
	private ResourceIdentifierRepository resourceIdentifierRepository;

	public UpstreamRepositoryImpl() {
		this(null, null, null, null);
	}

	@Inject
	public UpstreamRepositoryImpl(Session session, UpstreamServerRepository upstreamServerRepository,
			NginxRepository nginxRepository, ResourceIdentifierRepository resourceIdentifierRepository) {
		super(session);
		this.upstreamServerRepository = upstreamServerRepository;
		this.nginxRepository = nginxRepository;
		this.resourceIdentifierRepository = resourceIdentifierRepository;
	}

	@Override
	public OperationResult saveOrUpdate(Upstream upstream, List<UpstreamServer> upstreamServers)
			throws IOException, TemplateException {

		if (upstream.getId() == null) {
			upstream.setResourceIdentifier(resourceIdentifierRepository.create());
		}
		OperationResult operationResult = super.saveOrUpdate(upstream);
		upstreamServerRepository.recreate(new Upstream(operationResult.getId()), upstreamServers);
		flushAndClear();
		configure(upstream);
		return operationResult;
	}

	private void configure(Upstream upstream) throws IOException, TemplateException {
		Upstream upstreamToConfigure = load(upstream);
		TemplateProcessor.build().withTemplate("upstream.tpl").withData("upstream", upstreamToConfigure)
				.toLocation(new File(nginxRepository.configuration().upstream(),
						upstreamToConfigure.getResourceIdentifier().getHash() + ".conf"))
				.process();
	}

	@Override
	public List<String> validateBeforeSaveOrUpdate(Upstream upstream, List<UpstreamServer> upstreamServers) {
		List<String> errors = new ArrayList<>();

		if (upstreamServers.stream()
				.map(upstreamServer -> upstreamServer.getServer().getId() + ":" + upstreamServer.getPort())
				.collect(Collectors.toSet()).size() != upstreamServers.size()) {
			errors.add(Messages.getString("upstream.servers.mapped.twice"));
		}

		if (hasEquals(upstream) != null) {
			errors.add(Messages.getString("upstream.already.exists"));
		}

		return errors;
	}

	@Override
	public OperationType deleteWithResource(Upstream upstream) throws IOException {
		upstreamServerRepository.deleteAllFor(upstream);
		Upstream upstreamToDelete = load(upstream);
		String hash = upstreamToDelete.getResourceIdentifier().getHash();
		FileUtils.forceDelete(new File(nginxRepository.configuration().upstream(), hash + ".conf"));
		super.delete(upstreamToDelete);
		resourceIdentifierRepository.delete(hash);
		return OperationType.DELETE;
	}

	@Override
	public Upstream hasEquals(Upstream upstream) {
		Criteria criteria = session.createCriteria(Upstream.class);
		criteria.add(Restrictions.eq("name", upstream.getName()));
		if (upstream.getId() != null) {
			criteria.add(Restrictions.ne("id", upstream.getId()));
		}
		return (Upstream) criteria.uniqueResult();
	}

	@Override
	public Upstream findByName(String name) {
		Criteria criteria = session.createCriteria(Upstream.class);
		criteria.add(Restrictions.eq("name", name));
		return (Upstream) criteria.uniqueResult();
	}
}
