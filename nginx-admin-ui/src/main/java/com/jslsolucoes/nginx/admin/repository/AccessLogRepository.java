package com.jslsolucoes.nginx.admin.repository;

import java.util.List;

import com.jslsolucoes.nginx.admin.model.AccessLog;

public interface AccessLogRepository {

	public void log(AccessLog accessLog);
	
	public List<AccessLog> listAll(Integer firstResult,Integer maxResults);

	public Long count();
}
