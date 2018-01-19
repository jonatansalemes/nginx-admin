package com.jslsolucoes.nginx.admin.repository.impl;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import com.jslsolucoes.nginx.admin.error.NginxAdminException;
import com.jslsolucoes.nginx.admin.model.Nginx;
import com.jslsolucoes.nginx.admin.repository.NginxRepository;

@RequestScoped
public class NginxRepositoryImpl extends RepositoryImpl<Nginx> implements NginxRepository {

	
	public NginxRepositoryImpl() {
	
	}

	@Inject
	public NginxRepositoryImpl(EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	public Nginx configuration() {
		try {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Nginx> criteriaQuery = criteriaBuilder.createQuery(Nginx.class);
			criteriaQuery.select(criteriaQuery.from(Nginx.class));
			return entityManager.createQuery(criteriaQuery).getSingleResult();
		} catch (NoResultException noResultException) {
			return null;
		}
	}

	@Override
	public List<String> validateBeforeSaveOrUpdate(Nginx nginx) {
		List<String> errors = new ArrayList<>();

		return errors;
	}

	@Override
	public OperationResult saveOrUpdateAndConfigure(Nginx nginx) throws NginxAdminException {
		nginx.setSettings(normalize(nginx.getSettings()));
		nginx.setBin(normalize(nginx.getBin()));
		configure(nginx);
		return super.saveOrUpdate(nginx);
	}

	private String normalize(String path) {
		return path.replaceAll("\\\\", "/");
	}

	private void configure(Nginx nginx) throws NginxAdminException {
		
	}	
}
