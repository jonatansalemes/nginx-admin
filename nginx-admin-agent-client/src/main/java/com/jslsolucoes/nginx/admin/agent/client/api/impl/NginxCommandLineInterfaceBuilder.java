package com.jslsolucoes.nginx.admin.agent.client.api.impl;

import java.util.concurrent.ScheduledExecutorService;

import com.jslsolucoes.nginx.admin.agent.client.api.NginxAgentClientApiBuilder;

public class NginxCommandLineInterfaceBuilder implements NginxAgentClientApiBuilder {

	private ScheduledExecutorService scheduledExecutorService;
	private String bin;
	private String home;
	private String endpoint;
	private String authorization;

	@Override
	public NginxCommandLineInterface build() {
		return new NginxCommandLineInterface(scheduledExecutorService,endpoint,bin,home,authorization);
	}

	public static NginxCommandLineInterfaceBuilder newBuilder() {
		return new NginxCommandLineInterfaceBuilder();
	}

	public NginxCommandLineInterfaceBuilder withScheduledExecutorService(ScheduledExecutorService scheduledExecutorService) {
		this.scheduledExecutorService = scheduledExecutorService;
		return this;
	}

	public NginxCommandLineInterfaceBuilder withBin(String bin) {
		this.bin = bin;
		return this;
	}
	
	public NginxCommandLineInterfaceBuilder withHome(String home) {
		this.home = home;
		return this;
	}

	public NginxCommandLineInterfaceBuilder withEndpoint(String endpoint) {
		this.endpoint = endpoint;
		return this;
	}

	public NginxCommandLineInterfaceBuilder withAuthorization(String authorization) {
		this.authorization = authorization;
		return this;
	}

}
