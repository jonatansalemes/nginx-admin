package com.jslsolucoes.nginx.admin.report;

public class UserAgentStatistics {

	private Long count;
	private String userAgent;
	
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
	
	public String getUserAgent() {
		return userAgent;
	}
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}
}
