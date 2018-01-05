package com.jslsolucoes.nginx.admin.standalone.config;

public class StandaloneConfiguration {

	private Server server;
	private Application application;

	public Server getServer() {
		return server;
	}

	public void setServer(Server server) {
		this.server = server;
	}

	public Application getApplication() {
		return application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}

}
