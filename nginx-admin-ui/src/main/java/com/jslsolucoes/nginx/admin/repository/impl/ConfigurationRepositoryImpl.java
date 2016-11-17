package com.jslsolucoes.nginx.admin.repository.impl;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.jslsolucoes.nginx.admin.model.Configuration;
import com.jslsolucoes.nginx.admin.repository.ConfigurationRepository;

@RequestScoped
public class ConfigurationRepositoryImpl extends RepositoryImpl<Configuration> implements ConfigurationRepository {

	public ConfigurationRepositoryImpl() {

	}

	@Inject
	public ConfigurationRepositoryImpl(EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	public String variable(String variable) {
		Query query = entityManager.createQuery("select value from Configuration where variable = :variable ");
		query.setParameter("variable", variable);
		return ((String) query.getSingleResult());
	}

}
