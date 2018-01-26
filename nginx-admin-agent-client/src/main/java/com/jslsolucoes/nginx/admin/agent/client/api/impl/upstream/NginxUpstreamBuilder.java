package com.jslsolucoes.nginx.admin.agent.client.api.impl.upstream;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

import com.jslsolucoes.nginx.admin.agent.client.api.NginxAgentClientApiBuilder;
import com.jslsolucoes.nginx.admin.agent.model.Endpoint;

public class NginxUpstreamBuilder implements NginxAgentClientApiBuilder {

	private ScheduledExecutorService scheduledExecutorService;
	private String endpoint;
	private String authorizationKey;
	private String name;
	private String uuid;
	private String strategy;
	private List<Endpoint> endpoints;

	private NginxUpstreamBuilder() {

	}

	@Override
	public NginxUpstream build() {
		return new NginxUpstream(scheduledExecutorService, endpoint, authorizationKey, uuid, strategy, endpoints, name);
	}

	public static NginxUpstreamBuilder newBuilder() {
		return new NginxUpstreamBuilder();
	}

	public NginxUpstreamBuilder withScheduledExecutorService(
			ScheduledExecutorService scheduledExecutorService) {
		this.scheduledExecutorService = scheduledExecutorService;
		return this;
	}
	
	public NginxUpstreamBuilder withName(String name) {
		this.name = name;
		return this;
	}
	
	public NginxUpstreamBuilder withUuid(String uuid) {
		this.uuid = uuid;
		return this;
	}
	
	public NginxUpstreamBuilder withEndpoints(List<Endpoint> endpoints) {
		this.endpoints = endpoints;
		return this;
	}
	
	public NginxUpstreamBuilder withStrategy(String strategy) {
		this.strategy = strategy;
		return this;
	}

	public NginxUpstreamBuilder withEndpoint(String endpoint) {
		this.endpoint = endpoint;
		return this;
	}

	public NginxUpstreamBuilder withAuthorizationKey(String authorizationKey) {
		this.authorizationKey = authorizationKey;
		return this;
	}

}
