package com.jslsolucoes.nginx.admin.agent.model.request;

public class NginxErrorLogCollectRequest {

	private String home;
	
	public NginxErrorLogCollectRequest() {
		
	}
	
	public NginxErrorLogCollectRequest(String home) {
		this.home = home;
	}

	public String getHome() {
		return home;
	}

	public void setHome(String home) {
		this.home = home;
	}


}
