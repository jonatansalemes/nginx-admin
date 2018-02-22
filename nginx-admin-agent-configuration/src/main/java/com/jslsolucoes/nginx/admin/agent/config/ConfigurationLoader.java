package com.jslsolucoes.nginx.admin.agent.config;

import java.util.Properties;

import com.jslsolucoes.properties.PropertiesBuilder;

public class ConfigurationLoader {

	private Properties properties = new Properties();

	private ConfigurationLoader() {

	}

	public static ConfigurationLoader newBuilder() {
		return new ConfigurationLoader();
	}

	public ConfigurationLoader withProperties(Properties properties) {
		this.properties = PropertiesBuilder.newBuilder().withProperties(properties).build();
		return this;
	}

	public ConfigurationLoader withFile(String path) {
		properties = PropertiesBuilder.newBuilder().withFile(path).build();
		return this;
	}

	public Configuration build() {
		Configuration standaloneConfiguration = new Configuration();
		standaloneConfiguration.setServer(server());
		standaloneConfiguration.setApplication(application());
		standaloneConfiguration.setNginx(nginx());
		return standaloneConfiguration;
	}

	private Nginx nginx() {
		Nginx nginx = new Nginx();
		nginx.setBin(properties.getProperty("NGINX_BIN"));
		nginx.setSetting(properties.getProperty("NGINX_AGENT_SETTINGS"));
		return nginx;
	}

	private Application application() {
		Application application = new Application();
		application.setVersion(properties.getProperty("NGINX_AGENT_VERSION"));
		application.setUrlBase(properties.getProperty("NGINX_AGENT_URL_BASE"));
		application.setAuthorizationKey(properties.getProperty("NGINX_AGENT_AUTHORIZATION_KEY"));
		return application;
	}

	private Server server() {
		Server server = new Server();
		server.setHttpPort(Integer.valueOf(properties.getProperty("NGINX_AGENT_HTTP_PORT")));
		server.setHttpsPort(Integer.valueOf(properties.getProperty("NGINX_AGENT_HTTPS_PORT")));
		return server;
	}
}
