package com.jslsolucoes.nginx.admin.agent.model.response;

import java.math.BigDecimal;

public class NginxServerDescriptionResponse implements NginxResponse {

	private String version;
	private String address;
	private Integer pid;
	private BigDecimal uptime;
	
	public NginxServerDescriptionResponse() {
		
	}
	
	public NginxServerDescriptionResponse(String version,String address,Integer pid,BigDecimal uptime) {
		this.version = version;
		this.address = address;
		this.pid = pid;
		this.uptime = uptime;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public BigDecimal getUptime() {
		return uptime;
	}

	public void setUptime(BigDecimal uptime) {
		this.uptime = uptime;
	}
}
