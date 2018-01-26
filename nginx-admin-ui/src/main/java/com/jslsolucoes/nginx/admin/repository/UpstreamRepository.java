package com.jslsolucoes.nginx.admin.repository;

import java.util.List;

import com.jslsolucoes.nginx.admin.model.Nginx;
import com.jslsolucoes.nginx.admin.model.Upstream;
import com.jslsolucoes.nginx.admin.model.UpstreamServer;
import com.jslsolucoes.nginx.admin.repository.impl.OperationResult;
import com.jslsolucoes.nginx.admin.repository.impl.OperationStatusType;

public interface UpstreamRepository {

	public List<Upstream> listAllFor(Nginx nginx);

	public OperationStatusType delete(Upstream upstream);

	public Upstream load(Upstream upstream);

	public OperationResult saveOrUpdate(Upstream upstream, List<UpstreamServer> upstreamServers);

	public List<String> validateBeforeSaveOrUpdate(Upstream upstream, List<UpstreamServer> upstreamServers);

	public Upstream findByName(String name);

	public Upstream hasEquals(Upstream upstream);

}
