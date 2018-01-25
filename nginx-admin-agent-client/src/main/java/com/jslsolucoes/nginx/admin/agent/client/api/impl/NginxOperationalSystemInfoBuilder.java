package com.jslsolucoes.nginx.admin.agent.client.api.impl;

import java.util.concurrent.ScheduledExecutorService;

import com.jslsolucoes.nginx.admin.agent.client.api.NginxAgentClientApiBuilder;

public class NginxOperationalSystemInfoBuilder extends DefaultNginxAgentClientApi implements NginxAgentClientApiBuilder {

	private ScheduledExecutorService scheduledExecutorService;
	private String endpoint;
	private String authorizationKey;

	private NginxOperationalSystemInfoBuilder() {

	}

	@Override
	public NginxOperationalSystemInfo build() {
		return new NginxOperationalSystemInfo(scheduledExecutorService, authorizationKey, endpoint);
	}

	public static NginxOperationalSystemInfoBuilder newBuilder() {
		return new NginxOperationalSystemInfoBuilder();
	}

	public NginxOperationalSystemInfoBuilder withScheduledExecutorService(
			ScheduledExecutorService scheduledExecutorService) {
		this.scheduledExecutorService = scheduledExecutorService;
		return this;
	}

	public NginxOperationalSystemInfoBuilder withEndpoint(String endpoint) {
		this.endpoint = endpoint;
		return this;
	}

	public NginxOperationalSystemInfoBuilder withAuthorizationKey(String authorizationKey) {
		this.authorizationKey = authorizationKey;
		return this;
	}

}
