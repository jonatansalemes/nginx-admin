package com.jslsolucoes.nginx.admin.agent.model.request;

public class NginxLogCollectRequest {

	private String home;
	
	public NginxLogCollectRequest() {
		
	}
	
	public NginxLogCollectRequest(String home) {
		this.home = home;
	}

	public String getHome() {
		return home;
	}

	public void setHome(String home) {
		this.home = home;
	}


}
