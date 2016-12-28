package com.jslsolucoes.nginx.admin.repository;

import com.jslsolucoes.nginx.admin.model.AccessLog;

public interface AccessLogRepository {

	public void log(AccessLog accessLog);
}
