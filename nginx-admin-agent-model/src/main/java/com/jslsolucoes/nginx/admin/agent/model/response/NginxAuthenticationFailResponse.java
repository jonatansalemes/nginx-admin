package com.jslsolucoes.nginx.admin.agent.model.response;

public class NginxAuthenticationFailResponse implements NginxResponse {

	private String message;
	
	public NginxAuthenticationFailResponse() {
		
	}
	
	public NginxAuthenticationFailResponse(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public boolean forbidden() {
		return true;
	}
	
	
	
}
