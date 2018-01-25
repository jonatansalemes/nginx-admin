package com.jslsolucoes.nginx.admin.repository;

import java.util.List;

import com.jslsolucoes.nginx.admin.model.ErrorLog;
import com.jslsolucoes.nginx.admin.model.Nginx;

public interface ErrorLogRepository {

	public void log(ErrorLog errorLog);
	
	public List<ErrorLog> listAllFor(Nginx nginx,Integer firstResult,Integer maxResults);

	public Long countFor(Nginx nginx);
}
