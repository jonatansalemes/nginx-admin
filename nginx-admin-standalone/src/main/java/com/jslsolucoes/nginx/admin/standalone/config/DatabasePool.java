package com.jslsolucoes.nginx.admin.standalone.config;

public class DatabasePool {

	private Integer maxConnection;
	private Integer minConnection;
	private Integer initialConnection;
	public Integer getMaxConnection() {
		return maxConnection;
	}
	public void setMaxConnection(Integer maxConnection) {
		this.maxConnection = maxConnection;
	}
	public Integer getMinConnection() {
		return minConnection;
	}
	public void setMinConnection(Integer minConnection) {
		this.minConnection = minConnection;
	}
	public Integer getInitialConnection() {
		return initialConnection;
	}
	public void setInitialConnection(Integer initialConnection) {
		this.initialConnection = initialConnection;
	}
	
	
}
