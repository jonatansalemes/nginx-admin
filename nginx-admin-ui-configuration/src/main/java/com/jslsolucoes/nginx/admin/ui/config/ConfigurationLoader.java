package com.jslsolucoes.nginx.admin.ui.config;

import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ConfigurationLoader {

	private Properties properties;

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
		smtp.setSubject(properties.getProperty("NGINX_ADMIN_MAIL_SUBJECT"));
		smtp.setCharset(properties.getProperty("NGINX_ADMIN_MAIL_CHARSET"));
		return smtp;
	}

	private List<String> mailList(String emails) {
		return Arrays.asList(emails.split(",")).stream().collect(Collectors.toList());
	}

	private Application application() {
		Application application = new Application();
		application.setVersion(properties.getProperty("NGINX_ADMIN_APP_VERSION"));
		application.setUrlBase(properties.getProperty("NGINX_ADMIN_APP_URL_BASE"));
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
		server.setHttpPort(Integer.valueOf(properties.getProperty("NGINX_ADMIN_HTTP_PORT")));
		server.setHttpsPort(Integer.valueOf(properties.getProperty("NGINX_ADMIN_HTTPS_PORT")));
		return server;
	}
}
