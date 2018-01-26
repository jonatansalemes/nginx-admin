package com.jslsolucoes.nginx.admin.agent.model.request.upstream;

import java.util.List;

import com.jslsolucoes.nginx.admin.agent.model.Endpoint;

public class NginxUpstreamUpdateRequest {

	private String name;
	private String strategy;
	private List<Endpoint> endpoints;
	
	public NginxUpstreamUpdateRequest() {
		
	}
	
	public NginxUpstreamUpdateRequest(String name,String strategy,List<Endpoint> endpoints) {
		this.name = name;
		this.strategy = strategy;
		this.endpoints = endpoints;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
