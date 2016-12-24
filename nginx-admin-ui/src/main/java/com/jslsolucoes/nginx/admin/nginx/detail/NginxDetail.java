package com.jslsolucoes.nginx.admin.nginx.detail;

import java.math.BigDecimal;

public class NginxDetail {

	private String version;
	private String address;
	private Integer pid;
	private BigDecimal uptime;
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
