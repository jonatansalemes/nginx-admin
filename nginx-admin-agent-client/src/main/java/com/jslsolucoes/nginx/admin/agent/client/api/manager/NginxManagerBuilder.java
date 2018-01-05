package com.jslsolucoes.nginx.admin.agent.client.api.manager;

import java.util.concurrent.ScheduledExecutorService;

import com.jslsolucoes.nginx.admin.agent.client.api.NginxAgentClientApiBuilder;

public class NginxManagerBuilder implements NginxAgentClientApiBuilder {

	private ScheduledExecutorService scheduledExecutorService;
	private String endpoint;
	private String bin;

	@Override
	public NginxManager build() {
		return new NginxManager(scheduledExecutorService,endpoint,bin);
	}

	public static NginxManagerBuilder newBuilder() {
		return new NginxManagerBuilder();
	}

	public NginxManagerBuilder withScheduledExecutorService(ScheduledExecutorService scheduledExecutorService) {
		this.scheduledExecutorService = scheduledExecutorService;
		return this;
	}

	public NginxManagerBuilder withEndpoint(String endpoint) {
		this.endpoint = endpoint;
		return this;
	}

	public NginxManagerBuilder withBin(String bin) {
		this.bin = bin;
		return this;
	}

}
