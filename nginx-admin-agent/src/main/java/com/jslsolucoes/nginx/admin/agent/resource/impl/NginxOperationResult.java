package com.jslsolucoes.nginx.admin.agent.resource.impl;

public class NginxOperationResult {

	private NginxOperationResultType nginxOperationResultType;
	private String output;

	public NginxOperationResult(NginxOperationResultType nginxOperationResultType, String output) {
		this.nginxOperationResultType = nginxOperationResultType;
		this.output = output;
	}
	
	public NginxOperationResult(NginxOperationResultType runtimeResultType) {
		this(runtimeResultType,null);
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}
	
	public Boolean is(NginxOperationResultType nginxOperationResultType){
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