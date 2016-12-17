package com.jslsolucoes.nginx.admin.repository;

import java.util.List;

import com.jslsolucoes.nginx.admin.model.Nginx;
import com.jslsolucoes.nginx.admin.repository.impl.OperationResult;
import com.jslsolucoes.nginx.admin.repository.impl.OperationType;

public interface NginxRepository {
	public Nginx configuration();

	public OperationType insert(Nginx nginx);

	public List<String> validateBeforeSaveOrUpdate(Nginx nginx);

	public OperationResult saveOrUpdate(Nginx nginx);

}
