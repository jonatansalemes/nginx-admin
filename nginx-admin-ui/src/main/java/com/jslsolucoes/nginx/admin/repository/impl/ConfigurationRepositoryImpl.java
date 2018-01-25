package com.jslsolucoes.nginx.admin.repository.impl;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import com.jslsolucoes.i18n.Messages;
import com.jslsolucoes.nginx.admin.model.Configuration;
import com.jslsolucoes.nginx.admin.model.Configuration_;
import com.jslsolucoes.nginx.admin.model.Nginx;
import com.jslsolucoes.nginx.admin.model.Nginx_;
import com.jslsolucoes.nginx.admin.repository.ConfigurationRepository;

@RequestScoped
public class ConfigurationRepositoryImpl extends RepositoryImpl<Configuration> implements ConfigurationRepository {

	@Deprecated
	public ConfigurationRepositoryImpl() {
		
	}

	@Inject
	public ConfigurationRepositoryImpl(EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	public Configuration loadFor(Nginx nginx) {
		try {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Configuration> criteriaQuery = criteriaBuilder.createQuery(Configuration.class);
			Root<Configuration> root = criteriaQuery.from(Configuration.class);	
			criteriaQuery.where(
					criteriaBuilder.equal(root.join(Configuration_.nginx,JoinType.INNER).get(Nginx_.id), nginx.getId())
			);
			return entityManager.createQuery(criteriaQuery).getSingleResult();
		} catch (NoResultException noResultException) {
			return null;
		}
	}

	@Override
	public List<String> validateBeforeSaveOrUpdate(Configuration configuration) {
		List<String> errors = new ArrayList<>();

		//check permission and binary

		return errors;
	}

}
