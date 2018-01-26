package com.jslsolucoes.nginx.admin.agent.client.api.impl;

import java.util.concurrent.ScheduledExecutorService;

import com.jslsolucoes.nginx.admin.agent.client.api.NginxAgentClientApiBuilder;

public class NginxConfigureBuilder extends DefaultNginxAgentClientApi implements NginxAgentClientApiBuilder {

	private ScheduledExecutorService scheduledExecutorService;
	private String endpoint;
	private String authorizationKey;
	private Integer maxPostSize;
	private Boolean gzip;

	private NginxConfigureBuilder() {

	}

	@Override
	public NginxConfigure build() {
		return new NginxConfigure(scheduledExecutorService, authorizationKey, endpoint,maxPostSize,gzip);
	}

	public static NginxConfigureBuilder newBuilder() {
		return new NginxConfigureBuilder();
	}

	public NginxConfigureBuilder withScheduledExecutorService(
			ScheduledExecutorService scheduledExecutorService) {
		this.scheduledExecutorService = scheduledExecutorService;
		return this;
	}
	
	public NginxConfigureBuilder withEndpoint(String endpoint) {
		this.endpoint = endpoint;
		return this;
	}
	
	public NginxConfigureBuilder withMaxPostSize(Integer maxPostSize) {
		this.maxPostSize = maxPostSize;
		return this;
	}

	public NginxConfigureBuilder withGzip(Boolean gzip) {
		this.gzip = gzip;
		return this;
	}

	public NginxConfigureBuilder withAuthorizationKey(String authorizationKey) {
		this.authorizationKey = authorizationKey;
		return this;
	}

}
