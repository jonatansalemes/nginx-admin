package com.jslsolucoes.nginx.admin.standalone.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class StandaloneConfigurationParser {

	private Properties properties = new Properties();

	private StandaloneConfigurationParser(String path) throws FileNotFoundException, IOException {
		properties.load(new FileInputStream(new File(path)));
	}

	public static StandaloneConfiguration parse(String path) throws FileNotFoundException, IOException {
		StandaloneConfigurationParser standaloneConfigurationParser = new StandaloneConfigurationParser(path);
		return standaloneConfigurationParser.build();
	}

	private StandaloneConfiguration build() {
		StandaloneConfiguration standaloneConfiguration = new StandaloneConfiguration();
		standaloneConfiguration.setServer(server());
		standaloneConfiguration.setApplication(application());
		return standaloneConfiguration;
	}

	private Application application() {
		Application application = new Application();
		application.setVersion(properties.getProperty("NGINX_AGENT_VERSION"));
		return application;
	}
	
	private Server server() {
		Server server = new Server();
		server.setPort(Integer.valueOf(properties.getProperty("NGINX_AGENT_PORT")));
		return server;
	}

}
