package com.jslsolucoes.nginx.admin.agent.model.request;

public class NginxImportConfRequest {

	private String conf;
	
	public NginxImportConfRequest() {
		
	}
	
	public NginxImportConfRequest(String conf) {
		this.conf = conf;	
	}

	public String getConf() {
		return conf;
	}

	public void setConf(String conf) {
		this.conf = conf;
	}
}
