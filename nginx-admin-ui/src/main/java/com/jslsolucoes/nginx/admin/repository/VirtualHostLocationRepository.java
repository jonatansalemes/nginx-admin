package com.jslsolucoes.nginx.admin.repository;

import java.util.List;

import com.jslsolucoes.nginx.admin.model.VirtualHost;
import com.jslsolucoes.nginx.admin.model.VirtualHostLocation;

public interface VirtualHostLocationRepository {

	public void recreateAllFor(VirtualHost virtualHost, List<VirtualHostLocation> locations);

	public void deleteAllFor(VirtualHost virtualHost);

}
