package com.jslsolucoes.nginx.admin.agent.model.response;

public class NginxConfigureResponse implements NginxResponse {

	private String home;
	private Integer maxPostSize;
	private Boolean gzip;
	
	public NginxConfigureResponse() {
		
	}
	
	public NginxConfigureResponse(String home,Integer maxPostSize,Boolean gzip) {
		this.home = home;
		this.maxPostSize = maxPostSize;
		this.gzip = gzip;
	}

	public String getHome() {
		return home;
	}

	public void setHome(String home) {
		this.home = home;
	}

	public Integer getMaxPostSize() {
		return maxPostSize;
	}

	public void setMaxPostSize(Integer maxPostSize) {
		this.maxPostSize = maxPostSize;
	}

	public Boolean getGzip() {
		return gzip;
	}

	public void setGzip(Boolean gzip) {
		this.gzip = gzip;
	}
	
}
