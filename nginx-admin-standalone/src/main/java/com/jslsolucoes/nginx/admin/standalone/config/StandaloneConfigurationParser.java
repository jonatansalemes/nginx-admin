package com.jslsolucoes.nginx.admin.standalone.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

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
		standaloneConfiguration.setDatabase(database());
		standaloneConfiguration.setSmtp(smtp());
		return standaloneConfiguration;
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
		smtp.setMailList(mailList(properties.getProperty("NGINX_ADMIN_MAIL_MAILING_LIST")));
		smtp.setSubject(properties.getProperty("NGINX_ADMIN_SUBJECT"));
		smtp.setCharset(properties.getProperty("NGINX_ADMIN_CHARSET"));
		return smtp;
	}

	private List<String> mailList(String emails) {
		return Arrays.asList(emails.split(",")).stream().collect(Collectors.toList());
	}

	private Application application() {
		Application application = new Application();
		application.setVersion(properties.getProperty("NGINX_ADMIN_VERSION"));
		application.setUrlBase(properties.getProperty("NGINX_ADMIN_URL_BASE"));
		return application;
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
