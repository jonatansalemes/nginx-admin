package com.jslsolucoes.nginx.admin.repository;

import com.jslsolucoes.nginx.admin.model.Configuration;
import com.jslsolucoes.nginx.admin.model.Nginx;
import com.jslsolucoes.nginx.admin.repository.impl.OperationResult;

public interface ConfigurationRepository {

	public Configuration loadFor(Nginx nginx);

	public OperationResult saveOrUpdate(Configuration configuration);

}
