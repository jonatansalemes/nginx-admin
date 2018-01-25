package com.jslsolucoes.nginx.admin.agent.model.request;

public class NginxServerInfoRequest {

	private String home;
	private String bin;
	
	public NginxServerInfoRequest() {
		
	}
	
	public NginxServerInfoRequest(String home,String bin) {
		this.home = home;
		this.bin = bin;
	}

	public String getHome() {
		return home;
	}

	public void setHome(String home) {
		this.home = home;
	}

	public String getBin() {
		return bin;
	}

	public void setBin(String bin) {
		this.bin = bin;
	}

}
