package com.jslsolucoes.nginx.admin.model;

import java.util.Set;

public class Upstream {

	private String name;
	
	private Strategy strategy;
	
	private Set<Endpoint> servers;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Strategy getStrategy() {
		return strategy;
	}

	public void setStrategy(Strategy strategy) {
		this.strategy = strategy;
	}

	public Set<Endpoint> getServers() {
		return servers;
	}

	public void setServers(Set<Endpoint> servers) {
		this.servers = servers;
	}

}
