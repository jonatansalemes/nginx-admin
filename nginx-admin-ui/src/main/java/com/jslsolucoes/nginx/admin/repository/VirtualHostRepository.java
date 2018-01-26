package com.jslsolucoes.nginx.admin.repository;

import java.io.IOException;
import java.util.List;

import com.jslsolucoes.nginx.admin.error.NginxAdminException;
import com.jslsolucoes.nginx.admin.model.Nginx;
import com.jslsolucoes.nginx.admin.model.VirtualHost;
import com.jslsolucoes.nginx.admin.model.VirtualHostAlias;
import com.jslsolucoes.nginx.admin.model.VirtualHostLocation;
import com.jslsolucoes.nginx.admin.repository.impl.OperationResult;
import com.jslsolucoes.nginx.admin.repository.impl.OperationStatusType;

public interface VirtualHostRepository {

	public List<VirtualHost> listAllFor(Nginx nginx);

	public OperationStatusType deleteWithResource(VirtualHost virtualHost) throws IOException;

	public VirtualHost load(VirtualHost virtualHost);

	public OperationResult saveOrUpdate(VirtualHost virtualHost, List<VirtualHostAlias> aliases,
			List<VirtualHostLocation> locations) throws NginxAdminException;

	public List<String> validateBeforeSaveOrUpdate(VirtualHost virtualHost, List<VirtualHostAlias> aliases,
			List<VirtualHostLocation> locations);

	public VirtualHost hasEquals(VirtualHost virtualHost, List<VirtualHostAlias> aliases);

	public List<VirtualHost> searchFor(Nginx nginx,String term);
}
