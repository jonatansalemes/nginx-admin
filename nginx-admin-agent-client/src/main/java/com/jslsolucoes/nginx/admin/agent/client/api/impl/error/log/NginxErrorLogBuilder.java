package com.jslsolucoes.nginx.admin.agent.client.api.impl.error.log;

import java.util.concurrent.ScheduledExecutorService;

import com.jslsolucoes.nginx.admin.agent.client.api.NginxAgentClientApiBuilder;

public class NginxErrorLogBuilder implements NginxAgentClientApiBuilder {

	private ScheduledExecutorService scheduledExecutorService;
	private String endpoint;
	private String authorizationKey;

	private NginxErrorLogBuilder() {

	}

	@Override
	public NginxErrorLog build() {
		return new NginxErrorLog(scheduledExecutorService, endpoint, authorizationKey);
	}

	public static NginxErrorLogBuilder newBuilder() {
		return new NginxErrorLogBuilder();
	}

	public NginxErrorLogBuilder withScheduledExecutorService(ScheduledExecutorService scheduledExecutorService) {
		this.scheduledExecutorService = scheduledExecutorService;
		return this;
	}

	public NginxErrorLogBuilder withEndpoint(String endpoint) {
		this.endpoint = endpoint;
		return this;
	}

	public NginxErrorLogBuilder withAuthorizationKey(String authorizationKey) {
		this.authorizationKey = authorizationKey;
		return this;
	}

}
