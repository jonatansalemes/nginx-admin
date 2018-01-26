package com.jslsolucoes.nginx.admin.agent.client.api.impl;

import java.util.concurrent.ScheduledExecutorService;

import com.jslsolucoes.nginx.admin.agent.client.api.NginxAgentClientApiBuilder;

public class NginxAccessLogBuilder implements NginxAgentClientApiBuilder {

	
	private ScheduledExecutorService scheduledExecutorService;
	private String endpoint;
	private String authorizationKey;

	private NginxAccessLogBuilder() {
		
	}
	
	@Override
	public NginxAccessLog build() {
		return new NginxAccessLog(scheduledExecutorService,endpoint,authorizationKey);
	}

	public static NginxAccessLogBuilder newBuilder() {
		return new NginxAccessLogBuilder();
	}

	public NginxAccessLogBuilder withScheduledExecutorService(ScheduledExecutorService scheduledExecutorService) {
		this.scheduledExecutorService = scheduledExecutorService;
		return this;
	}
	
	public NginxAccessLogBuilder withEndpoint(String endpoint) {
		this.endpoint = endpoint;
		return this;
	}

	public NginxAccessLogBuilder withAuthorizationKey(String authorizationKey) {
		this.authorizationKey = authorizationKey;
		return this;
	}

}
