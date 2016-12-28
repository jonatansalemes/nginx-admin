package com.jslsolucoes.nginx.admin.repository.impl;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.hibernate.Session;

import com.jslsolucoes.nginx.admin.model.AccessLog;
import com.jslsolucoes.nginx.admin.repository.AccessLogRepository;

@RequestScoped
public class AccessLogRepositoryImpl extends RepositoryImpl<AccessLog> implements AccessLogRepository {

	public AccessLogRepositoryImpl() {

	}

	@Inject
	public AccessLogRepositoryImpl(Session session) {
		super(session);
	}

	@Override
	public void log(AccessLog accessLog) {
		super.insert(accessLog);
	}
}
