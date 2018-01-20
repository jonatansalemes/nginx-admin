package com.jslsolucoes.nginx.admin.repository.impl;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

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
	public List<String> validateBeforeSaveOrUpdate(Nginx nginx) {
		List<String> errors = new ArrayList<>();

		return errors;
	}

}
