package com.jslsolucoes.nginx.admin.nginx.parser.directive;

public class UpstreamDirectiveServer {

	private String ip;
	private Integer port;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}
}
