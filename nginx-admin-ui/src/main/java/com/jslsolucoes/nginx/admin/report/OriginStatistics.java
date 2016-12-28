package com.jslsolucoes.nginx.admin.report;

public class OriginStatistics {

	private Long total;
	private String ip;
	private Long request;
	private Long response;
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Long getRequest() {
		return request;
	}
	public void setRequest(Long request) {
		this.request = request;
	}
	public Long getResponse() {
		return response;
	}
	public void setResponse(Long response) {
		this.response = response;
	}
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
}
