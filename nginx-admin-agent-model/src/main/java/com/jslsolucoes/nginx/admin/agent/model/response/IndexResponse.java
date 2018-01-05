package com.jslsolucoes.nginx.admin.agent.model.response;

public class IndexResponse {

	private String message;
	
	public IndexResponse() {
		
	}

	public IndexResponse(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
