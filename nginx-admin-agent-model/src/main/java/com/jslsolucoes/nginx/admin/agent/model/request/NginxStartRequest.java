package com.jslsolucoes.nginx.admin.agent.model.request;

public class NginxStartRequest {

	private String bin;
	private String conf;
	
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

	public String getConf() {
		return conf;
	}

	public void setConf(String conf) {
		this.conf = conf;
	}
	
	
	
}
