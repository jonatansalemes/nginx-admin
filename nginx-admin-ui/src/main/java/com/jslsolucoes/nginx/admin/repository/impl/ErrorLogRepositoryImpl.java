package com.jslsolucoes.nginx.admin.repository.impl;

import java.io.File;
import com.jslsolucoes.i18n.Messages;
import java.io.IOException;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.commons.io.FileUtils;

import com.jslsolucoes.nginx.admin.model.AccessLog;
import com.jslsolucoes.nginx.admin.model.ErrorLog;
import com.jslsolucoes.nginx.admin.model.ErrorLog_;
import com.jslsolucoes.nginx.admin.model.Nginx;
import com.jslsolucoes.nginx.admin.model.Nginx_;
import com.jslsolucoes.nginx.admin.repository.ErrorLogRepository;
import com.jslsolucoes.nginx.admin.repository.NginxRepository;

@RequestScoped
public class ErrorLogRepositoryImpl extends RepositoryImpl<ErrorLog>  implements ErrorLogRepository {
	
	@Deprecated
	public ErrorLogRepositoryImpl() {
		
	}

	@Inject
	public ErrorLogRepositoryImpl(EntityManager entityManager) {
		super(entityManager);
	}


	@Override
	public void log(ErrorLog errorLog) {
		super.insert(errorLog);
	}
	
	public Long countFor(Nginx nginx) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		Root<ErrorLog> root = criteriaQuery.from(ErrorLog.class);
		criteriaQuery.select(criteriaBuilder.count(criteriaQuery.from(ErrorLog.class).get(ErrorLog_.id)))
			.where(criteriaBuilder.equal(root.join(ErrorLog_.nginx).get(Nginx_.id), nginx.getId()));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<ErrorLog> listAllFor(Nginx nginx,Integer firstResult, Integer maxResults) {	
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<ErrorLog> criteriaQuery = criteriaBuilder.createQuery(ErrorLog.class);
		Root<ErrorLog> root = criteriaQuery.from(ErrorLog.class);
		criteriaQuery.where(criteriaBuilder.equal(root.join(ErrorLog_.nginx).get(Nginx_.id), nginx.getId()));
		criteriaQuery.orderBy(criteriaBuilder.desc(root.get(ErrorLog_.timestamp)));
		TypedQuery<ErrorLog> query = entityManager.createQuery(criteriaQuery);
		if (firstResult != null && maxResults != null) {
			query.setFirstResult(firstResult).setMaxResults(maxResults);
		}
		return query.getResultList();
	}

}
