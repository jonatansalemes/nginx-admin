package com.jslsolucoes.nginx.admin.agent.model.response;

public class NginxStartResponse {

	private String message;
	
	public NginxStartResponse() {
		
	}
	
	public NginxStartResponse(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
