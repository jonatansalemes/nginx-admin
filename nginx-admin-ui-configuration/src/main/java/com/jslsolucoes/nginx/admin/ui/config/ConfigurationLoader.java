package com.jslsolucoes.nginx.admin.ui.config;

import java.util.Properties;

import com.jslsolucoes.properties.PropertiesBuilder;

public class ConfigurationLoader {

	private Properties properties = new Properties();

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
		standaloneConfiguration.setAccessLog(accessLog());
		standaloneConfiguration.setErrorLog(errorLog());
		standaloneConfiguration.setApplication(application());
		standaloneConfiguration.setDatabase(database());
		standaloneConfiguration.setSmtp(smtp());
		return standaloneConfiguration;
	}

	private Log accessLog() {
		Log log = new Log();
		log.setCollect(Integer.valueOf(properties.getProperty("NGINX_ADMIN_LOG_ACCESS_COLLECT_INTERVAL")));
		log.setRotate(Integer.valueOf(properties.getProperty("NGINX_ADMIN_LOG_ACCESS_ROTATE_INTERVAL")));
		return log;
	}

	private Log errorLog() {
		Log log = new Log();
		log.setCollect(Integer.valueOf(properties.getProperty("NGINX_ADMIN_LOG_ERROR_COLLECT_INTERVAL")));
		log.setRotate(Integer.valueOf(properties.getProperty("NGINX_ADMIN_LOG_ERROR_ROTATE_INTERVAL")));
		return log;
	}

	private Smtp smtp() {
		Smtp smtp = new Smtp();
		smtp.setHost(properties.getProperty("NGINX_ADMIN_MAIL_SERVER"));
		smtp.setPort(Integer.valueOf(properties.getProperty("NGINX_ADMIN_MAIL_PORT")));
		smtp.setTls(Boolean.valueOf(properties.getProperty("NGINX_ADMIN_MAIL_TLS")));
		smtp.setFromName(properties.getProperty("NGINX_ADMIN_MAIL_FROM_NAME"));
		smtp.setFromAddress(properties.getProperty("NGINX_ADMIN_MAIL_FROM_ADDRESS"));
		smtp.setAuthenticate(Boolean.valueOf(properties.getProperty("NGINX_ADMIN_MAIL_AUTHENTICATE")));
		smtp.setUserName(properties.getProperty("NGINX_ADMIN_MAIL_USERNAME"));
		smtp.setPassword(properties.getProperty("NGINX_ADMIN_MAIL_PASSWORD"));
		smtp.setCharset(properties.getProperty("NGINX_ADMIN_MAIL_CHARSET"));
		return smtp;
	}

	private Application application() {
		Application application = new Application();
		application.setVersion(properties.getProperty("NGINX_ADMIN_VERSION"));
		application.setUrlBase(properties.getProperty("NGINX_ADMIN_URL_BASE"));
		return application;
	}

	private Database database() {
		Database database = new Database();
		database.setDriver(properties.getProperty("NGINX_ADMIN_DB_DRIVER"));
		database.setLocation(properties.getProperty("NGINX_ADMIN_DB_LOCATION"));
		database.setHost(properties.getProperty("NGINX_ADMIN_DB_HOST"));
		database.setName(properties.getProperty("NGINX_ADMIN_DB_NAME"));
		database.setPort(Integer.valueOf(properties.getProperty("NGINX_ADMIN_DB_PORT")));
		database.setUsername(properties.getProperty("NGINX_ADMIN_DB_USERNAME"));
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
		server.setHttpPort(Integer.valueOf(properties.getProperty("NGINX_ADMIN_HTTP_PORT")));
		server.setHttpsPort(Integer.valueOf(properties.getProperty("NGINX_ADMIN_HTTPS_PORT")));
		return server;
	}
}
