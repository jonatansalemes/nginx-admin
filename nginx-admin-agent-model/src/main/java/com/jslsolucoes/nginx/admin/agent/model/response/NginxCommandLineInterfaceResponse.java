package com.jslsolucoes.nginx.admin.agent.model.response;

public class NginxCommandLineInterfaceResponse implements NginxResponse {

	private String output;
	private Boolean success;

	public NginxCommandLineInterfaceResponse() {

	}

	public NginxCommandLineInterfaceResponse(String output, Boolean success) {
		this.output = output;
		this.success = success;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}
}
