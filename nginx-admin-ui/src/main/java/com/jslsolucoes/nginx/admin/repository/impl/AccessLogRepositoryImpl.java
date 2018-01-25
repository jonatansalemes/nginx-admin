package com.jslsolucoes.nginx.admin.repository.impl;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.jslsolucoes.nginx.admin.model.AccessLog;
import com.jslsolucoes.nginx.admin.model.AccessLog_;
import com.jslsolucoes.nginx.admin.model.Nginx;
import com.jslsolucoes.nginx.admin.model.Nginx_;
import com.jslsolucoes.nginx.admin.repository.AccessLogRepository;

@RequestScoped
public class AccessLogRepositoryImpl extends RepositoryImpl<AccessLog> implements AccessLogRepository {

	public AccessLogRepositoryImpl() {
		// Default constructor
	}

	@Inject
	public AccessLogRepositoryImpl(EntityManager entityManager) {
		super(entityManager);
	}


	@Override
	public void log(AccessLog accessLog) {
		super.insert(accessLog);
	}
	
	public Long countFor(Nginx nginx) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		Root<AccessLog> root = criteriaQuery.from(AccessLog.class);
		criteriaQuery.select(criteriaBuilder.count(criteriaQuery.from(AccessLog.class).get(AccessLog_.id)))
			.where(criteriaBuilder.equal(root.join(AccessLog_.nginx).get(Nginx_.id), nginx.getId()));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<AccessLog> listAllFor(Nginx nginx,Integer firstResult, Integer maxResults) {	
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<AccessLog> criteriaQuery = criteriaBuilder.createQuery(AccessLog.class);
		Root<AccessLog> root = criteriaQuery.from(AccessLog.class);
		criteriaQuery.where(criteriaBuilder.equal(root.join(AccessLog_.nginx).get(Nginx_.id), nginx.getId()));
		criteriaQuery.orderBy(criteriaBuilder.desc(root.get(AccessLog_.timestamp)));
		TypedQuery<AccessLog> query = entityManager.createQuery(criteriaQuery);
		if (firstResult != null && maxResults != null) {
			query.setFirstResult(firstResult).setMaxResults(maxResults);
		}
		return query.getResultList();
	}
}
