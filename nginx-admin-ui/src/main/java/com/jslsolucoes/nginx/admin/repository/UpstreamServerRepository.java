package com.jslsolucoes.nginx.admin.repository;

import java.util.List;

import com.jslsolucoes.nginx.admin.model.Upstream;
import com.jslsolucoes.nginx.admin.model.UpstreamServer;

public interface UpstreamServerRepository {

	public void recreate(Upstream upstream, List<UpstreamServer> upstreamServers) throws Exception;

	public void deleteAllFor(Upstream upstream) throws Exception;
	
}
