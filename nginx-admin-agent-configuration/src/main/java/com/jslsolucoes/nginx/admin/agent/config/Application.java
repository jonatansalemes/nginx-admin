package com.jslsolucoes.nginx.admin.agent.config;

public class Application {
	private String version;
	private String urlBase;
	private String authorizationKey;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getUrlBase() {
		return urlBase;
	}

	public void setUrlBase(String urlBase) {
		this.urlBase = urlBase;
	}

	public String getAuthorizationKey() {
		return authorizationKey;
	}

	public void setAuthorizationKey(String authorizationKey) {
		this.authorizationKey = authorizationKey;
	}
}
