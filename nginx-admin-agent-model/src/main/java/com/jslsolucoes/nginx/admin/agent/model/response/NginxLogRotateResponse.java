package com.jslsolucoes.nginx.admin.agent.model.response;

public class NginxLogRotateResponse {

	private Integer count;
	
	public NginxLogRotateResponse() {
		
	}
	
	public NginxLogRotateResponse(Integer count) {
		this.count = count;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

}
