package com.jslsolucoes.nginx.admin.agent.model.request;

public class NginxVirtualHostDeleteRequest {

	private String home;
	private String uuid;
	
	public NginxVirtualHostDeleteRequest() {
		
	}
	
	public NginxVirtualHostDeleteRequest(String home,String uuid) {
		this.home = home;
		this.uuid = uuid;
	}
	
	public String getHome() {
		return home;
	}
	public void setHome(String home) {
		this.home = home;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
}
