package com.jslsolucoes.nginx.admin.agent.model.request.upstream;

import java.util.List;

import com.jslsolucoes.nginx.admin.agent.model.Endpoint;

public class NginxUpstreamCreateRequest {

	private String name;
	private String uuid;
	private String strategy;
	private List<Endpoint> endpoints;
	
	public NginxUpstreamCreateRequest() {
		
	}
	
	public NginxUpstreamCreateRequest(String name,String uuid,String strategy,List<Endpoint> endpoints) {
		this.name = name;
		this.uuid = uuid;
		this.strategy = strategy;
		this.endpoints = endpoints;
	}
	

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getStrategy() {
		return strategy;
	}
	public void setStrategy(String strategy) {
		this.strategy = strategy;
	}
	public List<Endpoint> getEndpoints() {
		return endpoints;
	}
	public void setEndpoints(List<Endpoint> endpoints) {
		this.endpoints = endpoints;
	}

}
