package com.jslsolucoes.nginx.admin.agent.model.request;

public class NginxLogRotateRequest {

	private String home;
	
	public NginxLogRotateRequest() {
		
	}
	
	public NginxLogRotateRequest(String home) {
		this.home = home;
	}

	public String getHome() {
		return home;
	}

	public void setHome(String home) {
		this.home = home;
	}


}
