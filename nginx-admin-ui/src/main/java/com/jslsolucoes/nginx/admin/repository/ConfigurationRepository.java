package com.jslsolucoes.nginx.admin.repository;

import com.jslsolucoes.nginx.admin.model.Configuration;
import com.jslsolucoes.nginx.admin.model.Nginx;

public interface ConfigurationRepository {

	public Configuration loadFor(Nginx nginx);
}
