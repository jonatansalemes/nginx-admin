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
import com.jslsolucoes.nginx.admin.model.VirtualDomain;
import com.jslsolucoes.nginx.admin.nginx.runner.Runner;
import com.jslsolucoes.nginx.admin.repository.NginxRepository;
import com.jslsolucoes.nginx.admin.repository.ResourceIdentifierRepository;
import com.jslsolucoes.nginx.admin.repository.VirtualDomainRepository;
import com.jslsolucoes.nginx.admin.util.TemplateProcessor;

@RequestScoped
public class VirtualDomainRepositoryImpl extends RepositoryImpl<VirtualDomain> implements VirtualDomainRepository {

	private ResourceIdentifierRepository resourceIdentifierRepository;
	private NginxRepository nginxRepository;
	private Runner runner;

	public VirtualDomainRepositoryImpl() {

	}

	@Inject
	public VirtualDomainRepositoryImpl(EntityManager entityManager,ResourceIdentifierRepository resourceIdentifierRepository,
			NginxRepository nginxRepository,Runner runner) {
		super(entityManager);
		this.resourceIdentifierRepository = resourceIdentifierRepository;
		this.nginxRepository = nginxRepository;
		this.runner = runner;
	}
	
	@Override
	public OperationResult saveOrUpdate(VirtualDomain virtualDomain) {
		try {
			if (virtualDomain.getId() == null) {
				virtualDomain.setResourceIdentifier(resourceIdentifierRepository.create());
			}
			if(virtualDomain.getHttps() == 0){
				virtualDomain.setSslCertificate(null);
			}
			OperationResult operationResult = super.saveOrUpdate(virtualDomain);
			flushAndClear();
			configure(virtualDomain);
			runner.reload();
			return operationResult;
		} catch(Exception exception){
			throw new RuntimeException(exception);
		}
	}
	
	private void configure(VirtualDomain virtualDomain) throws Exception {
		virtualDomain = load(virtualDomain);
		Nginx nginx = nginxRepository.configuration();
		new TemplateProcessor().withTemplate("virtual-domain.tpl").withData("virtualDomain", virtualDomain)
			.withData("nginx", nginx)
				.toLocation(new File(nginx.virtualDomain(),
						virtualDomain.getResourceIdentifier().getHash() + ".conf"))
				.process();
	}
	

	@Override
	public List<String> validateBeforeSaveOrUpdate(VirtualDomain virtualDomain) {
		List<String> errors = new ArrayList<String>();

		if (hasEquals(virtualDomain) != null) {
			errors.add(Messages.getString("virtualDomain.already.exists"));
		}
		
		return errors;
	}

	@Override
	public VirtualDomain hasEquals(VirtualDomain virtualDomain) {
		try {
			StringBuilder  hql = new StringBuilder("from VirtualDomain where domain = :domain ");
			if(virtualDomain.getId() != null){
				hql.append("and id <> :id");
			}
			Query query = entityManager.createQuery(hql.toString())
			.setParameter("domain", virtualDomain.getDomain());
			if(virtualDomain.getId() != null){
				query.setParameter("id", virtualDomain.getId());
			}
			return (VirtualDomain) 
					query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	
}
