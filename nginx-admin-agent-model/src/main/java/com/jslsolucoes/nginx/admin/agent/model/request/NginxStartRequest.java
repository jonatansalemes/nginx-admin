package com.jslsolucoes.nginx.admin.agent.model.request;

public class NginxStartRequest {

	private String bin;
	
	public NginxStartRequest() {
		
	}
	
	public NginxStartRequest(String bin) {
		this.bin = bin;
	}

	public String getBin() {
		return bin;
	}

	public void setBin(String bin) {
		this.bin = bin;
	}
	
	
	
}
