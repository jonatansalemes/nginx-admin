package com.jslsolucoes.nginx.admin.repository;

import java.util.List;

import com.jslsolucoes.nginx.admin.model.VirtualHost;
import com.jslsolucoes.nginx.admin.model.VirtualHostAlias;
import com.jslsolucoes.nginx.admin.model.VirtualHostLocation;
import com.jslsolucoes.nginx.admin.repository.impl.OperationResult;
import com.jslsolucoes.nginx.admin.repository.impl.OperationType;

public interface VirtualHostRepository {

	public List<VirtualHost> listAll();

	public OperationType delete(VirtualHost virtualHost) throws Exception;

	public VirtualHost load(VirtualHost virtualHost);

	public OperationResult saveOrUpdate(VirtualHost virtualHost,List<VirtualHostAlias> aliases,List<VirtualHostLocation> locations) throws Exception;

	public List<String> validateBeforeSaveOrUpdate(VirtualHost virtualHost,List<VirtualHostAlias> aliases,List<VirtualHostLocation> locations);

	public VirtualHost hasEquals(VirtualHost virtualHost,List<VirtualHostAlias> aliases);

	public List<VirtualHost> search(String term);
}
