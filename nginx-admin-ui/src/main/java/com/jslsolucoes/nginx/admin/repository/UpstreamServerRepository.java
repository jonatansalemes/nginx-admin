package com.jslsolucoes.nginx.admin.repository;

import java.util.List;

import com.jslsolucoes.nginx.admin.model.Upstream;
import com.jslsolucoes.nginx.admin.model.UpstreamServer;

public interface UpstreamServerRepository {

	public void create(Upstream upstream, List<UpstreamServer> upstreamServers);

	public void deleteAllFor(Upstream upstream);

}
