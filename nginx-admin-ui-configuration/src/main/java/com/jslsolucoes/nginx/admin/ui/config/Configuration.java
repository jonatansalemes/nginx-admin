package com.jslsolucoes.nginx.admin.ui.config;

public class Configuration {

	private Log accessLog;
	private Log errorLog;
	private Server server;
	private Database database;
	private Application application;
	private Smtp smtp;

	public Server getServer() {
		return server;
	}

	public void setServer(Server server) {
		this.server = server;
	}

	public Database getDatabase() {
		return database;
	}

	public void setDatabase(Database database) {
		this.database = database;
	}

	public Application getApplication() {
		return application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}

	public Smtp getSmtp() {
		return smtp;
	}

	public void setSmtp(Smtp smtp) {
		this.smtp = smtp;
	}

	public Log getAccessLog() {
		return accessLog;
	}

	public void setAccessLog(Log accessLog) {
		this.accessLog = accessLog;
	}

	public Log getErrorLog() {
		return errorLog;
	}

	public void setErrorLog(Log errorLog) {
		this.errorLog = errorLog;
	}
}
