package com.jslsolucoes.nginx.admin.agent.client.api.impl.status;

import java.util.concurrent.ScheduledExecutorService;

import com.jslsolucoes.nginx.admin.agent.client.api.NginxAgentClientApiBuilder;

public class NginxStatusBuilder implements NginxAgentClientApiBuilder {

	private ScheduledExecutorService scheduledExecutorService;
	private String endpoint;
	private String authorizationKey;

	private NginxStatusBuilder() {

	}

	@Override
	public NginxStatus build() {
		return new NginxStatus(scheduledExecutorService, endpoint, authorizationKey);
	}

	public static NginxStatusBuilder newBuilder() {
		return new NginxStatusBuilder();
	}

	public NginxStatusBuilder withScheduledExecutorService(ScheduledExecutorService scheduledExecutorService) {
		this.scheduledExecutorService = scheduledExecutorService;
		return this;
	}

	public NginxStatusBuilder withEndpoint(String endpoint) {
		this.endpoint = endpoint;
		return this;
	}

	public NginxStatusBuilder withAuthorizationKey(String authorizationKey) {
		this.authorizationKey = authorizationKey;
		return this;
	}

}
