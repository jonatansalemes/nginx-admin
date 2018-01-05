package com.jslsolucoes.nginx.admin.repository;

import com.jslsolucoes.nginx.admin.repository.impl.ConfigurationType;

public interface ConfigurationRepository {

	public Integer integer(ConfigurationType configurationType);

	public String string(ConfigurationType urlBase);

	public void update(ConfigurationType configurationType, String value);

}
