package com.jslsolucoes.nginx.admin.util;

public class RuntimeResult {

	private RuntimeResultType runtimeResultType;
	private String output;

	public RuntimeResult(RuntimeResultType runtimeResultType, String output) {
		this.runtimeResultType = runtimeResultType;
		this.output = output;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}



	public RuntimeResultType getRuntimeResultType() {
		return runtimeResultType;
	}



	public void setRuntimeResultType(RuntimeResultType runtimeResultType) {
		this.runtimeResultType = runtimeResultType;
	}
}
