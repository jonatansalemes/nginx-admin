package com.jslsolucoes.nginx.admin.repository;

import java.util.List;

import com.jslsolucoes.nginx.admin.model.VirtualHost;
import com.jslsolucoes.nginx.admin.repository.impl.OperationResult;
import com.jslsolucoes.nginx.admin.repository.impl.OperationType;

public interface VirtualHostRepository {

	public List<VirtualHost> listAll();

	public OperationType delete(VirtualHost virtualHost);

	public VirtualHost load(VirtualHost virtualHost);

	public OperationResult saveOrUpdate(VirtualHost virtualHost);

	public List<String> validateBeforeSaveOrUpdate(VirtualHost virtualHost);

	public VirtualHost hasEquals(VirtualHost virtualHost);

	public List<VirtualHost> search(String term);
}
