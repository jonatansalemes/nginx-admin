package com.jslsolucoes.nginx.admin.agent.model.request;

public class NginxAccessLogRotateRequest {

	private String home;
	
	public NginxAccessLogRotateRequest() {
		
	}
	
	public NginxAccessLogRotateRequest(String home) {
		this.home = home;
	}

	public String getHome() {
		return home;
	}

	public void setHome(String home) {
		this.home = home;
	}


}
