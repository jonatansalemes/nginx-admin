package com.jslsolucoes.nginx.admin.agent.model;

public class Endpoint {

	private String ip;
	private Integer port;

	public Endpoint() {

	}

	public Endpoint(String ip, Integer port) {
		this.ip = ip;
		this.port = port;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

}
