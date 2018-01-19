package com.jslsolucoes.nginx.admin.agent.model.request;

public class NginxCliRequest {

	private String bin;
	private String home;
	
	public NginxCliRequest() {
		
	}
	
	public NginxCliRequest(String bin) {
		this.bin = bin;
	}

	public String getBin() {
		return bin;
	}

	public void setBin(String bin) {
		this.bin = bin;
	}

	public String getHome() {
		return home;
	}

	public void setHome(String home) {
		this.home = home;
	}
	
}
