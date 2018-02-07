package com.jslsolucoes.nginx.admin.agent.resource.impl;

import org.apache.commons.lang3.exception.ExceptionUtils;

public class NginxOperationResult {

	private NginxOperationResultType nginxOperationResultType;
	private String output;

	public NginxOperationResult(NginxOperationResultType nginxOperationResultType, Throwable throwable) {
		this.nginxOperationResultType = nginxOperationResultType;
		this.output = ExceptionUtils.getStackTrace(throwable);
	}

	public NginxOperationResult(NginxOperationResultType nginxOperationResultType) {
		this.nginxOperationResultType = nginxOperationResultType;
	}

	public NginxOperationResult(NginxOperationResultType nginxOperationResultType, String output) {
		this.nginxOperationResultType = nginxOperationResultType;
		this.output = output;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	public Boolean is(NginxOperationResultType nginxOperationResultType) {
		return this.nginxOperationResultType.equals(nginxOperationResultType);
	}

	public Boolean isSuccess() {
		return is(NginxOperationResultType.SUCCESS);
	}

	public Boolean isError() {
		return is(NginxOperationResultType.ERROR);
	}

	public NginxOperationResultType getNginxOperationResultType() {
		return nginxOperationResultType;
	}

	public void setNginxOperationResultType(NginxOperationResultType nginxOperationResultType) {
		this.nginxOperationResultType = nginxOperationResultType;
	}
}