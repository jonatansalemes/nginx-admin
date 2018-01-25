package com.jslsolucoes.nginx.admin.agent.model.request;

public class NginxUpstreamDeleteRequest {

	private String home;
	public NginxUpstreamDeleteRequest() {
		
	}
	
	public NginxUpstreamDeleteRequest(String home) {
		this.home = home;
	}
	
	public String getHome() {
		return home;
	}
	public void setHome(String home) {
		this.home = home;
	}
}
