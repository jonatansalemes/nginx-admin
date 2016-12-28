package com.jslsolucoes.nginx.admin.report;

public class UserAgentStatistics {

	private Long total;
	private String userAgent;
	
	public String getUserAgent() {
		return userAgent;
	}
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
}
