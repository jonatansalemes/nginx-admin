package com.jslsolucoes.nginx.admin.database;

import java.io.IOException;
import java.util.Properties;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import com.jslsolucoes.nginx.admin.annotation.Application;

public class ApplicationProperties {

	@Produces
	@ApplicationScoped
	@Application
	public Properties getInstance() throws IOException {
		Properties properties = new Properties();
		properties.load(getClass().getResourceAsStream("/application.properties"));
		return properties;
	}
}
