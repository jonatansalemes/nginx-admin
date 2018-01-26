package com.jslsolucoes.nginx.admin.agent.model.response.error.log;

import com.jslsolucoes.nginx.admin.agent.model.response.NginxResponse;

public class NginxErrorLogRotateResponse implements NginxResponse {

	private Integer count;
	
	public NginxErrorLogRotateResponse() {
		
	}
	
	public NginxErrorLogRotateResponse(Integer count) {
		this.count = count;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

}
