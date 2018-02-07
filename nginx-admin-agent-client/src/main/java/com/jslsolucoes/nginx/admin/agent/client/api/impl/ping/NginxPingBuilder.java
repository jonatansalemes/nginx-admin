package com.jslsolucoes.nginx.admin.agent.client.api.impl.ping;

import java.util.concurrent.ScheduledExecutorService;

import com.jslsolucoes.nginx.admin.agent.client.api.NginxAgentClientApiBuilder;

public class NginxPingBuilder implements NginxAgentClientApiBuilder {

	private ScheduledExecutorService scheduledExecutorService;
	private String endpoint;
	private String authorizationKey;

	private NginxPingBuilder() {

	}

	@Override
	public NginxPing build() {
		return new NginxPing(scheduledExecutorService, endpoint, authorizationKey);
	}

	public static NginxPingBuilder newBuilder() {
		return new NginxPingBuilder();
	}

	public NginxPingBuilder withScheduledExecutorService(ScheduledExecutorService scheduledExecutorService) {
		this.scheduledExecutorService = scheduledExecutorService;
		return this;
	}

	public NginxPingBuilder withEndpoint(String endpoint) {
		this.endpoint = endpoint;
		return this;
	}

	public NginxPingBuilder withAuthorizationKey(String authorizationKey) {
		this.authorizationKey = authorizationKey;
		return this;
	}

}
