package com.jslsolucoes.nginx.admin.repository.impl;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import com.jslsolucoes.nginx.admin.model.Configuration_;
import com.jslsolucoes.nginx.admin.model.Nginx;
import com.jslsolucoes.nginx.admin.model.Nginx_;
import com.jslsolucoes.nginx.admin.repository.NginxRepository;

@RequestScoped
public class NginxRepositoryImpl extends RepositoryImpl<Nginx> implements NginxRepository {

	@Deprecated
	public NginxRepositoryImpl() {

	}

	@Inject
	public NginxRepositoryImpl(EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	public List<String> validateBeforeSaveOrUpdate(Nginx nginx) {
		List<String> errors = new ArrayList<>();

		return errors;
	}

	@Override
	public List<Nginx> listAllConfigured() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Nginx> criteriaQuery = criteriaBuilder.createQuery(Nginx.class);
		Root<Nginx> root = criteriaQuery.from(Nginx.class);
		criteriaQuery.where(
				criteriaBuilder.isNotNull(root.join(Nginx_.configuration,JoinType.INNER).get(Configuration_.id))
		);
		return entityManager.createQuery(criteriaQuery).getResultList();
	}

}
