package com.jslsolucoes.nginx.admin.agent.model.request;

import java.util.List;

import com.jslsolucoes.nginx.admin.agent.model.Endpoint;

public class NginxUpstreamUpdateRequest {

	private String home;
	private String name;
	private String strategy;
	private List<Endpoint> endpoints;
	
	public NginxUpstreamUpdateRequest() {
		
	}
	
	public NginxUpstreamUpdateRequest(String home,String name,String strategy,List<Endpoint> endpoints) {
		this.home = home;
		this.name = name;
		this.strategy = strategy;
		this.endpoints = endpoints;
	}
	
	public String getHome() {
		return home;
	}
	public void setHome(String home) {
		this.home = home;
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
