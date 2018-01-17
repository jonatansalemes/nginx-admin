package com.jslsolucoes.nginx.admin.agent.model.request;

public class NginxAccessLogCollectRequest {

	private String home;
	
	public NginxAccessLogCollectRequest() {
		
	}
	
	public NginxAccessLogCollectRequest(String home) {
		this.home = home;
	}

	public String getHome() {
		return home;
	}

	public void setHome(String home) {
		this.home = home;
	}


}
