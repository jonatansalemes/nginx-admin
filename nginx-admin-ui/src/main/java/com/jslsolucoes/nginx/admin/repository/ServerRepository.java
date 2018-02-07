package com.jslsolucoes.nginx.admin.repository;

import java.util.List;

import com.jslsolucoes.nginx.admin.model.Nginx;
import com.jslsolucoes.nginx.admin.model.Server;
import com.jslsolucoes.nginx.admin.repository.impl.OperationResult;
import com.jslsolucoes.nginx.admin.repository.impl.OperationStatusType;

public interface ServerRepository {

	public List<Server> listAllFor(Nginx nginx);

	public OperationStatusType delete(Server server);

	public Server load(Server server);

	public OperationResult saveOrUpdate(Server server);

	public List<String> validateBeforeSaveOrUpdate(Server server);

	public OperationStatusType insert(Server server);

	public Server searchFor(String ip, Nginx nginx);
}
