package com.jslsolucoes.nginx.admin.agent.client.api.impl;

import java.util.concurrent.ScheduledExecutorService;

import com.jslsolucoes.nginx.admin.agent.client.api.NginxAgentClientApiBuilder;

public class NginxCommandLineInterfaceBuilder implements NginxAgentClientApiBuilder {

	private ScheduledExecutorService scheduledExecutorService;
	private String endpoint;
	private String authorizationKey;

	private NginxCommandLineInterfaceBuilder() {

	}

	@Override
	public NginxCommandLineInterface build() {
		return new NginxCommandLineInterface(scheduledExecutorService, endpoint, authorizationKey);
	}

	public static NginxCommandLineInterfaceBuilder newBuilder() {
		return new NginxCommandLineInterfaceBuilder();
	}

	public NginxCommandLineInterfaceBuilder withScheduledExecutorService(
			ScheduledExecutorService scheduledExecutorService) {
		this.scheduledExecutorService = scheduledExecutorService;
		return this;
	}

	public NginxCommandLineInterfaceBuilder withEndpoint(String endpoint) {
		this.endpoint = endpoint;
		return this;
	}

	public NginxCommandLineInterfaceBuilder withAuthorizationKey(String authorizationKey) {
		this.authorizationKey = authorizationKey;
		return this;
	}

}
