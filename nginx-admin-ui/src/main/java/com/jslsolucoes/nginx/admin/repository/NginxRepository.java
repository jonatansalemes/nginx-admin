package com.jslsolucoes.nginx.admin.repository;

import java.util.List;

import com.jslsolucoes.nginx.admin.model.Nginx;
import com.jslsolucoes.nginx.admin.repository.impl.OperationResult;

public interface NginxRepository {
	public Nginx configuration();

	public void insert(Nginx nginx);

	public List<String> validateBeforeSaveOrUpdate(Nginx nginx);

	public OperationResult saveOrUpdate(Nginx nginx);

}
