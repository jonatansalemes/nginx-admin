package com.jslsolucoes.nginx.admin.repository;

import java.util.List;

import com.jslsolucoes.nginx.admin.error.NginxAdminException;
import com.jslsolucoes.nginx.admin.model.Nginx;
import com.jslsolucoes.nginx.admin.repository.impl.OperationResult;
import com.jslsolucoes.nginx.admin.repository.impl.OperationType;

public interface NginxRepository {
	
	public OperationType insert(Nginx nginx);

	public List<String> validateBeforeSaveOrUpdate(Nginx nginx);

	public OperationResult saveOrUpdate(Nginx nginx) throws NginxAdminException;

	public List<Nginx> listAll();

	public Nginx load(Nginx nginx);

	public OperationType delete(Nginx nginx);

}
