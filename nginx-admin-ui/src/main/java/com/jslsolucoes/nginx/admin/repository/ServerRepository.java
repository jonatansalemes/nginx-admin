package com.jslsolucoes.nginx.admin.repository;

import java.util.List;

import com.jslsolucoes.nginx.admin.model.Nginx;
import com.jslsolucoes.nginx.admin.model.Server;
import com.jslsolucoes.nginx.admin.repository.impl.OperationResult;
import com.jslsolucoes.nginx.admin.repository.impl.OperationType;

public interface ServerRepository {

	public List<Server> listAllFor(Nginx nginx);

	public OperationType delete(Server server);

	public Server load(Server server);

	public OperationResult saveOrUpdate(Server server);

	public List<String> validateBeforeSaveOrUpdate(Server server);

	public Server hasEquals(Server server);

	public OperationType insert(Server server);

	public Server findByIp(String ip);
}
