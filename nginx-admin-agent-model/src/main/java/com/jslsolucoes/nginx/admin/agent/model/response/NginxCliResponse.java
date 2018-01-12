package com.jslsolucoes.nginx.admin.agent.model.response;

public class NginxCliResponse implements NginxResponse {

	private String message;
	
	public NginxCliResponse() {
		
	}
	
	public NginxCliResponse(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
