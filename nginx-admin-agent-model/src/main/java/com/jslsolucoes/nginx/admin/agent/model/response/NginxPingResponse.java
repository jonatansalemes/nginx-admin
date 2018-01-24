package com.jslsolucoes.nginx.admin.agent.model.response;

public class NginxPingResponse implements NginxResponse {

	private String message;
	
	public NginxPingResponse() {
		
	}

	public NginxPingResponse(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
