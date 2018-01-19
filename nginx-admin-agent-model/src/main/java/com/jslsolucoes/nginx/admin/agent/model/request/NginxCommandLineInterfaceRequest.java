package com.jslsolucoes.nginx.admin.agent.model.request;

public class NginxCommandLineInterfaceRequest {

	private String bin;
	private String home;
	
	public NginxCommandLineInterfaceRequest() {
		
	}
	
	public NginxCommandLineInterfaceRequest(String bin,String home) {
		this.bin = bin;
		this.home = home;
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
