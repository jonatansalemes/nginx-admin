package com.jslsolucoes.nginx.admin.report;

public class OriginStatistics {

	private Long hits;
	private String ip;
	public Long getHits() {
		return hits;
	}
	public void setHits(Long hits) {
		this.hits = hits;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
}
