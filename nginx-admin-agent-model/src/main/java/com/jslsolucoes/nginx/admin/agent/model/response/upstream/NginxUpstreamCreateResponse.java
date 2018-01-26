package com.jslsolucoes.nginx.admin.agent.model.response.upstream;

import com.jslsolucoes.nginx.admin.agent.model.response.NginxResponse;

public class NginxUpstreamCreateResponse implements NginxResponse {

	private String stackTrace;
	private Boolean success;
	
	public NginxUpstreamCreateResponse() {
		
	}
	
	public NginxUpstreamCreateResponse(String stackTrace,Boolean success) {
		this.stackTrace = stackTrace;
		this.success = success;
		
	}

	public String getStackTrace() {
		return stackTrace;
	}

	public void setStackTrace(String stackTrace) {
		this.stackTrace = stackTrace;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}
}
