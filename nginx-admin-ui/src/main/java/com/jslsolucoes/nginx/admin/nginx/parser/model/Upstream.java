package com.jslsolucoes.nginx.admin.nginx.parser.model;

import java.util.List;

public class Upstream {

	private String name;
	private String strategy;
	private List<Server> servers;

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

	public List<Server> getServers() {
		return servers;
	}

	public void setServers(List<Server> servers) {
		this.servers = servers;
	}
}
