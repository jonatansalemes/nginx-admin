package com.jslsolucoes.nginx.admin.standalone.config;

public class Server {

	private Integer httpPort;
	private Integer httpsPort;
	public Integer getHttpPort() {
		return httpPort;
	}
	public void setHttpPort(Integer httpPort) {
		this.httpPort = httpPort;
	}
	public Integer getHttpsPort() {
		return httpsPort;
	}
	public void setHttpsPort(Integer httpsPort) {
		this.httpsPort = httpsPort;
	}
	
}
