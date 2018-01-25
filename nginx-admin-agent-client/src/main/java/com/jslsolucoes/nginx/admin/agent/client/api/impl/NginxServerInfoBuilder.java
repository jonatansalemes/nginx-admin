package com.jslsolucoes.nginx.admin.agent.client.api.impl;

import java.util.concurrent.ScheduledExecutorService;

import com.jslsolucoes.nginx.admin.agent.client.api.NginxAgentClientApiBuilder;

public class NginxServerInfoBuilder implements NginxAgentClientApiBuilder {

	private ScheduledExecutorService scheduledExecutorService;
	private String bin;
	private String home;
	private String endpoint;
	private String authorizationKey;

	private NginxServerInfoBuilder() {

	}

	@Override
	public NginxServerInfo build() {
		return new NginxServerInfo(scheduledExecutorService, endpoint, bin, home, authorizationKey);
	}

	public static NginxServerInfoBuilder newBuilder() {
		return new NginxServerInfoBuilder();
	}

	public NginxServerInfoBuilder withScheduledExecutorService(
			ScheduledExecutorService scheduledExecutorService) {
		this.scheduledExecutorService = scheduledExecutorService;
		return this;
	}

	public NginxServerInfoBuilder withBin(String bin) {
		this.bin = bin;
		return this;
	}

	public NginxServerInfoBuilder withHome(String home) {
		this.home = home;
		return this;
	}

	public NginxServerInfoBuilder withEndpoint(String endpoint) {
		this.endpoint = endpoint;
		return this;
	}

	public NginxServerInfoBuilder withAuthorizationKey(String authorizationKey) {
		this.authorizationKey = authorizationKey;
		return this;
	}

}
