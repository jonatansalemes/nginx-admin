package com.jslsolucoes.nginx.admin.repository;

import java.util.List;

import com.jslsolucoes.nginx.admin.error.NginxAdminException;
import com.jslsolucoes.nginx.admin.model.Nginx;
import com.jslsolucoes.nginx.admin.repository.impl.OperationResult;
import com.jslsolucoes.nginx.admin.repository.impl.OperationStatusType;

public interface NginxRepository {

	public OperationStatusType insert(Nginx nginx);

	public List<String> validateBeforeSaveOrUpdate(Nginx nginx);

	public OperationResult saveOrUpdate(Nginx nginx) throws NginxAdminException;

	public List<Nginx> listAll();
	
	public List<Nginx> listAllConfigured();

	public Nginx load(Nginx nginx);

	public OperationStatusType delete(Nginx nginx);

}
