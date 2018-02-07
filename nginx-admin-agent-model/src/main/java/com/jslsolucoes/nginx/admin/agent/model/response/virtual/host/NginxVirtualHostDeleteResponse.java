package com.jslsolucoes.nginx.admin.agent.model.response.virtual.host;

import com.jslsolucoes.nginx.admin.agent.model.response.NginxResponse;

public class NginxVirtualHostDeleteResponse implements NginxResponse {

	private String stackTrace;
	private Boolean success;

	public NginxVirtualHostDeleteResponse() {

	}

	public NginxVirtualHostDeleteResponse(String stackTrace, Boolean success) {
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
