package com.jslsolucoes.nginx.admin.agent.model.response;

public class NginxIndexResponse implements NginxResponse {

	private String message;

	public NginxIndexResponse() {

	}

	public NginxIndexResponse(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
