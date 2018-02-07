package com.jslsolucoes.nginx.admin.agent.configuration;

import java.util.Properties;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import com.jslsolucoes.cdi.misc.annotation.ApplicationProperties;
import com.jslsolucoes.nginx.admin.agent.config.Configuration;
import com.jslsolucoes.nginx.admin.agent.config.ConfigurationLoader;

@ApplicationScoped
public class ConfigurationFactory {

	@Inject
	@ApplicationProperties
	private Properties properties;

	@Produces
	public Configuration getInstance() {
		return ConfigurationLoader.newBuilder().withProperties(properties).build();
	}
}
