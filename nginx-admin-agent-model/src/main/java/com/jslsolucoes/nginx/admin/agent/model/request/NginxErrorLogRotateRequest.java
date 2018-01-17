package com.jslsolucoes.nginx.admin.agent.model.request;

public class NginxErrorLogRotateRequest {

	private String home;
	
	public NginxErrorLogRotateRequest() {
		
	}
	
	public NginxErrorLogRotateRequest(String home) {
		this.home = home;
	}

	public String getHome() {
		return home;
	}

	public void setHome(String home) {
		this.home = home;
	}


}
