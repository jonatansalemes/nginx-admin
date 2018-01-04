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
		standaloneConfiguration.setDatabase(database());
		return standaloneConfiguration;
	}

	private Database database() {
		Database database = new Database();
		database.setDatabaseDriver(DatabaseDriver.forName(properties.getProperty("NGINX_ADMIN_DB_DRIVER")));
		database.setHost(properties.getProperty("NGINX_ADMIN_DB_HOST"));
		database.setLocation(properties.getProperty("NGINX_ADMIN_DB_LOCATION"));
		database.setName(properties.getProperty("NGINX_ADMIN_DB_NAME"));
		database.setPort(Integer.valueOf(properties.getProperty("NGINX_ADMIN_DB_PORT")));
		database.setSid(properties.getProperty("NGINX_ADMIN_DB_SID"));
		database.setUserName(properties.getProperty("NGINX_ADMIN_DB_USERNAME"));
		database.setPassword(properties.getProperty("NGINX_ADMIN_DB_PASSWORD"));
		database.setDatabasePool(databasePool());
		return database;
	}

	private DatabasePool databasePool() {
		DatabasePool databasePool = new DatabasePool();
		databasePool.setInitialConnection(Integer.valueOf(properties.getProperty("NGINX_ADMIN_DB_POOL_INITIAL")));
		databasePool.setMaxConnection(Integer.valueOf(properties.getProperty("NGINX_ADMIN_DB_POOL_MAX")));
		databasePool.setMinConnection(Integer.valueOf(properties.getProperty("NGINX_ADMIN_DB_POOL_MIN")));
		return databasePool;
	}

	private Server server() {
		Server server = new Server();
		server.setPort(Integer.valueOf(properties.getProperty("NGINX_ADMIN_PORT")));
		return server;
	}
}
