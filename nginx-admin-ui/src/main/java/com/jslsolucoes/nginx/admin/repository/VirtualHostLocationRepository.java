package com.jslsolucoes.nginx.admin.repository;

import java.util.List;

import com.jslsolucoes.nginx.admin.model.VirtualHost;
import com.jslsolucoes.nginx.admin.model.VirtualHostLocation;

public interface VirtualHostLocationRepository {

	public void recreate(VirtualHost virtualHost, List<VirtualHostLocation> locations) throws Exception;

	public void deleteAllFor(VirtualHost virtualHost) throws Exception;

}
