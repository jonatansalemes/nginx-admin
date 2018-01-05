package com.jslsolucoes.nginx.admin.nginx.parser.directive;

import java.util.List;

public class UpstreamDirective implements Directive {

	private String name;
	private String strategy;
	private List<UpstreamDirectiveServer> servers;

	public UpstreamDirective(String name, String strategy, List<UpstreamDirectiveServer> servers) {
		this.name = name;
		this.strategy = strategy;
		this.servers = servers;
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

	public List<UpstreamDirectiveServer> getServers() {
		return servers;
	}

	public void setServers(List<UpstreamDirectiveServer> servers) {
		this.servers = servers;
	}

	@Override
	public DirectiveType type() {
		return DirectiveType.UPSTREAM;
	}
}
