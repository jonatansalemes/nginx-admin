package com.jslsolucoes.nginx.admin.agent.model.response.access.log;

import com.jslsolucoes.nginx.admin.agent.model.response.NginxResponse;

public class NginxAccessLogRotateResponse implements NginxResponse {

	private Integer count;

	public NginxAccessLogRotateResponse() {

	}

	public NginxAccessLogRotateResponse(Integer count) {
		this.count = count;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

}
