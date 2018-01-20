package com.jslsolucoes.nginx.admin.ui.config;

public class Configuration {

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
}
