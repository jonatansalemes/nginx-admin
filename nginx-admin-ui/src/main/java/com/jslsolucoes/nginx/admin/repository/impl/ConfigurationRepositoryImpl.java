package com.jslsolucoes.nginx.admin.repository.impl;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.jslsolucoes.nginx.admin.model.Configuration;
import com.jslsolucoes.nginx.admin.model.Configuration_;
import com.jslsolucoes.nginx.admin.repository.ConfigurationRepository;

@RequestScoped
public class ConfigurationRepositoryImpl extends RepositoryImpl<Configuration> implements ConfigurationRepository {

	public ConfigurationRepositoryImpl() {
		// Default constructor
	}

	@Inject
	public ConfigurationRepositoryImpl(EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	public Integer integer(ConfigurationType configurationType) {
		return Integer.valueOf(string(configurationType));
	}

	private Configuration load(ConfigurationType configurationType) {
		try {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Configuration> criteriaQuery = criteriaBuilder.createQuery(Configuration.class);
			Root<Configuration> root = criteriaQuery.from(Configuration.class);	
			criteriaQuery.where(
					criteriaBuilder.equal(root.get(Configuration_.variable), configurationType.getVariable())
			);
			return entityManager.createQuery(criteriaQuery).getSingleResult();
		} catch (NoResultException noResultException) {
			return null;
		}
	}

	@Override
	public String string(ConfigurationType configurationType) {
		Configuration configuration = load(configurationType);
		return configuration.getValue();
	}

	@Override
	public void update(ConfigurationType configurationType, String value) {
		Configuration configuration = load(configurationType);
		configuration.setValue(value);
	}
}
