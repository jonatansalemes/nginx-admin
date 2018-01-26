package com.jslsolucoes.nginx.admin.agent.model.request;

public class NginxConfigureRequest {

	
	private Integer maxPostSize;
	private Boolean gzip;
	
	public NginxConfigureRequest() {
		
	}
	
	public NginxConfigureRequest(Integer maxPostSize,Boolean gzip) {		
		this.maxPostSize = maxPostSize;
		this.gzip = gzip;
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
