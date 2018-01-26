package com.jslsolucoes.nginx.admin.agent.config;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConfigurationLoader {

	private Properties properties =  new Properties();

	private ConfigurationLoader() {
		
	}
	
	public static ConfigurationLoader newBuilder() {
		return new ConfigurationLoader();
	}
	
	public ConfigurationLoader withProperties(Properties properties) {
		this.properties = properties;
		return replace();
	}
	
	public ConfigurationLoader withFile(String path) {
		try {
			properties.load(new FileInputStream(new File(path)));
			return replace();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private ConfigurationLoader replace() {
		properties
			.entrySet()
			.forEach(entry-> {
				String key = (String) entry.getKey();
				String value = (String) entry.getValue();
				properties.setProperty(key, replace(value));
			});
		return this;
	}
	
	private String replace(String oldValue) {
		String value = oldValue;
		Matcher matcher = Pattern.compile("\\$([\\w]*)").matcher(value);
		while(matcher.find()){
			String currentValue = (String) properties.get(matcher.group(1));
			value = matcher.replaceAll(currentValue);
		}
		return value;
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
		application.setUrlBase(properties.getProperty("NGINX_AGENT_APP_URL_BASE"));
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
