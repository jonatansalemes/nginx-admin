package com.jslsolucoes.nginx.admin.repository.impl;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.jslsolucoes.nginx.admin.model.Strategy;
import com.jslsolucoes.nginx.admin.model.Strategy_;
import com.jslsolucoes.nginx.admin.repository.StrategyRepository;

@RequestScoped
public class StrategyRepositoryImpl extends RepositoryImpl<Strategy> implements StrategyRepository {

	@Deprecated
	public StrategyRepositoryImpl() {
		
	}

	@Inject
	public StrategyRepositoryImpl(EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	public Strategy findByName(String name) {
		try {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Strategy> criteriaQuery = criteriaBuilder.createQuery(Strategy.class);
			Root<Strategy> root = criteriaQuery.from(Strategy.class);	
			criteriaQuery.where(
					criteriaBuilder.equal(root.get(Strategy_.name), name)
			);
			return entityManager.createQuery(criteriaQuery).getSingleResult();
		} catch (NoResultException noResultException) {
			return null;
		}
	}

}
