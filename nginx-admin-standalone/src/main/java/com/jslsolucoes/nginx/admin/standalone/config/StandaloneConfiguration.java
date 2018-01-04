package com.jslsolucoes.nginx.admin.standalone.config;

public class StandaloneConfiguration {

	private Server server;
	private Database database;
	private Application application;

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
}
