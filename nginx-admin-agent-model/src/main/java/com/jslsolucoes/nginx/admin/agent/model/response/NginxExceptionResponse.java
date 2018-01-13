package com.jslsolucoes.nginx.admin.agent.model.response;

public class NginxExceptionResponse implements NginxResponse {

	private String stackTrace;
	
	public NginxExceptionResponse() {
		
	}
	
	public NginxExceptionResponse(String stackTrace) {
		this.stackTrace = stackTrace;
	}

	public String getStackTrace() {
		return stackTrace;
	}

	public void setStackTrace(String stackTrace) {
		this.stackTrace = stackTrace;
	}
}
