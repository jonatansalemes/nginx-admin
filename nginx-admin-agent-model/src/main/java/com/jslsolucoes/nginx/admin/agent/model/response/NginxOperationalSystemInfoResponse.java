package com.jslsolucoes.nginx.admin.agent.model.response;

public class NginxOperationalSystemInfoResponse implements NginxResponse {

	private String architecture;
	private String distribution;
	private String name;
	private String version;
	
	public NginxOperationalSystemInfoResponse() {
		
	}
	
	public NginxOperationalSystemInfoResponse(String architecture,String distribution,String name,String version) {
		this.architecture = architecture;
		this.distribution = distribution;
		this.name = name;
		this.version = version;
	}
	
	public String getArchitecture() {
		return architecture;
	}
	public void setArchitecture(String architecture) {
		this.architecture = architecture;
	}
	public String getDistribution() {
		return distribution;
	}
	public void setDistribution(String distribution) {
		this.distribution = distribution;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
}
